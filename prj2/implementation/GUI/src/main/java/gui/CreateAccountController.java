package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import businesslogic.Hasher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.EmployeeStorage;

public class CreateAccountController {

    Hasher hasher = new Hasher();
    EmployeeStorage es = new EmployeeStorage();
    Logger logger = new Logger();

    @FXML
    private URL location;

    @FXML
    private TextField IDField;

    @FXML
    private CheckBox checkbox1;

    @FXML
    private CheckBox checkbox2;

    @FXML
    private CheckBox checkbox3;

    @FXML
    private CheckBox checkbox4;

    @FXML
    private Label chooseRoleLabel;

    @FXML
    private Button createAccountButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void createAccount() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String position = getPositionFromCheckboxes();

        // exception handling gui
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()
                || position.isEmpty()) {
            errorLabel.setText("All fields are required and at least one role must be selected.");
            return;
        }
        if (firstName.matches(".*\\d.*") || lastName.matches(".*\\d.*")) {
            errorLabel.setText("First name and last name cannot contain numbers.");
            return;
        }

        try {
            String hashedPassword = hasher.hashPassword(password);
            boolean success = es.createEmployee(firstName, lastName, username, hashedPassword, position);

            if (success) {
                errorLabel.setText("Account created successfully!");
            } else {
                errorLabel.setText("Failed to create account. Please try again.");
            }

        } catch (Exception e) {
            errorLabel.setText("Error creating employee. Please try again.");
            logger.display(e);
        }
    }

    @FXML
    void showCreateAccountForm() throws IOException {
        Stage stage = new Stage();
        Parent layout = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene scene = new Scene(layout, 700, 400);

        stage.setScene(scene);
        stage.setTitle("Create new account");
        stage.show();
    }

    private String getPositionFromCheckboxes() {
        List<String> positions = new ArrayList<>();
        if (checkbox1.isSelected()) {
            positions.add("Sales Officer");
        }
        if (checkbox2.isSelected()) {
            positions.add("Account Manager");
        }
        if (checkbox3.isSelected()) {
            positions.add("Sales Manager");
        }
        if (checkbox4.isSelected()) {
            positions.add("Administrator");
        }
        return String.join(", ", positions);
    }

    @FXML
    void initialize() {

        assert checkbox1 != null : "fx:id=\"checkbox1\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert checkbox2 != null : "fx:id=\"checkbox2\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert checkbox3 != null : "fx:id=\"checkbox3\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert checkbox4 != null : "fx:id=\"checkbox4\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert chooseRoleLabel != null
                : "fx:id=\"chooseRoleLabel\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert createAccountButton != null
                : "fx:id=\"createAccountButton\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert errorLabel != null
                : "fx:id=\"detailsLabel\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert firstNameField != null
                : "fx:id=\"firstNameField\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert lastNameField != null
                : "fx:id=\"lastNameField\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert passwordField != null
                : "fx:id=\"passwordField\" was not injected: check your FXML file 'CreateAccount.fxml'.";
        assert usernameField != null
                : "fx:id=\"usernameField\" was not injected: check your FXML file 'CreateAccount.fxml'.";
    }
}
