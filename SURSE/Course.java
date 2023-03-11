import java.util.*;

public abstract class Course{
    private String curs,type,strategy;
    private Teacher teacher;
    private HashSet<Assistant> asistenti;
    private ArrayList<Grade> note;
    private HashMap<String, Group> dictionar;
    public Course(CourseBuilder builder){
        this.curs= builder.course;
        this.type=builder.type;
        this.strategy=builder.strategy;
        this.teacher=builder.teacher;
        this.asistenti=builder.assistants;
        this.dictionar=builder.dictionar;
        note=new ArrayList<>();
    }
    public Teacher getTeacher(){
        return teacher;
    }
    public String getAssistants(){
        int i=0;
        String rez="";
        for(Map.Entry<String, Group> entry:dictionar.entrySet()){
            i++;
            rez+=i+") "+entry.getValue().getAssistant().toString()+" grupa "+entry.getValue().getID()+"\n";
        }
        return rez;
    }
    public HashSet<Assistant> getAssistant(){
        return asistenti;
    }
    //Returnez cursul
    public String getCurs(){
        return curs;
    }
    //returnez dictionarul
    public HashMap<String, Group> getDictionar(){
        return dictionar;
    }
    public void addAssistant(String ID, Assistant assistant){
        asistenti.add(assistant);
        dictionar.get(ID).setAssistant(assistant);
    }
    // Adauga studentul în grupa cu ID-ul indicat
    public void addStudent(String ID, Student student){
        dictionar.get(ID).add(student);
    }
    // Adauga grupa
    public void addGroup(Group group){
        dictionar.put(group.getID(), group);
    }
    public void addGroup(String ID, Assistant assistant){
        Group x=new Group(ID,assistant);
        dictionar.put(x.getID(),x);
    }
    public void addGroup(String ID, Assistant assist, Comparator<Student> comp){
        Group x=new Group(ID,assist,comp);
        dictionar.put(x.getID(),x);
    }
    public Grade getGrade(Student student){
        for(Grade x:note){
            if(x.getStudent().toString().equals(student.toString()))
                return x;
        }
        return null;
    }
    public void addGrade(Grade grade){
        note.add(grade);
    }
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> rez=new ArrayList<>();
        for(Map.Entry<String,Group> entry:dictionar.entrySet()){
            rez.addAll(entry.getValue());
        }
        return rez;
    }
    public HashMap<Student, Grade> getAllStudentGrades(){
        HashMap<Student,Grade> rez=new HashMap<Student,Grade>();
        for(Grade x:note){
            rez.put(x.getStudent(),x);
        }
        return rez;
    }
    public abstract ArrayList<Student> getGraduatedStudents();
    public abstract static class CourseBuilder{
        private String course,type,strategy;
        private Teacher teacher;
        private HashSet<Assistant> assistants;
        private ArrayList<Grade> note;
        private HashMap<String, Group> dictionar;
        public CourseBuilder assistants(HashSet<Assistant> assistants){
            this.assistants=assistants;
            return this;
        }
        public CourseBuilder course(String  course){
            this.course=course;
            return this;
        }
        public CourseBuilder teacher(Teacher teacher){
            this.teacher=teacher;
            return this;
        }
        public CourseBuilder type(String type){
            this.type=type;
            return this;
        }
        public CourseBuilder strategy(String strategy){
            this.strategy=strategy;
            return this;
        }
        public CourseBuilder dictionar(HashMap<String, Group> dictionar){
            this.dictionar=dictionar;
            return this;
        }
        public abstract Course build();
    }
    public Student getBestStudent(String type){
        if(Objects.equals(type, "BestPartialScore")){
            BestPartialScore rez=new BestPartialScore();
            return rez.getTheBest(getAllStudentGrades());
        }
        if(Objects.equals(type, "BestExamScore")){
            BestExamScore rez=new BestExamScore();
            return rez.getTheBest(getAllStudentGrades());
        }
        if(Objects.equals(type, "BestTotalScore")){
            BestTotalScore rez=new BestTotalScore();
            return rez.getTheBest(getAllStudentGrades());
        }
        return null;
    }
    private class Snapshot{
        ArrayList<Grade> notele=new ArrayList<Grade>();
    }
    public void makeBackup() {
        for(Grade x:note){
            Grade y=x.clone();
            backup.notele.add(y);
        }
    }
    public void undo() {
        note.clear();
        for(Grade x: backup.notele){
            Grade y=x.clone();
            note.add(y);
        }
    }
    private Snapshot backup;
}