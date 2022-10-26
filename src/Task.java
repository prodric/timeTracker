import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Node{

    private ArrayList<TimeInterval> timeIntervals;
    public Task(String name, Node father) {
        super(name, father);
        this.timeIntervals = new ArrayList<TimeInterval>();
        setWorkingTime(Duration.ZERO);
    }

    public void startTask() {
        setStartTime(LocalDateTime.now());
        setEndTime(LocalDateTime.now());

        TimeInterval timeInterval = new TimeInterval(this);
        timeInterval.setStartTime(getStartTime());
        timeInterval.setEndTime(getEndTime());
        timeIntervals.add(timeInterval);
    }


    public void calculateTotalTime(){
        for (TimeInterval interval: timeIntervals) {
            setWorkingTime(getWorkingTime().plus(interval.getTotalWorkingTime()));
        }
    }
}
