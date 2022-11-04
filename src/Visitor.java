import java.io.IOException;

public interface Visitor {
    /*
    * Metodos que deberan implementar las classes que deriven de esta interface
    *
    * */
    void visitProject(Project p) throws IOException;
    void visitTask(Task t) throws IOException;
    void visitTimeInterval(TimeInterval interval) throws IOException;
}
