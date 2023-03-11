import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FullCourse extends Course{
    public FullCourse(FullCourseBuilder builder){
        super(builder);
    }
    public ArrayList<Student> getGraduatedStudents(){
        ArrayList<Student> rez=new ArrayList<>();
        HashMap<Student,Grade> grades=getAllStudentGrades();
        for(Map.Entry<Student,Grade> entry:grades.entrySet()){
            if(entry.getValue().getExamScore()>=2&&entry.getValue().getPartialScore()>=3)
                rez.add(entry.getValue().getStudent());
        }
        return rez;
    }
    public static class FullCourseBuilder extends CourseBuilder{
        public FullCourse build(){
            return new FullCourse(this);
        }
    }
}