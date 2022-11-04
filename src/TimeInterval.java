import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class TimeInterval implements Observer {
    private Task task;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration totalWorkingTime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TimeInterval(Task task) {
        totalWorkingTime = Duration.ZERO;
        startTime = LocalDateTime.now().plusSeconds(Clock.getInstance().getPeriod());
        endTime = LocalDateTime.now();
        this.task = task;
    }

    public TimeInterval(JSONObject jsonObject, Task task) {
        totalWorkingTime = Duration.ofSeconds(jsonObject.getLong("totalWorkingTime"));;
        startTime = LocalDateTime.parse( jsonObject.getString("startTime"), formatter);
        endTime = LocalDateTime.parse( jsonObject.getString("endTime"), formatter);
        this.task = task;
    }

    public Task getTask() {
        return task;
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

    public void acceptVisitor(Visitor visit) throws IOException {
        visit.visitTimeInterval(this);
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("task", this.getTask());
        jsonObject.put("startTime", this.getStartTime());
        jsonObject.put("endTime", this.getEndTime());
        jsonObject.put("totalWorkingTime", this.getTotalWorkingTime().toSeconds());

        return jsonObject;
    }
}
