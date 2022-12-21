package webserver;

import core.*;
import java.util.ArrayList;
import java.util.Arrays;


public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Node root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start your clock
    Clock clock = Clock.getInstance();
    new WebServer(root);
  }
  public static Project makeTreeCourses() {
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

    return root;
  }
}