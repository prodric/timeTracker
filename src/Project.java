import java.util.ArrayList;

public class Project extends Node {
    private ArrayList<Node> nodes;

    public Project(String name, Node father) {
        super(name, father);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public void addNode(Node child) {
        nodes.add(child);
    }
    public void removeNode(Node child) {
        nodes.remove(child);
    }
}
