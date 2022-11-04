import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Node {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

    public Node(JSONObject jsonObject, Project father) {
        name = jsonObject.getString("name");
        this.father = father;
        startTime = LocalDateTime.parse(jsonObject.getString("startTime"));
        endTime = LocalDateTime.parse(jsonObject.getString("endTime"));
        totalWorkingTime = Duration.ofSeconds(jsonObject.getLong("totalWorkingTime"));
    }

    public abstract void acceptVisitor(Visitor visit) throws IOException;
    public abstract void updateTree(Long period, LocalDateTime endTime);
    protected abstract JSONObject toJson();

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

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

    public Duration getTotalWorkingTime() {
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
