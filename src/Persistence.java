import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Persistence implements Visitor{
  private String path = "TimeTrackerGit/Persistence.txt";
  private Project root;
  private FileWriter file;

  public Persistence(Node root){
    this.root = (Project) root;
    root.acceptVisitor(this);
  }

  @Override
  public void visitProject(Project p) {



    for (Node child: p.getChildren()){
      child.acceptVisitor(this);
    }
  }

  @Override
  public void visitTask(Task t) {
    for (TimeInterval interval: t.getTimeIntervals()){
      interval.acceptVisitor(this);
    }
  }

  @Override
  public void visitTimeInterval(TimeInterval interval) {

  }

  public JSONObject toJson(Project p){
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("name", p.getName());
    jsonObject.put("father", p.getFather());
    jsonObject.put("startTime", p.getStartTime());
    jsonObject.put("endTime", p.getEndTime());
    jsonObject.put("totalWorkingTime", p.getTotalWorkingTime());

    return jsonObject;
  }

  public void save(String path, JSONObject jsonObject) throws IOException {
    String jsonText = jsonObject.toString();
    file = new FileWriter(path);
    file.write(jsonText);
  }

}
