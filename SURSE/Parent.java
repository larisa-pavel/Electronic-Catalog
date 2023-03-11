public class Parent extends User implements Observer{
    private String firstName, lastName;
    public Parent(String firstName,String lastName){
        super(firstName,lastName);
    }

    @Override
    public void update(Notification notification) {
        System.out.println(notification.toString());
    }
}
