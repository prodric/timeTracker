import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class Client {
  public static void main(String[] args) throws InterruptedException, IOException {
    //testA();
    testB();
    //testPersistence();
    //loadTest("file.json");

  }

  public static void testA() {

    Project p1 = new Project("Software Design", null);
    Project p2 = new Project("Software Testing", null);
    Project p3 = new Project("Databases", null);

    Task t5 = new Task("Task Transportation", null);

    Project p5 = new Project("Problems", p1);
    Project p6 = new Project("Project Time Tracker", p1);

    Task t1 = new Task("First List", p5);
    Task t2 = new Task("Second List", p5);

    Task t3 = new Task("Read Handout", p6);
    Task t4 = new Task("First Milestone", p6);

  }

  public static Project testB() throws InterruptedException {


    Thread.sleep(1500);

    Project root = new Project("root", null);
    Project p1 = new Project("Software Design", root);
    Project p2 = new Project("Software Testing", root);
    Project p3 = new Project("Databases", root);

    Task t5 = new Task("Task Transportation", root);

    Project p5 = new Project("Problems", p1);
    Project p6 = new Project("Project Time Tracker", p1);

    final Task t1 = new Task("First List", p5);
    final Task t2 = new Task("Second List", p5);

    Task t3 = new Task("Read Handout", p6);
    Task t4 = new Task("First Milestone", p6);

    TreePrinter printer = new TreePrinter(root);

    System.out.println("Start Test\n");
    System.out.println("\nTransportation Starts\n");
    t5.startTask();
    Thread.sleep(6000);
    t5.stopTask();
    System.out.println("\nTransportation Stops\n");
    Thread.sleep(2000);
    System.out.println("\nFirst List Starts\n");
    t1.startTask();
    Thread.sleep(6000);
    System.out.println("\nSecond List Starts\n");
    t2.startTask();
    Thread.sleep(4000);
    System.out.println("\nFirst List Stops\n");
    t1.stopTask();
    Thread.sleep(2000);
    System.out.println("\nSecond List Stops\n");
    t2.stopTask();
    Thread.sleep(2000);
    System.out.println("\nTransportation Starts\n");
    t5.startTask();
    Thread.sleep(4000);
    System.out.println("\nTransportation Stops\n");
    t5.stopTask();


    Clock.getInstance().stopClock();

    return root;
  }

  public static void testPersistence() throws InterruptedException {
    Project root = testB();

    root.save(root.getPath(), root.toJson());
  }

  public static void loadTest(String path) {
    JSONTokener tokenizer = null;
    try {
      tokenizer = new JSONTokener(new FileReader(path));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    JSONObject jsonObject = new JSONObject(tokenizer);

    Project loadRoot = new Project(jsonObject, null);

    System.out.println("Arbol cargado exitosamente");
  }


}

