import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

/**
 * Clase que representa un intervalo de tiempo relacionado a una tarea.
 */
public class TimeInterval implements Observer {
  private Task task;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration totalWorkingTime;

  /**
   * Constructor que crea un intervalo, setteando totalWorkingTime,
   * startTime y endTime a valores por defecto.
   */
  public TimeInterval(Task task) {
    totalWorkingTime = Duration.ZERO;
    startTime = LocalDateTime.now().plusSeconds(Clock.getInstance().getPeriod());
    endTime = LocalDateTime.now();
    this.task = task;
  }

  /**
   * Constructor que crea un intervalo a partir de un JSONObject.
   */
  public TimeInterval(JSONObject jsonObject, Task task) {
    totalWorkingTime = Duration.ofSeconds(jsonObject.getLong("totalWorkingTime"));
    startTime = LocalDateTime.parse(jsonObject.getString("startTime"));
    endTime = LocalDateTime.parse(jsonObject.getString("endTime"));
    this.task = task;
  }

  /**
   * Getter que recupera la tarea dueña de este intervalo.
   */
  public Task getTask() {
    return task;
  }

  /**
   * Getter que recupera el nombre de la tarea.
   * Usado para no inflingir el principio Dont Talk To Strangers.
   */
  public String getTaskName() {
    return task.getName();
  }

  /**
   * Getter que recupera la fecha dónde se inicio el intervalo.
   */
  public LocalDateTime getStartTime() {
    return startTime;
  }


  /**
   * Getter que recupera la fecha dónde el intervalo dejo de medir el tiempo.
   */
  public LocalDateTime getEndTime() {
    return endTime;
  }

  /**
   * Getter que recupera el tiempo total que se ha trabajado en este intervalo.
   */
  public Duration getTotalWorkingTime() {
    return totalWorkingTime;
  }


  /**
   * Metodo que actualiza los parametros de este intervalo y
   * propaga los cambios a la tarea referenciada.
   * Posteriormente propagara los cambios a sus proyectos padres.
   */
  @Override
  public void update(Observable o, Object arg) {
    long period = Clock.getInstance().getPeriod();
    endTime = endTime.plusSeconds(period);
    totalWorkingTime = totalWorkingTime.plusSeconds(period);

    //System.out.println("Interval " + startTime);
    //System.out.println("Interval " + endTime);
    //System.out.println("Interval " + totalWorkingTime.toSeconds());

    task.updateTree(period, endTime);
  }

  /**
   * Metodo que acepta el visitor para recorrer el/los intervalo/s.
   * En cada intervalo realiza una operacion determinada por el visitor.
   */
  public void acceptVisitor(Visitor visit) {
    visit.visitTimeInterval(this);
  }

  /**
   * Metodo que convierte un intervalo a un objeto JSON.
   */
  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("task", this.getTask());
    jsonObject.put("startTime", this.getStartTime());
    jsonObject.put("endTime", this.getEndTime());
    jsonObject.put("totalWorkingTime", this.getTotalWorkingTime().toSeconds());

    return jsonObject;
  }
}
