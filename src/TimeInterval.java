import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class TimeInterval implements Observer {
    private Task task;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration totalWorkingTime;

    public TimeInterval(Task task) {
        totalWorkingTime = Duration.ZERO;
        startTime = LocalDateTime.now();
        endTime = LocalDateTime.now();
        this.task = task;
    }

    public String getTaskName(){
        return task.getName();
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Duration getTotalWorkingTime(){
        return totalWorkingTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

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
