public class Student extends User{
    private String firstName, lastName;
    private Parent mother, father;
    public Student(String firstName,String lastName){
        super(firstName,lastName);
    }
    public void setMother(Parent mother) {
        this.mother=mother;
    }
    public void setFather(Parent father) {
        this.father=father;
    }
    public Parent getMother(){
        return mother;
    }
    public Parent getFather(){
        return father;
    }
}
