import java.io.IOException;

public interface Visitor {
    /*
    * Metodos que deberan implementar las classes que deriven de esta interface
    *
    * */
    void visitProject(Project p);
    void visitTask(Task t);
    void visitTimeInterval(TimeInterval interval);
}
