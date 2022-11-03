import java.sql.Time;
import java.time.Duration;
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
            setStartTime(LocalDateTime.now());
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
    }

    /**
     * Funcion que implementa el visitor para recorrer la/s tarea/s
     */
    public void acceptVisitor(Visitor visit) {
        visit.visitTask(this);
    }

    /**
     * Funcion que actualiza el arbol modificando el tiempo final, el tiempo de trabajo
     */
    @Override
    public void updateTree(Long period, LocalDateTime endTime){
        this.setEndTime(endTime);
        this.setWorkingTime(getWorkingTime().plusSeconds(period));
        if(getFather() != null) {
            this.getFather().setStartTime(this.getStartTime());
            this.getFather().updateTree(period, endTime);
        }

//        System.out.println("Task " + getStartTime());
//        System.out.println("Task " + getEndTime());
//        System.out.println("Task " + getWorkingTime().toSeconds());
    }
}
