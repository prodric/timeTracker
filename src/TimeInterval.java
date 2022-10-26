import java.time.Duration;
import java.time.LocalDateTime;

public class TimeInterval {
    private Task task;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration workingTime;
    public TimeInterval(Task task) {
        this.task = task;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void update(){}
}
