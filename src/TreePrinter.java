public class TreePrinter implements Visitor {

    public TreePrinter(Node n) {
        n.acceptVisitor(this);
    }
    public void project(Project p) {
        System.out.println(p.toString());
    }

    @Override
    public void task(Task t) {
        System.out.println(t.toString());
    }
}
