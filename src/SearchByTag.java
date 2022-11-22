import java.util.ArrayList;
import java.util.List;

/**
 * Checkstyle me cago en tu puta madre.
 */
public class SearchByTag implements Visitor {

  private String tag;
  private List<String> nodesFound;

  /**
   * Constructor de tu puta madre.
   */
  public SearchByTag(Node root, String tag) {
    this.tag = tag;
    nodesFound = new ArrayList<String>();
    root.acceptVisitor(this);
  }

  @Override
  public void visitProject(Project p) {
    ArrayList<String> projectTags = (ArrayList<String>) p.getTag();

    for (String tag : projectTags) {
      if (tag.toUpperCase().equals(this.getTag().toUpperCase())) {
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
      if (tag.toUpperCase().equals(this.getTag().toUpperCase())) {
        nodesFound.add(t.getName());
      }
    }
  }

  @Override
  public void visitTimeInterval(TimeInterval interval) {}


  public String getTag() {
    return tag;
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
