import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public class Persistence implements Visitor, Observer {
  private String path = "file.json";
  private Project root;
  private FileWriter fileWriter;

  public Persistence(Node root) throws IOException {
    this.root = (Project) root;
    Clock.getInstance().addObserver(this);
  }

  @Override
  public void visitProject(Project p) throws IOException {
    JSONObject jsonObject = projectToJson(p);
    save(path, jsonObject);

    for (Node child: p.getChildren()){
      child.acceptVisitor(this);
    }
  }

  @Override
  public void visitTask(Task t) throws IOException {
    JSONObject jsonObject = taskToJson(t);
    save(path, jsonObject);

    for (TimeInterval interval: t.getTimeIntervals()){
      interval.acceptVisitor(this);
    }
  }

  @Override
  public void visitTimeInterval(TimeInterval interval) throws IOException {
//    JSONObject jsonObject = timeIntervalToJson(interval);
//    save(path, jsonObject);
  }

  public JSONObject projectToJson(Project p){
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("name", p.getName());
    jsonObject.put("father", p.getFatherName());
    jsonObject.put("startTime", p.getStartTime());
    jsonObject.put("endTime", p.getEndTime());
    jsonObject.put("totalWorkingTime", p.getTotalWorkingTime().toSeconds());

    return jsonObject;
  }

  public JSONObject taskToJson(Task t){
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("name", t.getName());
    jsonObject.put("father", t.getFatherName());
    jsonObject.put("startTime", t.getStartTime());
    jsonObject.put("endTime", t.getEndTime());
    jsonObject.put("totalWorkingTime", t.getTotalWorkingTime().toSeconds());
    jsonObject.put("lastAdded", t.getLast());

    return jsonObject;
  }

  public JSONObject timeIntervalToJson(TimeInterval interval){
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("task", interval.getTask());
    jsonObject.put("startTime", interval.getStartTime());
    jsonObject.put("endTime", interval.getEndTime());
    jsonObject.put("totalWorkingTime", interval.getTotalWorkingTime().toSeconds());

    return jsonObject;
  }

  public void save(String path, JSONObject jsonObject) throws IOException {
    fileWriter = new FileWriter(path);
    fileWriter.write(jsonObject.toString());
    fileWriter.flush();
    fileWriter.close();
  }

  @Override
  public void update(Observable o, Object arg) {
    try {
      root.acceptVisitor(this);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
