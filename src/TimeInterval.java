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

    public void update(){}
}
