import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Node {
    private String name;
    private Node father;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration totalWorkingTime;

    /**
     * Constructor que crea un nodo
     * @param "void"
     */
    public Node(String name, Project father) {
        this.name = name;
        this.father = father;
        totalWorkingTime = Duration.ZERO;
        startTime = null;
        endTime = null;
    }

    /**
     * Funcion que implementa el Visitor
     */
    public abstract void acceptVisitor(Visitor visit);

    /**
     * Funcion que actualiza el arbol durante el periodo
     */
    public abstract void updateTree(Long period, LocalDateTime endTime);

    /**
     * Getter que recupera el padre del proyecto/tarea
     */
    protected Node getFather() {
        return father;
    }

    /**
     * Getter que recupera el nombre del padre
     */
    public String getFatherName(){
        if(father != null)
            return father.getName();
        return null;
    }

    /**
     * Getter que devuelve el nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Getter que devuelve el tiempo total de trabajo
     */
    public Duration getWorkingTime() {
        return totalWorkingTime;
    }

    /**
     * Getter que devuelve el tiempo de inicio
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }

    /**
     * Getter que devuelve el tiempo final
     */
    public LocalDateTime getEndTime(){
        return endTime;
    }

    /**
     * Setter que edita el valor del tiempo inicial
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Setter que edita el valor del tiempo de trabajo
     */
    public void setWorkingTime(Duration workingTime) {
        this.totalWorkingTime = workingTime;
    }

    /**
     * Setter que edita el valor del tiempo final
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
