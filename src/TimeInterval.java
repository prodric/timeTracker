import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class TimeInterval implements Observer {
    private Task task;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration totalWorkingTime;

    /**
     * Constructor que crea una tarea
     * @param "void"
     */
    public TimeInterval(Task task) {
        totalWorkingTime = Duration.ZERO;
        startTime = LocalDateTime.now();
        endTime = LocalDateTime.now();
        this.task = task;
    }

    /**
     * Getter que recupera el nombre de la tarea
     */
    public String getTaskName(){
        return task.getName();
    }

    /**
     * Getter que recupera el tiempo de inicio del intervalo
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter que recupera el tiempo final del intervalo
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter que recupera el tiempo total de trabajo
     */
    public Duration getTotalWorkingTime(){
        return totalWorkingTime;
    }

    /**
     * Setter que modifica el tiempo de inicio del intervalo
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Setter que modifica el tiempo final del intervalo
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Funcion que actualiza el orbserver
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

    public void acceptVisitor(Visitor visit) {
        visit.visitTimeInterval(this);
    }
}
