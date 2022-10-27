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
        this.task = task;
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

    public void calculateTime(){
        totalWorkingTime = totalWorkingTime.between(startTime,endTime);
    }
    @Override
    public void update(Observable o, Object arg) {
        this.setEndTime((LocalDateTime) arg);
        task.setEndTime((LocalDateTime) arg);
        calculateTime();
    }
}
