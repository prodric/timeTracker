import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * La clase Project implementa el patron estructural Composite, con
 * el fin de que un proyecto pueda estar compuesto de otros
 * nodos ya sean proyectos o tareas.
 * Tambien implementa los metodos necesarios para convertir un proyecto
 * en objecto json y guardarlo en un fichero.json
 */
public class Project extends Node {
  private ArrayList<Node> children;
  private String path = "file.json";


  /**
   * Constructor que crea un proyecto, añadiendolo como proyecto hijo
   * en el caso de que no sea el root.
   */
  public Project(String name, Project father) {
    super(name, father);
    children = new ArrayList<Node>();
    if (father != null) {
      father.children.add(this);
    }
    invariant();
  }

  /**
   * Constructor que crea un proyecto a partir de un JSONObject.
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
  }

  private void invariant(){
    assert getName() != "";
    assert getName().charAt(0) != ' ';
    assert getName().charAt(0) != '\t';
  }

  public String getPath() {
    assert path != null;
    assert path != "";
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
   */
  public void acceptVisitor(Visitor visit) {
    assert visit != null;
    visit.visitProject(this);
  }

  /**
   * Metodo que actualiza el arbol modificando el endTime, el totalWorkingTime.
   */
  @Override
  public void updateTree(Long period, LocalDateTime endTime) {
    //precondiciones
    assert period == Clock.getInstance().getPeriod();
    assert getStartTime() != null;
    assert endTime != null;

    this.setEndTime(endTime);
    this.setWorkingTime(getTotalWorkingTime().plusSeconds(period));
    if (getFather() != null) {
      this.getFather().setStartTime(this.getStartTime());
      this.getFather().updateTree(period, endTime);
    }

    //postcondiciones
    assert getTotalWorkingTime() != null;

    //.out.println("Project " + getStartTime());
    //System.out.println("Project " + getEndTime());
    //System.out.println("Project " + getWorkingTime().toSeconds());
  }

  /**
   * Metodo que convierte un proyecto a un objeto JSON,
   * también convierte a cada proyecto de la lista children.
   */
  @Override
  public JSONObject toJson() {
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

    return jsonObject;
  }

  /**
   * Metodo que guarda el jsonObject en el directorio especificado.
   */
  public void save(String path, JSONObject jsonObject) {

    try {
      File myObj = new File(path);
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }


    try {
      FileWriter myWriter = new FileWriter(path);
      myWriter.write(jsonObject.toString());
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
