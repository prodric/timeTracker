package core;

import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa cada nodo del arbol de tareas y proyectos.
 * Siendo esta la clase abstracta de la cual heredan las clases
 * main.Task y main.Project, a si misma usada por el patron estructural Composite
 * en la clase main.Project.
 */
public abstract class Node {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private String name;
  private List<String> tags;
  private final Node father;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration totalWorkingTime;
  private int id;

  /**
   * Constructor por defecto de un nodo.
   */
  public Node(String name, Project father) {
    Id id = Id.getInstance();
    this.id = id.generateId();
    this.name = name;
    this.father = father;
    this.tags = new ArrayList<String>();
    this.totalWorkingTime = Duration.ZERO;
    this.startTime = null;
    this.endTime = null;
  }


  /**
   * Constructor que crea un Nodo a partir de un JSONObject.
   */
  public Node(JSONObject jsonObject, Project father) {
    this.father = father;
  }

  public abstract void acceptVisitor(Visitor visit);

  public abstract void updateTree(Long period, LocalDateTime endTime);
  public abstract JSONObject toJson(int depth);

  protected abstract JSONObject toJson();


  /**
   * Getter para obtener el padre de un proyecto/tarea.
   */
  protected Node getFather() {
    return father;
  }

  public int getId() {return id;}
  /**
   * Getter para obtener el nombre del padre de un proyecto/tarea.
   */
  public String getFatherName() {
    if (father != null) {
      return father.getName();
    }
    return null;
  }

  /**
   * Getter para obtener el nombre de un proyecto/tarea.
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTag() {
    return tags;
  }

  public void setTag(ArrayList<String> list) {
    this.tags.addAll(list);
  }

  public Duration getTotalWorkingTime() {
    return totalWorkingTime;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public void setWorkingTime(Duration workingTime) {
    this.totalWorkingTime = workingTime;
  }

  protected void toJson(JSONObject json) {
    json.put("id", id);
    json.put("name", name);
    json.put("initialDate", startTime==null
        ? JSONObject.NULL : formatter.format(startTime));
    json.put("finalDate", endTime==null
        ? JSONObject.NULL : formatter.format(endTime));
    json.put("duration", totalWorkingTime.toSeconds());
  }
}
