import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Node {
    private String name;
    private Node father;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration totalWorkingTime;

    public Node(String name, Node father) {
        this.name = name;
        this.father = father;
    }

    public abstract void acceptVisitor(Visitor visit);

    public Node getFather() {
        return father;
    }

    public String getName() {
        return name;
    }

    public Duration getWorkingTime() {
        return totalWorkingTime;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setWorkingTime(Duration workingTime) {
        this.totalWorkingTime = workingTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return "   " + name + " " + startTime + "  " + endTime + "   " + totalWorkingTime;
    }
    public void calculateTotalTime(){}

}
