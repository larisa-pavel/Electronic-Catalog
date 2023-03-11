import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader= new FileReader("input.json")){
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject=(JSONObject) obj;
            JSONArray courses = (JSONArray) jsonObject.get("courses");
            Catalog catalog=Catalog.getInstance();
            Course course;
            for(Object x:courses){
                JSONObject y = (JSONObject) x;
                String type = (String) y.get("type");
                String strategy=(String)y.get("strategy");
                String name=(String)y.get("name");
                JSONObject jsonTeacher=(JSONObject) y.get("teacher");
                String firstName=(String)jsonTeacher.get("firstName");
                String lastName=(String)jsonTeacher.get("lastName");
                UserFactory userFactory=new UserFactory();
                Teacher teacher=(Teacher) userFactory.getUser("teacher",firstName,lastName);
                HashMap<String, Group> dictionar=new HashMap<>();
                HashSet<Assistant> asistenti=new HashSet<Assistant>();
                JSONArray assistants=(JSONArray) y.get("assistants");
                for(Object assistant:assistants){
                    JSONObject asistent=(JSONObject) assistant;
                    firstName=(String) asistent.get("firstName");
                    lastName=(String) asistent.get("lastName");
                    UserFactory user=new UserFactory();
                    asistenti.add((Assistant) user.getUser("assistant",firstName,lastName));
                }
                JSONArray groups=(JSONArray) y.get("groups");
                for(Object group:groups){
                    JSONObject grupuri=(JSONObject) group;
                    String ID=(String) grupuri.get("ID");
                    JSONObject jsonAssistant=(JSONObject) grupuri.get("assistant");
                    firstName=(String) jsonAssistant.get("firstName");
                    lastName=(String) jsonAssistant.get("lastName");
                    UserFactory user=new UserFactory();
                    Assistant assistant=(Assistant) user.getUser("assistant",firstName,lastName);
                    if(asistenti.contains(assistant)){
                        for (Course curs:Catalog.getInstance().getCourse()){
                            if(curs.getCurs().contains(name)){
                                curs.addAssistant(ID,assistant);
                            }
                        }
                    }
                    Group mareleGrup=new Group(ID,assistant);
                    JSONArray students=(JSONArray) grupuri.get("students");
                    for(Object names:students){
                        JSONObject student=(JSONObject) names;
                        firstName=(String) student.get("firstName");
                        lastName=(String) student.get("lastName");
                        Student studentul=(Student) user.getUser("student",firstName,lastName);
                        JSONObject jsonMother=(JSONObject) student.get("mother");
                        if (jsonMother != null) {
                            firstName=(String) jsonMother.get("firstName");
                            lastName=(String) jsonMother.get("lastName");
                            Parent mama=(Parent) user.getUser("parent",firstName,lastName);
                            studentul.setMother(mama);
                        }
                        JSONObject jsonFather=(JSONObject) student.get("father");
                        if(jsonFather!=null){
                            firstName=(String) jsonFather.get("firstName");
                            lastName=(String) jsonFather.get("lastName");
                            Parent tata=(Parent) user.getUser("parent",firstName,lastName);
                            studentul.setFather(tata);
                        }
                        mareleGrup.add(studentul);
                    }
                    dictionar.put(ID,mareleGrup);
                }
                if(type.equals("FullCourse")){
                    FullCourse.FullCourseBuilder builder= (FullCourse.FullCourseBuilder) new FullCourse.FullCourseBuilder().assistants(asistenti);
                    builder= (FullCourse.FullCourseBuilder) builder.teacher(teacher);
                    builder=(FullCourse.FullCourseBuilder) builder.course(name);
                    builder=(FullCourse.FullCourseBuilder) builder.type(type);
                    builder=(FullCourse.FullCourseBuilder) builder.strategy(strategy);
                    builder=(FullCourse.FullCourseBuilder) builder.dictionar(dictionar);
                    course= builder.build();
                    catalog.addCourse(course);
                }
                else if(type.equals("PartialCourse")){
                    PartialCourse.PartialCourseBuilder builder= (PartialCourse.PartialCourseBuilder) new PartialCourse.PartialCourseBuilder().assistants(asistenti);
                    builder=(PartialCourse.PartialCourseBuilder) builder.teacher(teacher);
                    builder=(PartialCourse.PartialCourseBuilder) builder.course(name);
                    builder=(PartialCourse.PartialCourseBuilder) builder.type(type);
                    builder=(PartialCourse.PartialCourseBuilder) builder.strategy(strategy);
                    builder=(PartialCourse.PartialCourseBuilder) builder.dictionar(dictionar);
                    course=builder.build();
                    catalog.addCourse(course);
                }

            }
            ScoreVisitor scoreVisitor=new ScoreVisitor("examScores");
            JSONArray examScores= (JSONArray) jsonObject.get("examScores");
            for(Object x:examScores){
                JSONObject getter=(JSONObject) x;
                JSONObject teacher=(JSONObject) getter.get("teacher");
                String firstNameT=(String) teacher.get("firstName");
                String lastNameT= (String) teacher.get("lastName");
                Teacher t=new Teacher(firstNameT,lastNameT);
                JSONObject student=(JSONObject) getter.get("student");
                String firstNameS=(String) student.get("firstName");
                String lastNameS= (String) student.get("lastName");
                Student s=new Student(firstNameS,lastNameS);
                String curs=(String) getter.get("course");
                Double grade;
                if(getter.get("grade").getClass().equals(Double.class)){
                    grade=(Double) getter.get("grade");
                }else{
                    Long y=(Long) getter.get("grade");
                    grade=y.doubleValue();
                }
                scoreVisitor.init(t,s,grade,curs);
                t.accept(scoreVisitor);
            }
            JSONArray partialScores= (JSONArray) jsonObject.get("partialScores");
            ScoreVisitor scoreVisitor1=new ScoreVisitor("partialScores");
            for(Object x:partialScores){
                JSONObject getter=(JSONObject) x;
                JSONObject assistant=(JSONObject) getter.get("assistant");
                String firstName=(String) assistant.get("firstName");
                String lastName= (String) assistant.get("lastName");
                Assistant a=new Assistant(firstName,lastName);
                JSONObject student=(JSONObject) getter.get("student");
                String firstNameS=(String) student.get("firstName");
                String lastNameS= (String) student.get("lastName");
                Student s=new Student(firstNameS,lastNameS);
                String curs=(String) getter.get("course");
                Double grade;
                if(getter.get("grade").getClass().equals(Double.class)){
                    grade=(Double) getter.get("grade");
                }else{
                    Long y=(Long) getter.get("grade");
                    grade=y.doubleValue();
                }
                scoreVisitor1.init(a,s,grade,curs);
                a.accept(scoreVisitor1);
            }
            for(Course c:catalog.getCourse()){
                System.out.println("\ncursul: "+c.getCurs());
                System.out.println("profesorul cursului: "+c.getTeacher());
                System.out.println("asistentii cursului:\n"+c.getAssistants());
                System.out.println("Lista studentilor cursului, impreuna cu parintii lor:");
                for(Student s:c.getAllStudents()){
                    System.out.println(s+", mama: "+s.getMother()+", tata: "+s.getFather());
                }
                System.out.println("\nLista notelor studentilor:");
                for(Map.Entry<Student,Grade> entry:c.getAllStudentGrades().entrySet()){
                    System.out.println(entry.getKey()+"\n   examen: "+entry.getValue().getExamScore()+"\n   partial: "+entry.getValue().getPartialScore());
                }
                System.out.println("\nLista studentilor care au trecut materia:");
                for(Student s:c.getGraduatedStudents()){
                    System.out.println(s);
                }
                System.out.println("\nCea mai mare nota la examen: "+c.getBestStudent("BestExamScore")+", cu "+c.getGrade(c.getBestStudent("BestExamScore")).getExamScore());
                System.out.println("Cea mai mare nota la partial: "+c.getBestStudent("BestPartialScore")+", cu "+c.getGrade(c.getBestStudent("BestPartialScore")).getPartialScore());
                System.out.println("Cea mai mare nota finala: "+c.getBestStudent("BestTotalScore")+", cu "+c.getGrade(c.getBestStudent("BestTotalScore")).getTotal());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
