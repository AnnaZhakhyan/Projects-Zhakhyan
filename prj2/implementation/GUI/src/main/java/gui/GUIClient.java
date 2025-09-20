package gui;

import datarecords.Client;

public class GUIClient {

    String id;
    String first_name;
    String last_name;
    String birthday;

    GUIClient(Client client){
        this.id = client.id();
        this.first_name = client.firstName();
        this.last_name = client.lastName();
        this.birthday = client.birthday();
    }

    public String getId() {return String.valueOf(id);}
    public String getFirst_name() {return first_name;}
    public String getLast_name() {return last_name;}
    public String getFullName() {return first_name + " " + last_name;}
    public String getBirthday() {return birthday;}

}
