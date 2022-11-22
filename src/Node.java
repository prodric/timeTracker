import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;

/**
 * Representa cada nodo del arbol de tareas y proyectos.
 * Siendo esta la clase abstracta de la cual heredan las clases
 * Task y Project, a si misma usada por el patron estructural Composite
 * en la clase Project.
 */
public abstract class Node {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private String name;
  private Node father;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration totalWorkingTime;

  public Node(String name, Project father) {
    this.name = name;
    this.father = father;
    totalWorkingTime = Duration.ZERO;
    startTime = null;
    endTime = null;
  }


  public Node(JSONObject jsonObject, Project father) {
    this.father = father;
  }

  public abstract void acceptVisitor(Visitor visit);

  public abstract void updateTree(Long period, LocalDateTime endTime);

  protected abstract JSONObject toJson();

  protected Node getFather() {
    return father;
  }

  public String getFatherName() {
    if (father != null) {
      return father.getName();
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
}
