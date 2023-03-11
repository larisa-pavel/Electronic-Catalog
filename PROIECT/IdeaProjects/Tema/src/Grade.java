public class Grade implements Comparable, Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course;
    public Grade(Student student, String course, Double partialScore, Double examScore){
        this.student=student;
        this.course=course;
        this.partialScore=partialScore;
        this.examScore=examScore;
    }
    @Override
    public Grade clone(){
        try {
            Grade clone= (Grade) super.clone();
            return clone;
        } catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
    public Double getTotal() {
        return partialScore+examScore;
    }
    public Double getPartialScore(){
        return partialScore;
    }
    public void setPartialScore(Double x){
        partialScore=x;
    }
    public Double getExamScore(){
        return examScore;
    }
    public void setExamScore(Double x){
        examScore=x;
    }
    public Student getStudent(){
        return student;
    }
    public void setStudent(Student x){
        student=x;
    }
    public String getCourse(){
        return course;
    }
    public void setCourse(String x){
        course=x;
    }

    @Override
    public int compareTo(Object o) {
        if(this.getTotal()>((Grade)o).getTotal())
            return -1;
        if(this.getTotal()<((Grade)o).getTotal())
            return 1;
        return 0;
    }
}
