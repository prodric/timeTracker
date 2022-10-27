import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Node{

    private List<TimeInterval> timeIntervals;
    private TimeInterval current;
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
        current = timeInterval;
    }

    public TimeInterval getCurrentTimeInterval (){
        return current;
    }
    @Override
    public void calculateTotalTime(){
        for (TimeInterval interval: timeIntervals) {
            this.setWorkingTime(this.getWorkingTime().plus(interval.getTotalWorkingTime()));
        }
    }
}
