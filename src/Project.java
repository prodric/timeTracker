import java.time.LocalDateTime;
import java.util.ArrayList;

public class Project extends Node {
    private ArrayList<Node> children;


    /**
     * Constructor que crea un proyecto
     * @param "void"
     */
    public Project(String name, Project father) {
        super(name, father);
        children = new ArrayList<Node>();
        if (father != null)
            father.children.add(this);
    }


    /**
     * Getter que recupera el hijo

     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /**
     * Funcion que implementa el visitor para recorrer el/los proyecto/S
     */
    public void acceptVisitor(Visitor visit) {
        visit.visitProject(this);
    }


    /**
     * Funcion que actualiza el arbol modificando el tiempo final, el tiempo de trabajo
     */
    @Override
    public void updateTree(Long period, LocalDateTime endTime){
        this.setEndTime(endTime);
        this.setWorkingTime(getWorkingTime().plusSeconds(period));
        if(getFather() != null) {
            this.getFather().setStartTime(this.getStartTime());
            this.getFather().updateTree(period, endTime);
        }

//        System.out.println("Project " + getStartTime());
//        System.out.println("Project " + getEndTime());
//        System.out.println("Project " + getWorkingTime().toSeconds());
    }

}
