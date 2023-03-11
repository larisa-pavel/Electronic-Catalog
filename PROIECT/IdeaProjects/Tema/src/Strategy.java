import java.util.ArrayList;
import java.util.HashMap;

public interface Strategy {
    public abstract Student getTheBest(HashMap<Student, Grade> x);
}
