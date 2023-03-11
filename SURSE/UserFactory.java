public class UserFactory {
    public User getUser(String username,String fisrtName,String lastName){
        if(username.equals("student"))
            return new Student(fisrtName,lastName);
        if(username.equals("parent"))
            return new Parent(fisrtName,lastName);
        if(username.equals("assistant"))
            return new Assistant(fisrtName,lastName);
        if(username.equals("teacher"))
            return new Teacher(fisrtName,lastName);
        return null;
    }
}
