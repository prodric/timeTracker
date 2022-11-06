import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *La funcion principal de la classe Task es la de iniciar o parar una tarea
 * guardando sus intervalos de tiempo gracias al patron de comportamiento Observer,
 * añadiendo o eliminando el intervalo de ser observado por clock.
 * A su vez tambien utiliza el patron visitor para que TreePrinter visite cada tarea
 * y la pueda imprimir correctamente.
 */
public class Task extends Node{

    private List<TimeInterval> timeIntervals;
    private TimeInterval lastAdded;

    /**
     * Constructor que crea una tarea
     * @param name : String     nombre de la tarea
     * @param father : Project   padre de la tarea
     * @return void
     */
    public Task(String name, Project father) {
        super(name, father);
        this.timeIntervals = new ArrayList<TimeInterval>();
        if (father != null)
            father.getChildren().add(this);
    }

    /**
     * Constructor que crea una tarea a partir de un JSONObject
     * @param jsonObject : JSONObject     JSONObject del cual se extrae la informacion para reconstruir la tarea
     * @param father : Project   padre de la tarea
     * @return void
     */
    public Task(JSONObject jsonObject, Project father) {
        super(jsonObject, father);
        timeIntervals = new ArrayList<TimeInterval>();

        if(jsonObject.get("startTime") == JSONObject.NULL){
            setName(jsonObject.getString("taskName"));
            setStartTime(null);
            setEndTime(null);
            setWorkingTime(Duration.ZERO);
        }
        else {
            setName(jsonObject.getString("taskName"));
            setStartTime(LocalDateTime.parse(jsonObject.getString("startTime")));
            setEndTime(LocalDateTime.parse(jsonObject.getString("endTime")));
            setWorkingTime(Duration.ofSeconds(jsonObject.getLong("totalWorkingTime")));
        }

        if (jsonObject.has("ObjectInterval")){
            JSONArray jsonArray = jsonObject.getJSONArray("ObjectInterval");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jOb = jsonArray.getJSONObject(i);

                TimeInterval timeInterval = new TimeInterval(jOb, this);
                timeIntervals.add(timeInterval);
                lastAdded = timeInterval;
            }
        }

    }


    /**
     * Getter que recupera los intervalos de tiempo que han sido guardados en la tarea
     * @param "void"
     * @return timeIntervals    Lista de intervalos
     */
    public List<TimeInterval> getTimeIntervals (){
        return timeIntervals;
    }

    /**
     * Getter que recupera el ultimo time interval que se ha añadido a la tarea
     * @return lastAdded   time interval mas reciente
     */
    public TimeInterval getLast(){
        return lastAdded;
    }

    /**
     * Metodo que arranca la tarea, hace set del startTime y endTime a valores predeterminados.
     * Instancia un intervalo nuevo y lo añade a la lista timeIntervals, además de guardarlo en lastAdded
     * @param "void"
     * @return "void"
     */
    public void startTask() {
        if (getStartTime() == null && getEndTime() == null) {
            setStartTime(LocalDateTime.now().plusSeconds(Clock.getInstance().getPeriod()));
            setEndTime(LocalDateTime.now());
        }

        TimeInterval timeInterval = new TimeInterval(this);
        timeIntervals.add(timeInterval);
        lastAdded = timeInterval;
        Clock.getInstance().addObserver(timeInterval);
    }

    /**
     * Metodo que detiene la tarea
     * @param "void"
     * @return "void"
     */
    public void stopTask(){
        Clock.getInstance().deleteObserver(this.getLast());
        //Clock.getInstance().deleteObserver(this.getTimeIntervals().get(this.getTimeIntervals().size() - 1));
    }

    /**
     * Metodo que acepta el visitor para recorrer la/s tarea/s y realizar una operacion determinada por el visitor
     * @param visit : Visitor   objeto de la clase Visitor que pasamos al metodo para poder realizar la llamada a visitTask()
     * @return "void"
     */
    public void acceptVisitor(Visitor visit) {
        visit.visitTask(this);
    }

    /**
     * Metodo que actualiza el arbol modificando el endTime, el totalWorkingTime
     * @param period : Long     periodo de tiempo que tenemos que sumar a las variables LocalDateTime
     * @param endTime : LocalDateTime  variable usada para actualizar endTime a los padres de la tarea
     * @return "void"
     */
    @Override
    public void updateTree(Long period, LocalDateTime endTime){
        this.setEndTime(endTime);
        this.setWorkingTime(getTotalWorkingTime().plusSeconds(period));
        if(getFather() != null) {
            this.getFather().setStartTime(this.getStartTime());
            this.getFather().updateTree(period, endTime);
        }

//        System.out.println("Task " + getStartTime());
//        System.out.println("Task " + getEndTime());
//        System.out.println("Task " + getWorkingTime().toSeconds());
    }

    /**
     * Metodo que convierte una tarea a un objeto JSON, también convierte a cada TimeInterval de la lista timeIntervals
     *
     * @param "void"
     * @return JSONObject
     */
    @Override
    public JSONObject toJson(){
        JSONObject jsonObject= new JSONObject();

        if (this.getStartTime() == null){
            jsonObject.put("taskName", this.getName());
            jsonObject.put("father", this.getFather());
            jsonObject.put("startTime", JSONObject.NULL);
            jsonObject.put("endTime", JSONObject.NULL);
            jsonObject.put("totalWorkingTime", JSONObject.NULL);
            jsonObject.put("lastAdded", this.getLast());
        }
        else {
            jsonObject.put("taskName", this.getName());
            jsonObject.put("father", this.getFather());
            jsonObject.put("startTime", this.getStartTime());
            jsonObject.put("endTime", this.getEndTime());
            jsonObject.put("totalWorkingTime", this.getTotalWorkingTime().toSeconds());
            jsonObject.put("lastAdded", this.getLast());
        }

        JSONArray jsonArray = new JSONArray();
        for (TimeInterval interval: timeIntervals){
            jsonArray.put(interval.toJson());
        }
        String key = "ObjectInterval";
        jsonObject.put(key, jsonArray);

        return jsonObject;
    }
}
