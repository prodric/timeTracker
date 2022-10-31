import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Node{

    private List<TimeInterval> timeIntervals;
    private final Clock clock = Clock.getInstance();
    public Task(String name, Node father) {
        super(name, father);
        this.timeIntervals = new ArrayList<TimeInterval>();
    }

    public void startTask() {
        if (getStartTime() == null && getEndTime() == null) {
            setStartTime(LocalDateTime.now());
            setEndTime(LocalDateTime.now());
        }

        TimeInterval timeInterval = new TimeInterval(this);
        timeIntervals.add(timeInterval);
        clock.addObserver(timeInterval);
    }

    public void stopTask(){
            clock.deleteObservers();
    }

    public void acceptVisitor(Visitor visit) {
        visit.task(this);
    }
}
