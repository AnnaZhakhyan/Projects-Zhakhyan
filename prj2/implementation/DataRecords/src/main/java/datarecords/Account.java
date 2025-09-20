package datarecords;

public class Account {

    String firstname;
    String lastname;
    String role;
    int id;

    public Account(String firstname, String lastname, String role, int id){
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.id = id;
    }
    
    public String getFirstname(){return firstname;}
    public String getLastname(){return lastname;}
    public String getRole(){return role;}
    public String getId(){return String.valueOf(id);}
}
