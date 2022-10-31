import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
    private TimerTask timerTask;
    private Timer timer;

    private static final long period = 2;
    private static Clock uniqueInstance = null;
    private boolean stopClock = false;


    private Clock() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                tick();
                if(stopClock)
                    timer.cancel();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, period * 1000);

    }
    private void tick(){
        setChanged();
        notifyObservers();
    }

    public long getPeriod(){
        return period;
    }
    public void stopClock(){
        stopClock = true;
    }

    public static Clock getInstance() {
        if(uniqueInstance == null)
            uniqueInstance = new Clock();
        return uniqueInstance;
    }

}
