import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Node{

    private List<TimeInterval> timeIntervals;
    private TimeInterval lastAdded;

    /**
     * Constructor que crea una tarea
     * @param "void"
     */
    public Task(String name, Project father) {
        super(name, father);
        this.timeIntervals = new ArrayList<TimeInterval>();
        if (father != null)
            father.getChildren().add(this);
    }

    public Task(JSONObject jsonObject, Project father) {
        super(jsonObject, father);
        lastAdded = (TimeInterval) jsonObject.get("lastAdded");

        for (int i = 0; i < jsonObject.getJSONArray("ObjectInterval").length(); i++){
            JSONObject jsonObject2 = jsonObject.getJSONArray("ObjectInterval").getJSONObject(i);

            if (jsonObject2.has("ObjectInterval")){
                TimeInterval interval = new TimeInterval(jsonObject2, this);
            }
        }

    }


    /**
     * Getter que recupera los intervalos de tiempo
     */
    public List<TimeInterval> getTimeIntervals (){
        return timeIntervals;
    }

    /**
     * Getter que recupera el ultimo time interval que se ha añadido a la tarea
     */
    public TimeInterval getLast(){
        return lastAdded;
    }

    /**
     * Funcion que arranca la tarea
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
     * Funcion que detiene la tarea

     */
    public void stopTask(){
        Clock.getInstance().deleteObserver(this.getLast());
        //Clock.getInstance().deleteObserver(this.getTimeIntervals().get(this.getTimeIntervals().size() - 1));
    }

    /**
     * Funcion que implementa el visitor para recorrer la/s tarea/s
     */
    public void acceptVisitor(Visitor visit) throws IOException {
        visit.visitTask(this);
    }

    /**
     * Funcion que actualiza el arbol modificando el tiempo final, el tiempo de trabajo
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

    @Override
    public JSONObject toJson(){
        JSONObject jsonObject= new JSONObject();

        jsonObject.put("name", this.getName());
        jsonObject.put("father", this.getFatherName());
        jsonObject.put("startTime", this.getStartTime());
        jsonObject.put("endTime", this.getEndTime());
        jsonObject.put("totalWorkingTime", this.getTotalWorkingTime().toSeconds());
        jsonObject.put("lastAdded", this.getLast());

        JSONArray jsonArray = new JSONArray();
        for (TimeInterval interval: timeIntervals){
            jsonArray.put(interval.toJson());
        }

        jsonObject.put("ObjectInterval", jsonArray);

        return jsonObject;
    }
}
