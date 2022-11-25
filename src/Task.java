import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * La funcion principal de la classe Task es la de iniciar o parar una tarea
 * guardando sus intervalos de tiempo gracias al patron de comportamiento Observer,
 * añadiendo o eliminando el intervalo de ser observado por clock.
 * A su vez tambien utiliza el patron visitor para que TreePrinter visite cada tarea
 * y la pueda imprimir correctamente.
 */
public class Task extends Node {

  private static final Logger logger = LoggerFactory.getLogger("Fita1");
  private List<TimeInterval> timeIntervals;
  private TimeInterval lastAdded;

  /**
   * Constructor que crea una tarea.
   * param name   : String     nombre de la tarea.
   * param father : Project   padre de la tarea.
   * return void.
   */
  public Task(String name, Project father) {
    super(name, father);
    this.timeIntervals = new ArrayList<TimeInterval>();
    if (father != null) {
      father.getChildren().add(this);
    } else {
      logger.warn("Task: {} must extend from parent node", this.getName());
    }
    invariant();
    logger.info("Task: {} successfully created", this.getName());
  }

  /**
   * Constructor que crea una tarea a partir de un JSONObject.
   * param jsonObject : JSONObject     JSONObject del cual se extrae la informacion para
   * reconstruir la tarea.
   * param father : Project   padre de la tarea.
   * return void.
   */
  public Task(JSONObject jsonObject, Project father) {
    super(jsonObject, father);
    timeIntervals = new ArrayList<TimeInterval>();

    if (jsonObject.get("startTime") == JSONObject.NULL) {
      setName(jsonObject.getString("taskName"));
      setStartTime(null);
      setEndTime(null);
      setWorkingTime(Duration.ZERO);
    } else {
      setName(jsonObject.getString("taskName"));
      setStartTime(LocalDateTime.parse(jsonObject.getString("startTime")));
      setEndTime(LocalDateTime.parse(jsonObject.getString("endTime")));
      setWorkingTime(Duration.ofSeconds(jsonObject.getLong("totalWorkingTime")));
    }

    if (jsonObject.has("ObjectInterval")) {
      JSONArray jsonArray = jsonObject.getJSONArray("ObjectInterval");

      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jasonObj = jsonArray.getJSONObject(i);

        TimeInterval timeInterval = new TimeInterval(jasonObj, this);
        timeIntervals.add(timeInterval);
        lastAdded = timeInterval;
      }
    }

    invariant();
    logger.info("Task: {} loaded successfully from JSON format", this.getName());
  }

  private void invariant() {
    assert getFather() != null;
    assert !Objects.equals(getName(), "");
    assert getName().charAt(0) != ' ';
    assert getName().charAt(0) != '\t';
  }

  /**
   * Getter que recupera los intervalos de tiempo que han sido guardados en la tarea.
   * param "void".
   * return timeIntervals    Lista de intervalos.
   */
  public List<TimeInterval> getTimeIntervals() {
    assert timeIntervals != null;
    return timeIntervals;
  }

  /**
   * Getter que recupera el ultimo time interval que se ha añadido a la tarea.
   * return lastAdded   time interval mas reciente.
   */
  public TimeInterval getLast() {
    return lastAdded;
  }

  /**
   * Metodo que arranca la tarea, hace set del startTime y endTime a valores predeterminados.
   * Instancia un intervalo nuevo y lo añade a la lista timeIntervals,
   * además de guardarlo en lastAdded.
   * param "void".
   * return "void".
   */
  public void startTask() {

    logger.info("Task: {} STARTED", this.getName());

    if (getStartTime() == null && getEndTime() == null) {
      setStartTime(LocalDateTime.now().plusSeconds(Clock.getInstance().getPeriod()));
      setEndTime(LocalDateTime.now());
    }

    TimeInterval timeInterval = new TimeInterval(this);
    timeIntervals.add(timeInterval);
    lastAdded = timeInterval;
    Clock.getInstance().addObserver(timeInterval);

    //postcondiciones
    assert !timeIntervals.isEmpty();
    assert lastAdded != null;
    assert getTotalWorkingTime() != null;
    assert getStartTime() != null;
    assert getEndTime() != null;
  }

  /**
   * Metodo que detiene la tarea.
   * param "void".
   * return "void".
   */
  public void stopTask() {
    final int numObservers = Clock.getInstance().countObservers();
    //precondiciones
    assert getLast() != null;
    assert Clock.getInstance() != null;

    logger.info("Task: {} STOPPED", this.getName());
    Clock.getInstance().deleteObserver(this.getLast());

    //postcondiciones
    assert Clock.getInstance().countObservers() == numObservers - 1;
  }

  /**
   * Metodo que acepta el visitor para recorrer la/s tarea/s y realizar
   * una operacion determinada por el visitor.
   * param visit : Visitor   objeto de la clase Visitor que pasamos al metodo para poder realizar
   * la llamada a visitTask().
   * return "void".
   */
  public void acceptVisitor(Visitor visit) {
    assert visit != null; //precondicion
    visit.visitTask(this);
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

    logger.info("Task: {} updating values...", this.getName());

    this.setEndTime(endTime);
    this.setWorkingTime(getTotalWorkingTime().plusSeconds(period));
    if (getFather() != null) {
      this.getFather().setStartTime(this.getStartTime());
      this.getFather().updateTree(period, endTime);
    }

    logger.debug("Task: {} -> Start Time: {}", this.getName(), this.getStartTime());
    logger.debug("Task: {} -> End Time: {}", this.getName(), this.getEndTime());
    logger.debug("Task: {} -> Total Working Time: {}", this.getName(),
        this.getTotalWorkingTime().toSeconds());

    //postcondiciones
    assert getTotalWorkingTime() != null;

    logger.info("Task: {} values updated", this.getName());
  }

  /**
   * Metodo que convierte una tarea a un objeto JSON,
   * también convierte a cada TimeInterval de la lista timeIntervals.
   * param "void".
   * return JSONObject.
   */
  @Override
  public JSONObject toJson() {
    logger.trace("Converting Task: {} to JSON format", this.getName());
    JSONObject jsonObject = new JSONObject();

    if (this.getStartTime() == null) {
      jsonObject.put("taskName", this.getName());
      jsonObject.put("father", this.getFather());
      jsonObject.put("startTime", JSONObject.NULL);
      jsonObject.put("endTime", JSONObject.NULL);
      jsonObject.put("totalWorkingTime", JSONObject.NULL);
      jsonObject.put("lastAdded", this.getLast());
    } else {
      jsonObject.put("taskName", this.getName());
      jsonObject.put("father", this.getFather());
      jsonObject.put("startTime", this.getStartTime());
      jsonObject.put("endTime", this.getEndTime());
      jsonObject.put("totalWorkingTime", this.getTotalWorkingTime().toSeconds());
      jsonObject.put("lastAdded", this.getLast());
    }

    JSONArray jsonArray = new JSONArray();
    for (TimeInterval interval : timeIntervals) {
      jsonArray.put(interval.toJson());
    }
    String key = "ObjectInterval";
    jsonObject.put(key, jsonArray);

    logger.trace("Task: {} conversion to JSON format successful", this.getName());

    return jsonObject;
  }
}
