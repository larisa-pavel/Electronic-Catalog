import java.util.*;

public class ScoreVisitor implements Visitor{
    private class Tuple<A,B,C>{
        A student;
        B course;
        C grade;
        public Tuple(A student, B course, C grade){
            this.student=student;
            this.course=course;
            this.grade=grade;
        }
    }
    HashMap<Teacher,ArrayList<Tuple<Student, String, Double>>> examScores;
    HashMap<Assistant,ArrayList<Tuple<Student, String, Double>>> partialScores;
    public ScoreVisitor(String x){
        if(x=="partialScores") {
            partialScores = new HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>>();
        }
        if(x=="examScores") {
            examScores = new HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>>();
        }
    }
    public void init(Assistant assistant,Student student,Double grade, String course){
        for(Map.Entry<Assistant, ArrayList<Tuple<Student, String, Double>>> entry:partialScores.entrySet()){
            if(entry.getKey().equals(assistant)){
                ArrayList<Tuple<Student, String, Double>> x=entry.getValue();
                x.add(new Tuple<>(student,course,grade));
                partialScores.replace(assistant,x);
                return;
            }
        }
        ArrayList<Tuple<Student, String, Double>> x=new ArrayList<>();
        x.add(new Tuple<>(student,course,grade));
        partialScores.put(assistant,x);
    }
    public void init(Teacher teacher,Student student,Double grade, String course){
        for(Map.Entry<Teacher, ArrayList<Tuple<Student, String, Double>>> entry:examScores.entrySet()){
            if(entry.getKey().equals(teacher)){
                ArrayList<Tuple<Student, String, Double>> x=entry.getValue();
                x.add(new Tuple<>(student,course,grade));
                examScores.replace(teacher,x);
                return;
            }
        }
        ArrayList<Tuple<Student, String, Double>> x=new ArrayList<>();
        x.add(new Tuple<>(student,course,grade));
        examScores.put(teacher,x);
    }
    @Override
    public void visit(Assistant assistant) {
        for(Map.Entry<Assistant, ArrayList<Tuple<Student, String, Double>>> entry:partialScores.entrySet()){
            if(entry.getKey().equals(assistant)){
                ArrayList<Tuple<Student, String, Double>> note=entry.getValue();
                for(Tuple<Student, String, Double> x:note){
                    for(Course i:Catalog.getInstance().getCourse()){
                        if(i.getCurs().equals(x.course)) {
                            Grade rez = i.getGrade(x.student);
                            if (rez != null) {
                                rez.setPartialScore(x.grade);
                            } else {
                                rez = new Grade(x.student, x.course, 0d, x.grade);
                            }
                            i.addGrade(rez);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void visit(Teacher teacher) {
        for(Map.Entry<Teacher, ArrayList<Tuple<Student, String, Double>>> entry:examScores.entrySet()){
            if(entry.getKey().equals(teacher)){
                ArrayList<Tuple<Student, String, Double>> note=entry.getValue();
                for(Tuple<Student, String, Double> x:note){
                    for(Course i:Catalog.getInstance().getCourse()){
                        if(i.getCurs().equals(x.course)) {
                            Grade rez = i.getGrade(x.student);
                            if (rez != null) {
                                rez.setExamScore(x.grade);
                            } else {
                                rez = new Grade(x.student, x.course, 0d, x.grade);
                            }
                            i.addGrade(rez);
                        }
                    }
                }
            }
        }
    }

}
