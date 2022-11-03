import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class TreePrinter implements Visitor,Observer {
    private Project root;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public TreePrinter(Node root) {
        this.root = (Project) root;
        Clock.getInstance().addObserver(this);
    }
    /**
     * Funcion que implementa el visitor para mostrar el/los proyecto/s
     */
    public void visitProject(Project p) {
        System.out.printf("%-35s", "Project " + p.getName());
        System.out.printf("%-35s", "child of " + p.getFatherName());
        System.out.printf("%-35s", p.getStartTime());
        System.out.printf("%-35s", p.getEndTime());
        System.out.printf("%-35s %n", p.getTotalWorkingTime().toSeconds());

            for (Node child: p.getChildren()){
                child.acceptVisitor(this);
            }
    }

    /**
     * Funcion que implementa el visitor para mostrar la/s tarea/s
     */
    @Override
    public void visitTask(Task t) {
        System.out.printf("%-35s", "Task " + t.getName());
        System.out.printf("%-35s", "child of " + t.getFatherName());
        System.out.printf("%-35s", t.getStartTime());
        System.out.printf("%-35s", t.getEndTime());
        System.out.printf("%-35s %n", t.getTotalWorkingTime().toSeconds());

        for (TimeInterval interval: t.getTimeIntervals()){
            interval.acceptVisitor(this);
        }
    }

    /**
     * Funcion que implementa el visitor para mostrar el time interval
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
     * Funcion que actualiza el orbserver
     */
    @Override
    public void update(Observable o, Object arg) {
        root.acceptVisitor(this);
    }
}

