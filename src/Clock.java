import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * La classe Clock nos permite iniciar y parar el reloj,
 * implementando el patron creacional Singleton,
 * con el fin de que el reloj se instancie una unica vez y por
 * otro lado proporcione un punto de acceso global para todo el
 * proyecto.
 */
public class Clock extends Observable {
  private static final long period = 2;
  private static Clock uniqueInstance = null;
  private TimerTask timerTask;
  private Timer timer;
  private boolean stopClock = false;
  private static final Logger logger = LoggerFactory.getLogger("Fita1");


  /**
   * Constructor que crea un thread que llama a la funcion tick() cada periodo de tiempo.
   */
  private Clock() {
    timer = new Timer();
    timerTask = new TimerTask() {
      @Override
      public void run() {
        tick();
        if (stopClock) {   //si es true los Threads en lista de espera se cancelan,
          timer.cancel();   // el actual termina su ejecucion
        }
      }
    };
    timer.scheduleAtFixedRate(timerTask, 0, period * 1000);

  }

  /**
   * Implementacion del Singletone para obtener la unica instancia del reloj.
   */
  public static Clock getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new Clock();
    }
    logger.trace("Instance of Clock is being used");
    return uniqueInstance;
  }

  /**
   * Metodo que marca el estado del Observable a Changed para poder notificar a todos los observers.
   */
  private void tick() {
    logger.trace("Clock Tick");
    setChanged();
    notifyObservers();
  }

  /**
   * Getter que devuelve el valor del periodo.
   */
  public long getPeriod() {
    return period;
  }

  /**
   * Metodo que detiene el reloj mediante un flag.
   */
  public void stopClock() {
    logger.trace("Clock STOPPED");
    stopClock = true;
  }

}
