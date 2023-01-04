package core;

public class SearchById implements Visitor{

  private final Node root;
  private Node found;
  private int id;

  public SearchById (Node root) {
    this.root = root;
    found = null;
    id=0;
  }

  public Node search(int id) {
    this.id = id;
    root.acceptVisitor(this);
    return found;
  }

  @Override
  public void visitProject(Project p) {
    if (p.getId() == id){
      found = (Node) p;
    }

    for (Node child : p.getChildren()) {
      child.acceptVisitor(this);
    }
  }

  @Override
  public void visitTask(Task t) {
    if(t.getId() == id)
      found = (Node) t;
  }

  @Override
  public void visitTimeInterval(TimeInterval interval) {}
}
