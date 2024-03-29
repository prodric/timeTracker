package core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * La clase main.Project implementa el patron estructural Composite, con
 * el fin de que un proyecto pueda estar compuesto de otros
 * nodos ya sean proyectos o tareas.
 * Tambien implementa los metodos necesarios para convertir un proyecto
 * en objecto json y guardarlo en un fichero.json
 */
public class Project extends Node {
  private ArrayList<Node> children;
  private String path = "file.json";
  private static final Logger logger = LoggerFactory.getLogger("Fita1");




  /**
   * Constructor que crea un proyecto, añadiendolo como proyecto hijo
   * en el caso de que no sea el root.
   * param name : String    nombre del proyecto
   * param father : main.Project     padre del proyecto que se creará
   */
  public Project(String name, Project father) {
    super(name, father);
    children = new ArrayList<Node>();
    if (father != null) {
      father.children.add(this);
    } else {
      logger.info("Root main.Project: {} successfully created", this.getName());
    }
    invariant();
    logger.info("main.Project: {} successfully created", this.getName());
  }

  /**
   * Constructor que crea un proyecto a partir de un JSONObject.
   * param jsonObject : JSONObject    objeto del cual extraemos el proyecto
   * param father : main.Project   padre del proyecto que se creará
   */
  public Project(JSONObject jsonObject, Project father) {
    super(jsonObject, father);

    children = new ArrayList<Node>();

    if (jsonObject.get("startTime") == JSONObject.NULL) {
      setName(jsonObject.getString("projectName"));
      setStartTime(null);
      setEndTime(null);
      setWorkingTime(Duration.ZERO);
    } else {
      setName(jsonObject.getString("projectName"));
      setStartTime(LocalDateTime.parse(jsonObject.getString("startTime")));
      setEndTime(LocalDateTime.parse(jsonObject.getString("endTime")));
      setWorkingTime(Duration.ofSeconds(jsonObject.getLong("totalWorkingTime")));
    }

    if (jsonObject.has("Object")) {
      JSONArray jsonArray = jsonObject.getJSONArray("Object");

      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

        if (jsonObject1.has("projectName")) {
          Project pr = new Project(jsonObject1, this);
          children.add(pr);
        } else if (jsonObject1.has("taskName")) {
          Task t = new Task(jsonObject1, this);
          children.add(t);
        }
      }
    }
    invariant();
    logger.info("main.Project: {} loaded successfully from JSON format", this.getName());
  }

  /**
   * Metodo que implementa el invariante global de la clase.
   */
  private void invariant() {
    assert !Objects.equals(getName(), "");
    assert getName().charAt(0) != ' ';
    assert getName().charAt(0) != '\t';
  }

  /**
   * Getter para obtener el nombre del fichero donde se guardará el arbol.
   */
  public String getPath() {
    assert path != null;
    assert !path.equals("");

    return path;
  }

  /**
   * Getter que devuelve la lista de los hijos del proyecto.
   */
  public ArrayList<Node> getChildren() {
    assert children != null;

    return children;
  }

  /**
   * Metodo que acepta el visitor para recorrer el/los proyecto/s
   * y realizar una operacion determinada por el visitor.
   * param visit : main.Visitor   objeto de la clase main.Visitor que pasamos al metodo para poder realizar
   * la llamada a visitTask().
   */
  public void acceptVisitor(Visitor visit) {
    assert visit != null;
    visit.visitProject(this);
  }

  /**
   * Metodo que actualiza el arbol modificando el endTime, el totalWorkingTime.
   * param period  : Long     periodo de tiempo que tenemos que sumar a las variables LocalDateTime.
   * param endTime : LocalDateTime  variable usada para actualizar endTime a los padres de la tarea.
   * return "void".
   */
  @Override
  public void updateTree(Long period, LocalDateTime endTime) {
    //precondiciones
    assert period == Clock.getInstance().getPeriod();
    assert getStartTime() != null;
    assert endTime != null;

    logger.info("main.Project: {} updating values...", this.getName());

    this.setEndTime(endTime);
    this.setWorkingTime(getTotalWorkingTime().plusSeconds(period));
    if (getFather() != null) {
      this.getFather().setStartTime(this.getStartTime());
      this.getFather().updateTree(period, endTime);
    }

    logger.debug("main.Project: {} -> Start Time: {}", this.getName(), this.getStartTime());
    logger.debug("main.Project: {} -> End Time: {}", this.getName(), this.getEndTime());
    logger.debug("main.Project: {} -> Total Working Time: {}", this.getName(),
        this.getTotalWorkingTime().toSeconds());

    //postcondiciones
    assert getTotalWorkingTime() != null;

    logger.info("main.Project: {} values updated", this.getName());
  }

  /**
   * Metodo que convierte un proyecto a un objeto JSON,
   * también convierte a cada proyecto de la lista children.
   * param "void".
   * return JSONObject.
   */
  @Override
  public JSONObject toJson() {
    logger.trace("Converting main.Project: {} to JSON format", this.getName());
    JSONObject jsonObject = new JSONObject();

    if (this.getStartTime() == null) {
      jsonObject.put("projectName", this.getName());
      jsonObject.put("father", this.getFather());
      jsonObject.put("startTime", JSONObject.NULL);
      jsonObject.put("endTime", JSONObject.NULL);
      jsonObject.put("totalWorkingTime", JSONObject.NULL);
    } else {
      jsonObject.put("projectName", this.getName());
      jsonObject.put("father", this.getFather());
      jsonObject.put("startTime", this.getStartTime());
      jsonObject.put("endTime", this.getEndTime());
      jsonObject.put("totalWorkingTime", this.getTotalWorkingTime().toSeconds());
    }

    JSONArray jsonArray = new JSONArray();
    for (Node child : children) {
      jsonArray.put(((Node) child).toJson());
    }
    String key = "Object";
    jsonObject.put(key, jsonArray);

    logger.trace("main.Project: {} conversion to JSON format successful", this.getName());

    return jsonObject;
  }

  public JSONObject toJson(int depth) {
    JSONObject json = new JSONObject();
    json.put("class", "project");
    super.toJson(json);
    if (depth>0) {
      JSONArray jsonActivities = new JSONArray();
      for (Node activity : children) {
        jsonActivities.put(activity.toJson(depth - 1));
        // important: decrement depth
      }
      json.put("activities", jsonActivities);
    }
    return json;
  }

  /**
   * Metodo que guarda el jsonObject en el directorio especificado.
   * param path : String    directorio especificado
   * param jsonObject : JSONObject    objeto que se desea guardar
   */
  public void save(String path, JSONObject jsonObject) {

    File myObj = new File(path);

    try {
      if (myObj.createNewFile()) {
        logger.debug("File: {} created", myObj.getName());
      } else {
        logger.debug("File: {} already exists", myObj.getName());
      }
    } catch (IOException e) {
      logger.debug("An error occurred while creating file: {}", myObj.getName());
      e.printStackTrace();
    }


    try {
      logger.debug("Writing to file: {}", myObj.getName());
      FileWriter myWriter = new FileWriter(path);
      myWriter.write(jsonObject.toString());
      myWriter.close();
      System.out.println(".");
      logger.debug("Successfully wrote to file: {}", myObj.getName());
    } catch (IOException e) {
      logger.debug("An error occurred while writing to file: {}", myObj.getName());
      e.printStackTrace();
    }
  }
}
