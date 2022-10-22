import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Node {
    private char[] name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration workingTime;
    public void calculateTotalTime(){}
    public void createNewTask(){}
    public void createNewSubProject(){}

}
