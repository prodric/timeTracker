import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Checkstyle me cago en tu puta madre.
 */
public class SearchByTag implements Visitor {

  private String tag;
  private List<String> nodesFound;
  private static final Logger logger = LoggerFactory.getLogger("Fita2");

  /**
   * Constructor de tu puta madre.
   */
  public SearchByTag(Node root, String tag) {
    this.tag = tag;
    nodesFound = new ArrayList<String>();
    logger.info("Searching for tag: {}", this.tag);
    root.acceptVisitor(this);
  }

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

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder(tag + "\t->\t");

    for (String object : nodesFound) {
      string.append(object).append(", ");
    }
    return string.toString().substring(0, string.length() - 2);
  }

}
