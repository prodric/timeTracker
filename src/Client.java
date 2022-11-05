import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;



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

        Clock clock = Clock.getInstance();
        Thread.sleep(1500);

        Project root = new Project ("root", null);
        Project p1 = new Project("Software Design", root);
        Project p2 = new Project("Software Testing", root);
        Project p3 = new Project("Databases", root);

        Task t5 = new Task("Task Transportation", root);

        Project p5 = new Project("Problems", p1);
        Project p6 = new Project("Project Time Tracker", p1);

        Task t1 = new Task("First List", p5);
        Task t2 = new Task("Second List", p5);

        Task t3 = new Task("Read Handout", p6);
        Task t4 = new Task("First Milestone", p6);

        TreePrinter printer = new TreePrinter(root);
        //Persistence persistence= new Persistence(root);

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


        clock.stopClock();

        return root;
    }

    public static void testPersistence() throws InterruptedException {
        Project root = testB();

        root.save(root.getPath(), root.toJson());
    }

    public static void loadTest(String path) {


//        InputStream is = Client.class.getResourceAsStream(path);
//        //InputStream is = new FileInputStream(path);
//        if (is == null) {
//            throw new NullPointerException("Cannot find resource file " + path);
//        }
//
//        //JSONTokener tokener = new JSONTokener(is);
//        JSONObject object = new JSONObject(tokener);
//
//
//        JSONArray children = object.getJSONArray("Object");
//        for (int i = 0; i < children.length(); i++) {
//            System.out.println("  - " + children.get(i));
//        }

        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(tokener);

        Project rootLoad = new Project(jsonObject, null);

    }
/*
    public static void getKey(JSONObject json, String key) {
        boolean exists = json.has(key);
        Iterator < ? > keys;
        String nextKeys;

        if (!exists) {
            keys = json.keys();
            while (keys.hasNext()) {
                nextKeys = (String) keys.next();
                try {
                    if (json.get(nextKeys) instanceof JSONObject) {
                        if (exists == false) {
                            getKey(json.getJSONObject(nextKeys), key);
                        }
                    } else if (json.get(nextKeys) instanceof JSONArray) {
                        JSONArray jsonarray = json.getJSONArray(nextKeys);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            String jsonarrayString = jsonarray.get(i).toString();
                            JSONObject innerJSOn = new JSONObject(jsonarrayString);
                            if (exists == false) {
                                getKey(innerJSOn, key);
                            }
                        }
                    }
                } catch (Exception e) {}
            }
        } else {
            parseObject(json, key);
        }
    }

    public static void parseObject(JSONObject json, String key) {
        System.out.println("Key : "+key+" has value : "+json.get(key));
    }*/
}

