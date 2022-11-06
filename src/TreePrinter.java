import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * TreePrinter nos permite printar todo el arbol, gracias
 * a visitar los nodos del proyecto principal cada vez que se actualiza
 * el clock.
 */
public class TreePrinter implements Visitor,Observer {
    private Project root;
    public TreePrinter(Node root) {
        this.root = (Project) root;
        Clock.getInstance().addObserver(this);
    }
    /**
     * Metodo que implementa el visitor para mostrar el/los proyecto/s
     * @param p : Project    proyecto que queremos mostrar
     * @return "void"
     */
    public void visitProject(Project p) {
        if (!(p.getStartTime() == null)){
            System.out.printf("%-35s", "Project " + p.getName());
            System.out.printf("%-35s", "child of " + p.getFatherName());
            System.out.printf("%-35s", p.getStartTime());
            System.out.printf("%-35s", p.getEndTime());
            System.out.printf("%-35s %n", p.getTotalWorkingTime().toSeconds());

            for (Node child: p.getChildren()){
                child.acceptVisitor(this);
            }
        }
    }

    /**
     * Metodo que implementa el visitor para mostrar la/s tarea/s
     * @param t : Task    tarea que queremos mostrar
     * @return "void"
     */
    @Override
    public void visitTask(Task t) {
        if (!(t.getStartTime() == null)) {
            System.out.printf("%-35s", "Task " + t.getName());
            System.out.printf("%-35s", "child of " + t.getFatherName());
            System.out.printf("%-35s", t.getStartTime());
            System.out.printf("%-35s", t.getEndTime());
            System.out.printf("%-35s %n", t.getTotalWorkingTime().toSeconds());

            for (TimeInterval interval : t.getTimeIntervals()) {
                interval.acceptVisitor(this);
            }
        }
    }

    /**
     * Metodo que implementa el visitor para mostrar el/los intervalo/s
     * @param interval : TimeInterval    TimeInterval que queremos mostrar
     * @return "void"
     */
    @Override
    public void visitTimeInterval(TimeInterval interval) {
        System.out.printf("%-35s", "Interval ");
        System.out.printf("%-35s", "child of " + interval.getTaskName());
        System.out.printf("%-35s", interval.getStartTime());
        System.out.printf("%-35s", interval.getEndTime());
        System.out.printf("%-35s %n", interval.getTotalWorkingTime().toSeconds());
    }

    /**
     * Funcion que actualiza el visitor cada vez que recibe un aviso del observable
     * Sirve para printar todo el arbol x periodo de tiempo
     */
    @Override
    public void update(Observable o, Object arg) {
            root.acceptVisitor(this);
    }
}

