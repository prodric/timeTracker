import java.util.Observer;

public class Client {
    public static void main(String[] args){

        Clock clock = new Clock();

        Project root = new Project("root", null);
        Project p1 = new Project("P1", root);
        Project p2 = new Project("P2", root);
        Project p3 = new Project("P3", root);

        Task t1 = new Task("T1", root);
        Task t2 = new Task("T2", p1);
        Task t3 = new Task("T3", p2);

        clock.addObserver((Observer) t1);
        clock.addObserver((Observer) t2);
        clock.addObserver((Observer) t3);

//        t1.startTask();
//        t2.startTask();
//        t3.startTask();
//        clock.stopTimer();

        System.out.println(clock.countObservers());

    }
}
