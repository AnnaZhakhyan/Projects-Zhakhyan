package datarecords;

public class Employee {
    private String ID;
    private String firstname;
    private String lastname;
    private String position;
    private String username;
    private String password;

    public Employee(String ID, String firstname, String lastname, String position, String username, String password) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.position = position;
        this.username = username;
        this.password = password;
    }

    // Getters and setters for all of this
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String text) {
        this.password = password;
    }
}