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
        totalWorkingTime = Duration.ZERO;
        startTime = null;
        endTime = null;
    }

    public abstract void acceptVisitor(Visitor visit);

    private Node getFather() {
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

    public void updateTree(Long period, LocalDateTime endTime){
        this.setEndTime(endTime);
        this.setWorkingTime(getWorkingTime().plusSeconds(period));
        this.getFather().updateTree(period, endTime);
    }

    @Override
    public String toString() {
        return "   " + name + " " + startTime + "  " + endTime + "   " + totalWorkingTime;
    }

}
