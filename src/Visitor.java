public interface Visitor {
    void visitProject(Project p);
    void visitTask(Task t);
    void visitTimeInterval(TimeInterval interval);
}
