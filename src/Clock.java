import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * La classe Clock nos permite iniciar y parar el reloj,
 * implementando el patron creacional Singleton,
 * con el fin de que el reloj se instancie una unica vez y por
 * otro lado proporcione un punto de acceso global para todo el
 * proyecto.
 */
public class Clock extends Observable {
    private TimerTask timerTask;
    private Timer timer;

    private static final long period = 2;
    private static Clock uniqueInstance = null;
    private boolean stopClock = false;


    /**
     * Constructor que crea un thread que llama a la funcion tick() cada periodo de tiempo
     * @param "void"
     * @return "void"
     */

    private Clock() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                tick();
                if(stopClock)   //si es true los Threads en lista de espera se cancelan, el actual termina su ejecucion
                    timer.cancel();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, period * 1000);

    }

    /**
     * Metodo que marca el estado del Observable a Changed para poder notificar a todos los observers
     * @param "void"
     * @return "void"
     */
    private void tick(){
        setChanged();
        notifyObservers();
    }

    /**
     * Getter que devuelve el valor del periodo
     * @param "void"
     * @return long
     */
    public long getPeriod(){
        return period;
    }

    /**
     * Metodo que detiene el reloj mediante un flag
     * @param "void"
     * @return long
     */
    public void stopClock(){
        stopClock = true;
    }

    /**
     * Implementacion del Singletone para obtener la unica instancia del reloj
     */
    public static Clock getInstance() {
        if(uniqueInstance == null)
            uniqueInstance = new Clock();
        return uniqueInstance;
    }

}
