import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Project extends Node {
    private ArrayList<Node> children;
    private String path = "file.json";


    /**
     * Constructor que crea un proyecto, añadiendolo como proyecto hijo en el caso de que no sea el root
     * @param name : String     nombre del proyecto
     * @param father : Project   padre del proyecto
     * @return "void"
     */
    public Project(String name, Project father) {
        super(name, father);
        children = new ArrayList<Node>();
        if (father != null)
            father.children.add(this);
    }

    /**
     * Constructor que crea un proyecto a partir de un JSONObject
     * @param jsonObject : JSONObject     JSONObject del cual se extrae la informacion para reconstruir el proyecto
     * @param father : Project   padre del proyecto
     * @return "void"
     */
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
     * Getter que devuelve la lista de los hijos del proyecto
     * @return ArrayList<Node>  lista con los hijos
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /**
     * Metodo que acepta el visitor para recorrer el/los proyecto/s y realizar una operacion determinada por el visitor
     * @param visit : Visitor   objeto de la clase Visitor que pasamos al metodo para poder realizar la llamada a visitProject()
     * @return "void"
     */
    public void acceptVisitor(Visitor visit) throws IOException {
        visit.visitProject(this);
    }


    /**
     * Metodo que actualiza el arbol modificando el endTime, el totalWorkingTime
     * @param period : Long     periodo de tiempo que tenemos que sumar a las variables LocalDateTime
     * @param endTime : LocalDateTime  variable usada para actualizar endTime a los padres de la tarea
     * @return "void"
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

    /**
     * Metodo que convierte un proyecto a un objeto JSON, también convierte a cada proyecto de la lista children
     * @param "void"
     * @return JSONObject
     */
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

    /**
     * Metodo que guarda el jsonObject en el directorio especificado
     * @param path : String     nombre del directorio
     * @param jsonObject : JSONObject
     * @return JSONObject
     */
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
