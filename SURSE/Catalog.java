import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Catalog implements Subject{
    private List<Observer> observers = new ArrayList<Observer>();
    private static Catalog instance= new Catalog();
    private ArrayList<Course> cursuri;
    private Catalog(){
        cursuri= new ArrayList<Course>();
    }
    public ArrayList<Course> getCourse(){
        return cursuri;
    }
    public static Catalog getInstance(){
        return instance;
    }
    public void addCourse(Course course) {
        cursuri.add(course);
    }
    public void removeCourse(Course course) {
        cursuri.remove(course);
    }
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        System.out.println(grade.getStudent().getMother().toString()+" "+grade.getStudent().getMother()+"your child obtained "+grade+" in "+grade.getCourse());
    }
}
