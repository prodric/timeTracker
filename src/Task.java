import java.sql.Time;
import java.util.ArrayList;

public class Task extends Node{

    private int taskType;
    private ArrayList<TimeInterval> timeIntervals;
    public Task(String name, Node father) {
        super(name, father);
        this.timeIntervals = new ArrayList<TimeInterval>();
    }
    public void startTask() {
        timeIntervals.add(new TimeInterval(this));
    }

    public void calculateTotalTime(){}
}
