package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import datarecords.Employee;
import persistence.EmployeeStorage;

public class SearchAccountController {

    EmployeeStorage es = new EmployeeStorage();

    @FXML
    private URL location;

    @FXML
    private Button editButton;
    @FXML
    private Button createAccount;

    @FXML
    private TableColumn<Employee, String> firstname;

    @FXML
    private TableColumn<Employee, String> lastname;

    @FXML
    private TableColumn<Employee, String> position;

    @FXML
    private TableColumn<Employee, String> username;

    @FXML
    private TableView<Employee> table;

    @FXML
    private TextField searchBar;

    public Button getCreateAccount() {
        return createAccount;
    }

    @FXML
void Search(ActionEvent event) {
    String query = searchBar.getText();
    try {
        ArrayList<Employee> employees = es.findEmployees(query);
        table.getItems().clear();
        table.getItems().addAll(employees);
    } catch (IOException e) {
        System.err.println("Error reading data: " + e.getMessage());
    
    } catch (Exception e) {
        System.err.println("Unexpected error: " + e.getMessage());
    }
}

    @FXML
    void editAccount(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditWindow.fxml"));
        Parent layout = loader.load();
        Scene scene = new Scene(layout, 300, 400);
        EditWindowController controller = loader.getController();
    
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            controller.setSelectedEmployee(selected); 
            controller.insert(selected.getFirstname(), selected.getLastname(), selected.getUsername(),
                    selected.getPosition());
    
            stage.setScene(scene);
            stage.setTitle("Edit Account");
            stage.show();
        } else {
            System.out.println("Please select an employee to edit.");
        }
    }
    // @FXML
    // void deleteAccount(ActionEvent event) {
    // if (selectedEmployee != null) {
    // try {
    // es.deleteEmployee(selectedEmployee);
    // Stage stage = (Stage) deleteAccountcheckbox.getScene().getWindow();
    // stage.close();
    // } catch (SQLException e) {
    // Logger logger = new Logger();
    // logger.display(e);
    // }
    // }
    // }

    @FXML
    void initialize() {
        assert firstname != null : "fx:id=\"firstname\" was not injected: check your FXML file 'SearchAccount.fxml'.";
        assert lastname != null : "fx:id=\"lastname\" was not injected: check your FXML file 'SearchAccount.fxml'.";
        assert position != null : "fx:id=\"position\" was not injected: check your FXML file 'SearchAccount.fxml'.";
        assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'SearchAccount.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'SearchAccount.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'SearchAccount.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'SearchAccount.fxml'.";

        firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Employee, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        table.getColumns().add(idColumn);
    }
}
