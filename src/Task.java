import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Node{

    private ArrayList<TimeInterval> timeIntervals;
    public Task(String name, Node father) {
        super(name, father);
        this.timeIntervals = new ArrayList<TimeInterval>();
        workingTime = Duration.ZERO;
    }
    public void startTask() {
        startTime = LocalDateTime.now();
        endTime = LocalDateTime.now();

        TimeInterval timeInterval = new TimeInterval(this);
        timeInterval.setStartTime(startTime);
        timeInterval.setEndTime(endTime);
        timeIntervals.add(timeInterval);
    }




    public void calculateTotalTime(){}
}
