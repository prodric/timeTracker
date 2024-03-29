package core;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe principal que contiene el main del proyecto y los diferentes tests.
 */
public class Client {

  private static final Logger logger = LoggerFactory.getLogger("main.Client");

  /**
   * Main del programa principal que incluye los diferentes tests.
   */
  public static void main(String[] args) throws InterruptedException, IOException {
    //testA();
    //testB();
    testPersistence();
    //loadTest("file.json");
    testSearchByTag();
  }

  /**
   * Test A que comprueba las requisitos de la fita 1.
   */
  public static Project testA() {
    logger.info("Starting Test A");

    final ArrayList<String> softwareDesign = new ArrayList<>(
        Arrays.asList("java", "flutter"));
    final ArrayList<String> softwareTesting = new ArrayList<>(
        Arrays.asList("c++", "Java", "python"));
    final ArrayList<String> databases = new ArrayList<>(
        Arrays.asList("SQL", "python", "C++"));
    final ArrayList<String> firstList = new ArrayList<>(
        Arrays.asList("java"));
    final ArrayList<String> secondList = new ArrayList<>(
        Arrays.asList("Dart"));
    final ArrayList<String> firstMilestone = new ArrayList<>(
        Arrays.asList("java", "IntelliJ"));

    Project root = new Project("root", null);
    Project p1 = new Project("Software Design", root);
    Project p2 = new Project("Software Testing", root);
    Project p3 = new Project("Databases", root);

    Task t5 = new Task("main.Task Transportation", root);

    Project p5 = new Project("Problems", p1);
    Project p6 = new Project("main.Project Time Tracker", p1);

    final Task t1 = new Task("First List", p5);
    final Task t2 = new Task("Second List", p5);

    Task t3 = new Task("Read Handout", p6);
    final Task t4 = new Task("First Milestone", p6);

    p1.setTag(softwareDesign);
    p2.setTag(softwareTesting);
    p3.setTag(databases);
    t1.setTag(firstList);
    t2.setTag(secondList);
    t4.setTag(firstMilestone);

    logger.info("Test A Finished");

    return root;
  }

  /**
   * Test B que comprueba las requisitos de la fita 1.
   */
  public static Project testB() throws InterruptedException {
    logger.info("Starting Test B");

    Project root = new Project("root", null);
    Project p1 = new Project("Software Design", root);
    Project p2 = new Project("Software Testing", root);
    Project p3 = new Project("Databases", root);

    Task t5 = new Task("main.Task Transportation", root);

    Project p5 = new Project("Problems", p1);
    Project p6 = new Project("main.Project Time Tracker", p1);

    final Task t1 = new Task("First List", p5);
    final Task t2 = new Task("Second List", p5);

    Task t3 = new Task("Read Handout", p6);
    Task t4 = new Task("First Milestone", p6);

    TreePrinter printer = new TreePrinter(root);

    Thread.sleep(1500);
    t5.startTask();
    Thread.sleep(6000);
    t5.stopTask();
    Thread.sleep(2000);
    t1.startTask();
    Thread.sleep(6000);
    t2.startTask();
    Thread.sleep(4000);
    t1.stopTask();
    Thread.sleep(2000);
    t2.stopTask();
    Thread.sleep(2000);
    t5.startTask();
    Thread.sleep(4000);
    t5.stopTask();

    Clock.getInstance().stopClock();

    logger.info("Test B Finished");

    return root;
  }

  /**
   * Test que comprueba el requisito de persistencia de la fita 1.
   */
  public static void testPersistence() throws InterruptedException {
    logger.info("Starting Persistence Test");
    Project root = testB();

    root.save(root.getPath(), root.toJson());

    logger.info("Persistence Test Finished");
  }

  /**
   * Test que comprueba las requisitos de la fita 2.
   */
  public static void testSearchByTag() {
    logger.info("Starting Search By Tag Test");
    Project root = testA();

    String[] tags = {"Java", "JAVA", "flutteR", "c++", "pyThon", "SqL", "InTellij", "DaRT"};
    ArrayList<SearchByTag> results = new ArrayList<SearchByTag>();

    for (String tag : tags) {
      SearchByTag searchByTag = new SearchByTag(root, tag);
      results.add(searchByTag);
    }

    for (SearchByTag searchByTag : results) {
      System.out.println(searchByTag);
    }
    System.out.println();

    logger.info("Search By Tag Test Finished");
  }

  /**
   * Test para comprobar que se carga bien el arbol.
   */
  public static void loadTest(String path) {
    logger.info("Starting Load Test");
    JSONTokener tokenizer = null;
    try {
      tokenizer = new JSONTokener(new FileReader(path));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    JSONObject jsonObject = new JSONObject(tokenizer);

    Project loadRoot = new Project(jsonObject, null);

    logger.info("Tree Loaded Successfully");
    logger.info("Load Test Finished");
  }


}

