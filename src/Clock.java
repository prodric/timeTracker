import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
    private TimerTask timerTask;
    private Timer timer;
    private static Clock uniqueInstance = null;
    private boolean stopClock = false;

    public Clock() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                tick();
                if(stopClock)
                    timer.cancel();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

    }
    private void tick(){
        setChanged();
        notifyObservers(LocalDateTime.now());
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
