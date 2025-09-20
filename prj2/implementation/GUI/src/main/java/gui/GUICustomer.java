package gui;

import javafx.scene.control.TextField;

public class GUICustomer {
    int id;
    String first_name;
    String last_name;
    String birthday;
    TextField address = new TextField();
    TextField email = new TextField();

    GUICustomer(GUIClient client){
        id = Integer.valueOf(client.getId());
        first_name = client.getFirst_name();
        last_name = client.getLast_name();
        birthday = client.getBirthday();
        address.setPromptText("Address");
        email.setPromptText("Email");
    }

    public int getId() {return id;}
    public String getFirst_name() {return first_name;}
    public String getLast_name() {return last_name;}
    public String getBirthday() {return birthday;}
    public TextField getAddress() {return address;}
    public TextField getEmail() {return email;}

    public Boolean isEmpty(){
        if(getAddress().getText().isEmpty() || getEmail().getText().isEmpty()){
            return true;
        } else {
            return false;
        }
    }
}
