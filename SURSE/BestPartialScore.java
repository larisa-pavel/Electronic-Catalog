import java.util.HashMap;
import java.util.Map;

public class BestPartialScore implements Strategy {
    @Override
    public Student getTheBest(HashMap<Student, Grade> grades) {
        Student rez= new Student("","");
        double nota=0;
        for(Map.Entry<Student, Grade> entry:grades.entrySet()){
            if(entry.getValue().getPartialScore()>=nota){
                nota=entry.getValue().getPartialScore();
                rez=entry.getKey();
            }
        }
        return rez;
    }
}
