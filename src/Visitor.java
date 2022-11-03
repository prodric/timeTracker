import java.io.IOException;

public interface Visitor {
    void visitProject(Project p) throws IOException;
    void visitTask(Task t) throws IOException;
    void visitTimeInterval(TimeInterval interval) throws IOException;
}
