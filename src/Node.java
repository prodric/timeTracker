import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Node {
    private String name;
    private Node father;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration totalWorkingTime;

    public Node(String name, Project father) {
        this.name = name;
        this.father = father;
        totalWorkingTime = Duration.ZERO;
        startTime = null;
        endTime = null;
    }

    public abstract void acceptVisitor(Visitor visit);
    public abstract void updateTree(Long period, LocalDateTime endTime);

    protected Node getFather() {
        return father;
    }
    public String getFatherName(){
        if(father != null)
            return father.getName();
        return null;
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

}
