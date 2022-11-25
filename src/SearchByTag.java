import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Clase que implementa la interfaz Visitor.
 * SearchByTag nos permite visitar cada nodo del
 * arbol de actividades y buscar los proyectos y tareas con
 * un cierto tag.
 */
public class SearchByTag implements Visitor {

  private String tag;
  private List<String> nodesFound;
  private static final Logger logger = LoggerFactory.getLogger("Fita2");

  /**
   * Constructor por defecto de SearchByTag.
   * param root : Node    nodo raiz del arbol de actividades
   * param tag : String   tag que se buscará en el arbol
   */
  public SearchByTag(Node root, String tag) {
    this.tag = tag;
    nodesFound = new ArrayList<String>();
    logger.info("Searching for tag: {}", this.tag);
    root.acceptVisitor(this);
  }

  /**
   * Metodo que implementa el visitor para buscar el tag en el proyecto.
   * param p : Project    proyecto donde queremos buscar el tag
   */
  @Override
  public void visitProject(Project p) {
    ArrayList<String> projectTags = (ArrayList<String>) p.getTag();

    for (String tag : projectTags) {
      if (tag.equalsIgnoreCase(this.tag)) {
        logger.info("Tag: {} has been found", this.tag);
        nodesFound.add(p.getName());
      }
    }

    for (Node child : p.getChildren()) {
      child.acceptVisitor(this);
    }
  }

  /**
   * Metodo que implementa el visitor para buscar el tag en la/s tarea/s.
   * param t : Task    tarea donde queremos buscar el tag
   */
  @Override
  public void visitTask(Task t) {
    ArrayList<String> taskTags = (ArrayList<String>) t.getTag();

    for (String tag : taskTags) {
      if (tag.equalsIgnoreCase(this.tag)) {
        logger.info("Tag: {} has been found", this.tag);
        nodesFound.add(t.getName());
      }
    }
  }

  @Override
  public void visitTimeInterval(TimeInterval interval) {
    //empty because TimeIntervals don't have tags nor names
  }

  /**
   * Metodo toString sobreescrito para poder printar objetos SearchByTag.
   */
  @Override
  public String toString() {
    StringBuilder string = new StringBuilder(tag + "\t->\t");

    for (String object : nodesFound) {
      string.append(object).append(", ");
    }
    return string.toString().substring(0, string.length() - 2);
  }

}
