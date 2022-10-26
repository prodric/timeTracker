import java.util.ArrayList;

public class Project extends Node {
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public Project(String name, Node father) {
        super(name, father);
        nodes.add(this);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

}
