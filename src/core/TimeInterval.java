package core;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

/**
 * Clase que representa un intervalo de tiempo relacionado a una tarea.
 */
public class TimeInterval implements Observer {
  private Task task;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration totalWorkingTime;
  private static final Logger logger = LoggerFactory.getLogger("Fita1");

  /**
   * Constructor que crea un intervalo, setteando totalWorkingTime,
   * startTime y endTime a valores por defecto.
   * param task : main.Task    tarea a la que pertenece el intervalo
   */
  public TimeInterval(Task task) {
    totalWorkingTime = Duration.ZERO;
    startTime = LocalDateTime.now().plusSeconds(Clock.getInstance().getPeriod());
    endTime = LocalDateTime.now();
    this.task = task;
    logger.info("Time Interval for main.Task: {} has been created", this.task.getName());
  }

  /**
   * Constructor que crea un intervalo a partir de un JSONObject.
   * param jsonObject : JSONObject     JSONObject del cual se extrae la informacion para
   * reconstruir el intervalo.
   * param task : main.Task   tarea a la que pertenece el intervalo
   */
  public TimeInterval(JSONObject jsonObject, Task task) {
    totalWorkingTime = Duration.ofSeconds(jsonObject.getLong("totalWorkingTime"));
    startTime = LocalDateTime.parse(jsonObject.getString("startTime"));
    endTime = LocalDateTime.parse(jsonObject.getString("endTime"));
    this.task = task;
    logger.info("Time Interval for main.Task: {} "
        + "has been loaded from JSON format successfully", this.task.getName());
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
    logger.info("Time Interval updating values...");

    long period = Clock.getInstance().getPeriod();
    endTime = endTime.plusSeconds(period);
    totalWorkingTime = totalWorkingTime.plusSeconds(period);

    logger.debug("Time Interval -> Start Time: {}", this.getStartTime());
    logger.debug("Time Interval -> End Time: {}", this.getEndTime());
    logger.debug("Time Interval -> Total Working Time: {}", this.getTotalWorkingTime().toSeconds());

    logger.info("Time Interval values updated");

    task.updateTree(period, endTime);
  }

  /**
   * Metodo que acepta el visitor para recorrer el/los intervalo/s.
   * En cada intervalo realiza una operacion determinada por el visitor.
   * param visit : main.Visitor   objeto de la clase main.Visitor que pasamos al metodo para poder realizar
   * la llamada a visitTimeInterval().
   */
  public void acceptVisitor(Visitor visit) {
    visit.visitTimeInterval(this);
  }

  /**
   * Metodo que convierte un intervalo a un objeto JSON.
   */
  public JSONObject toJson() {
    logger.trace("Converting Time Interval to JSON format");

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("task", this.getTask());
    jsonObject.put("startTime", this.getStartTime());
    jsonObject.put("endTime", this.getEndTime());
    jsonObject.put("totalWorkingTime", this.getTotalWorkingTime().toSeconds());

    logger.trace("Time Interval conversion to JSON format successful");

    return jsonObject;
  }
}
