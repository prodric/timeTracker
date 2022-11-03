import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
    private TimerTask timerTask;
    private Timer timer;

    private static final long period = 2;
    private static Clock uniqueInstance = null;
    private boolean stopClock = false;


    /**
     * Constructor que crea un thread que llama a la funcion tick() y lo actualiza cada periodo de tiempo
     * @param "void"
     */

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

    /**
     * Se usa para actualizar el Observer a cada tick del reloj
     */
    private void tick(){
        setChanged();
        notifyObservers();
    }

    /**
     * Getter para recuperar el valor del periodo

     */
    public long getPeriod(){
        return period;
    }

    /**
     * Funcion que para el reloj
     */
    public void stopClock(){
        stopClock = true;
    }

    /**
     * Implementacion del SIngletone para obtener la unica instancia del reloj
     */
    public static Clock getInstance() {
        if(uniqueInstance == null)
            uniqueInstance = new Clock();
        return uniqueInstance;
    }

}
