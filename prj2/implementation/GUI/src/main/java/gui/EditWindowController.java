package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import datarecords.Employee;
import persistence.EmployeeStorage;

public class EditWindowController {
    private Employee selectedEmployee;
    EmployeeStorage es = new EmployeeStorage();
    Logger logger = new Logger();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox deleteAccountcheckbox;

    @FXML
    private Label editdetailslabel;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField position;

    @FXML
    private Button saveChangesButton;

    @FXML
    private TextField username;

    @FXML
    private Label errorLabel;

    public void insert(String fn, String ln, String user, String pos) {
        username.setText(user);
        firstname.setText(fn);
        lastname.setText(ln);
        position.setText(pos);
    }

    public void setSelectedEmployee(Employee employee) {
        this.selectedEmployee = employee;
        if (selectedEmployee != null) {
            firstname.setText(employee.getFirstname());
            lastname.setText(employee.getLastname());
            username.setText(employee.getUsername());
            position.setText(employee.getPosition());
        }
    }

    @FXML
    void deleteAccount(ActionEvent event) {
        if (selectedEmployee != null) {
            try {
                es.deleteEmployee(selectedEmployee);
                errorLabel.setText("Account deleted successfully.");
            } catch (Exception e) {
                errorLabel.setText("Error deleting account. Please try again.");
                logger.display(e);
            }
        } else {
            errorLabel.setText("No employee selected to delete.");
        }
    }

    @FXML
    void saveChanges(ActionEvent event) {
        if (selectedEmployee == null) {
            errorLabel.setText("No employee selected.");
            return;
        }

        String firstName = firstname.getText().trim();
        String lastName = lastname.getText().trim();
        String userName = username.getText().trim();
        String pos = position.getText().trim();
        if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || pos.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }

        if (firstName.matches(".*\\d.*") || lastName.matches(".*\\d.*")) {
            errorLabel.setText("First name and last name cannot contain numbers.");
            return;
        }

        try {
            selectedEmployee.setFirstname(firstName);
            selectedEmployee.setLastname(lastName);
            selectedEmployee.setUsername(userName);
            selectedEmployee.setPosition(pos);

            es.editEmployee(selectedEmployee);
            errorLabel.setText("Employee changes saved successfully.");
        } catch (Exception e) {
            errorLabel.setText("Error saving changes. Please try again.");
            logger.display(e);
        }
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    @FXML
    void openEditDialog(MouseEvent event) {
        if (selectedEmployee != null) {
            firstname.setText(selectedEmployee.getFirstname());
            lastname.setText(selectedEmployee.getLastname());
            username.setText(selectedEmployee.getUsername());
            position.setText(selectedEmployee.getPosition());
        }
    }

    @FXML
    void initialize() {
        assert deleteAccountcheckbox != null
                : "fx:id=\"deleteAccountcheckbox\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert editdetailslabel != null
                : "fx:id=\"editdetailslabel\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert firstname != null : "fx:id=\"firstname\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert lastname != null : "fx:id=\"lastname\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert position != null : "fx:id=\"position\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert saveChangesButton != null
                : "fx:id=\"saveChangesButton\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'EditWindow.fxml'.";
    }
}
