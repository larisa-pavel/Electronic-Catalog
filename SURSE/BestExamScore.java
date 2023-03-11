import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BestExamScore implements Strategy {
    @Override
    public Student getTheBest(HashMap<Student, Grade> grades) {
        Student rez= new Student("","");
        double nota=0;
        for(Map.Entry<Student, Grade> entry:grades.entrySet()){
            if(entry.getValue().getExamScore()>=nota){
                nota=entry.getValue().getExamScore();
                rez=entry.getKey();
            }
        }
        return rez;
    }
}
