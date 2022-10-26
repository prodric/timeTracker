import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Node {
    private String name;
    protected Node father;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected Duration workingTime;

    public Node(String name, Node father) {
        this.name = name;
        this.father = father;
    }

    public Node getFather() {
        return father;
    }

    public String getName() {
        return name;
    }

    public Duration getWorkingTime() {
        return workingTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setWorkingTime(Duration workingTime) {
        this.workingTime = workingTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void calculateTotalTime(){}
    public void createNewTask(){}
    public void createNewSubProject(){}

}
