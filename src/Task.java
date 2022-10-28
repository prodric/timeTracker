import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Node{

    private List<TimeInterval> timeIntervals;
    public Task(String name, Node father) {
        super(name, father);
        this.timeIntervals = new ArrayList<TimeInterval>();
        setWorkingTime(Duration.ZERO);
    }

    public void startTask() {
        setStartTime(LocalDateTime.now());
        setEndTime(LocalDateTime.now());

        Clock clock = Clock.getInstance();

        TimeInterval timeInterval = new TimeInterval(this);
        timeIntervals.add(timeInterval);
        clock.addObserver(timeInterval);
    }

    @Override
    public void calculateTotalTime(){

    }
}
