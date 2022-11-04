import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class TimeInterval implements Observer {
    private Task task;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration totalWorkingTime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor que crea un intervalo, setteando totalWorkingTime, startTime y endTime a valores por defecto
     * @param task : Task     tarea a la que pertenece/hace referencia este intervalo
     * @return void
     */
    public TimeInterval(Task task) {
        totalWorkingTime = Duration.ZERO;
        startTime = LocalDateTime.now().plusSeconds(Clock.getInstance().getPeriod());
        endTime = LocalDateTime.now();
        this.task = task;
    }

    /**
     * Constructor que crea un intervalo a partir de un JSONObject
     * @param jsonObject : JSONObject     JSONObject del cual se extrae la informacion para reconstruir el intervalo
     * @param task : Task     tarea a la que pertenece/hace referencia este intervalo
     * @return void
     */
    public TimeInterval(JSONObject jsonObject, Task task) {
        totalWorkingTime = Duration.ofSeconds(jsonObject.getLong("totalWorkingTime"));;
        startTime = LocalDateTime.parse( jsonObject.getString("startTime"));
        endTime = LocalDateTime.parse( jsonObject.getString("endTime"));
        this.task = task;
    }

    /**
     * Getter que recupera la tarea dueña de este intervalo
     * @param "void"
     * @return Task    tarea que contiene este intervalo
     */
    public Task getTask() {
        return task;
    }

    /**
     * Getter que recupera el nombre de la tarea
     * Usado para no inflingir el principio Dont Talk To Strangers
     * @param "void"
     * @return String    nombre de la tarea que es dueña de este intervalo
     */
    public String getTaskName(){
        return task.getName();
    }

    /**
     * Getter que recupera la fecha dónde se inicio el intervalo
     * @param "void"
     * @return LocalDateTime    fecha en la que se inicio el intervalo
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }


    /**
     * Getter que recupera la fecha dónde el intervalo dejo de medir el tiempo
     * @param "void"
     * @return LocalDateTime    fecha en la que se detuvo el intervalo
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter que recupera el tiempo total que se ha trabajado en este intervalo
     * @param "void"
     * @return LocalDateTime    tiempo total trabajado
     */
    public Duration getTotalWorkingTime(){
        return totalWorkingTime;
    }


    /**
     * Metodo que actualiza los parametros de este intervalo y propaga los cambios a la tarea referenciada,
     * que posteriormente propagara los cambios a sus proyectos padres
     * @param o : Observable     objecto del cual escucha
     * @param arg : Object  argumento que se pasa al notifyObservers(arg)
     * @return "void"
     */
    @Override
    public void update(Observable o, Object arg) {
        long period = Clock.getInstance().getPeriod();
        endTime = endTime.plusSeconds(period);
        totalWorkingTime = totalWorkingTime.plusSeconds(period);

//        System.out.println("Interval " + startTime);
//        System.out.println("Interval " + endTime);
//        System.out.println("Interval " + totalWorkingTime.toSeconds());

        task.updateTree(period, endTime);
    }

    /**
     * Metodo que acepta el visitor para recorrer el/los intervalo/s y realizar una operacion determinada por el visitor
     * @param visit : Visitor   objeto de la clase Visitor que pasamos al metodo para poder realizar la llamada a visitTimeInterval()
     * @return "void"
     */
    public void acceptVisitor(Visitor visit) throws IOException {
        visit.visitTimeInterval(this);
    }

    /**
     * Metodo que convierte un intervalo a un objeto JSON
     * @param "void"
     * @return JSONObject
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
