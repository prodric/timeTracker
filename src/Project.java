import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Project extends Node {
    private ArrayList<Node> children;

    private FileWriter fileWriter;

    private String path = "src/resources/file.json";


    /**
     * Constructor que crea un proyecto
     * @param "void"
     */
    public Project(String name, Project father) {
        super(name, father);
        children = new ArrayList<Node>();
        if (father != null)
            father.children.add(this);
    }

    public Project(JSONObject jsonObject, Project father) {
        super(jsonObject, father);

        for (int i = 0; i < jsonObject.getJSONArray("Object").length(); i++){
            JSONObject jsonObject2 = jsonObject.getJSONArray("Object").getJSONObject(i);

            if (jsonObject2.has("Object")){
                Project project = new Project(jsonObject2, this);
                children.add(project);
            }
        }

    }


    public String getPath(){
        return path;
    }
    /**
     * Getter que recupera el hijo

     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /**
     * Funcion que implementa el visitor para recorrer el/los proyecto/S
     */
    public void acceptVisitor(Visitor visit) throws IOException {
        visit.visitProject(this);
    }


    /**
     * Funcion que actualiza el arbol modificando el tiempo final, el tiempo de trabajo
     */
    @Override
    public void updateTree(Long period, LocalDateTime endTime){
        this.setEndTime(endTime);
        this.setWorkingTime(getTotalWorkingTime().plusSeconds(period));
        if(getFather() != null) {
            this.getFather().setStartTime(this.getStartTime());
            this.getFather().updateTree(period, endTime);
        }

//        System.out.println("Project " + getStartTime());
//        System.out.println("Project " + getEndTime());
//        System.out.println("Project " + getWorkingTime().toSeconds());
    }

    @Override
    public JSONObject toJson(){
        JSONObject jsonObject= new JSONObject();

        jsonObject.put("name", this.getName());
        jsonObject.put("father", this.getFatherName());
        jsonObject.put("startTime", this.getStartTime());
        jsonObject.put("endTime", this.getEndTime());
        jsonObject.put("totalWorkingTime", this.getTotalWorkingTime().toSeconds());

        JSONArray jsonArray = new JSONArray();
        for (Node child: children){
            jsonArray.put(((Node)child).toJson());
        }

        jsonObject.put("Object", jsonArray);

        return jsonObject;
    }

    public void save(String path, JSONObject jsonObject) {

        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(jsonObject.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
