public class Teacher extends User implements Element{
    private String firstName, lastName;
    public Teacher(String firstName,String lastName){
        super(firstName,lastName);
        this.firstName=firstName;
        this.lastName=lastName;
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
