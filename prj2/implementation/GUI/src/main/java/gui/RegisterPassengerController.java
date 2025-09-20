package gui;

import businesslogic.Verifier;
import datarecords.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import persistence.DatabaseConnection;

public class RegisterPassengerController {

    @FXML private DatePicker bdayPicker;
    @FXML private TextField firstField;
    @FXML private TextField lastField;
    @FXML private Button saveButton;
    
    @FXML private Label errLabel;

    Verifier verifier = new Verifier();
    DatabaseConnection dbconnect = new DatabaseConnection();

    public Button getSaveButton() {
        return saveButton;
    }

    public Boolean isReady(){
        if(firstField.getText().isEmpty() && lastField.getText().isEmpty() && bdayPicker.getValue().toString().isEmpty()){
            errLabel.setText("TextFields cannot be empty!");
            return false;
        } else if(!verifier.inThePast(bdayPicker.getValue())) {
            errLabel.setText("Birthday cannot be in the future!");
            return false;
        } else {
            return true;
        }
    }

    public void saveClient(){
        dbconnect.registerClient(new Client(null, firstField.getText(), lastField.getText(), bdayPicker.getValue().toString()));   
    }
}
