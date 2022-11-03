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
    public void visitProject(Project p) {

        System.out.printf("%-35s", "Project " + p.getName());
        System.out.printf("%-35s", "child of " + p.getFatherName());
        System.out.printf("%-35s", p.getStartTime());
        System.out.printf("%-35s", p.getEndTime());
        System.out.printf("%-35s %n", p.getWorkingTime().toSeconds());
        /*
        System.out.println("Project " + p.getName() +
            "\t\t\t" + "child of " + p.getFatherName() +
            "\t\t\t" + p.getStartTime() +
            "\t\t\t" + p.getEndTime() +
            "\t\t\t" + p.getWorkingTime().toSeconds());*/

            for (Node child: p.getChildren()){
                child.acceptVisitor(this);
            }
    }

    @Override
    public void visitTask(Task t) {
        /*System.out.println("Task " + t.getName() +
            "\t\t\t" + "child of " + t.getFatherName()+
            "\t\t\t" + t.getStartTime() +
            "\t\t\t" + t.getEndTime() +
            "\t\t\t" + t.getWorkingTime().toSeconds());
*/
        System.out.printf("%-35s", "Task " + t.getName());
        System.out.printf("%-35s", "child of " + t.getFatherName());
        System.out.printf("%-35s", t.getStartTime());
        System.out.printf("%-35s", t.getEndTime());
        System.out.printf("%-35s %n", t.getWorkingTime().toSeconds());

        for (TimeInterval interval: t.getTimeIntervals()){
            interval.acceptVisitor(this);
        }
    }

    @Override
    public void visitTimeInterval(TimeInterval interval) {
        /*System.out.println("Interval " +
            "\t\t\t" + "child of " + interval.getTaskName() +
            "\t\t\t" + interval.getStartTime().format(formatter) +
            "\t\t\t" + interval.getEndTime().format(formatter) +
            "\t\t\t" + interval.getTotalWorkingTime().toSeconds());*/

        System.out.printf("%-35s", "Interval ");
        System.out.printf("%-35s", "child of " + interval.getTaskName());
        System.out.printf("%-35s", interval.getStartTime());
        System.out.printf("%-35s", interval.getEndTime());
        System.out.printf("%-35s %n", interval.getTotalWorkingTime().toSeconds());
    }

    @Override
    public void update(Observable o, Object arg) {
        root.acceptVisitor(this);
    }
}

