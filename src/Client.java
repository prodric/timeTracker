
public class Client {
    public static void main(String[] args) throws InterruptedException {

        Clock clock = Clock.getInstance();

        Project p1 = new Project("Software Design", null);
        Project p2 = new Project("Software Testing", null);
        Project p3 = new Project("Databases", null);

        Task t5 = new Task("Task Transportation", null);

        Project p5 = new Project("Problems", p1);
        Project p6 = new Project("Project Time Tracker", p1);

        Task t1 = new Task("First List", p5);
        Task t2 = new Task("Second List", p5);

        Task t3= new Task("Read Handout", p6);
        Task t4 = new Task("First Milestone", p6);

        TreePrinter printer = new TreePrinter(p1);
        t1.startTask();
        Thread.sleep(2000);
        t1.stopTask();
        t2.startTask();
        Thread.sleep(4000);
        t2.stopTask();
        clock.stopClock();




    }

    public void testB (){

    }
}
