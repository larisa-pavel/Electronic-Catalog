import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PartialCourse extends Course{
    public PartialCourse(PartialCourseBuilder builder){
        super(builder);
    }
    public ArrayList<Student> getGraduatedStudents(){
        ArrayList<Student> rez=new ArrayList<>();
        HashMap<Student,Grade> grades=getAllStudentGrades();
        for(Map.Entry<Student,Grade> entry:grades.entrySet()){
            if(entry.getValue().getTotal()>=5)
                rez.add(entry.getValue().getStudent());
        }
        return rez;
    }
    public static class PartialCourseBuilder extends CourseBuilder{

        public PartialCourse build(){
            return new PartialCourse(this);
        }
    }
}
