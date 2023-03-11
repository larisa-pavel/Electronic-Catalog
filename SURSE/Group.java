import java.util.ArrayList;
import java.util.Comparator;

public class Group extends ArrayList<Student> {
    private String ID;
    private Assistant assistant;
    private Comparator<Student> comp;
    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.assistant=assistant;
        this.ID=ID;
        this.comp=comp;
    }
    public Group(String ID, Assistant assistant) {
        this.ID=ID;
        this.assistant=assistant;
    }
    public String getID(){
        return ID;
    }
    public void setID(String ID){
        this.ID=ID;
    }
    public Assistant getAssistant(){
        return assistant;
    }
    public void setAssistant(Assistant assistant){
        this.assistant=assistant;
    }
    public Comparator<Student> getComp(){
        return comp;
    }
    public void setComp(Comparator<Student> comp){
        this.comp=comp;
    }
}
