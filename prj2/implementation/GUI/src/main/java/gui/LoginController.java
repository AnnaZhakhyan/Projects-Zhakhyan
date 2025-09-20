package gui;

import java.io.IOException;
import java.util.function.Supplier;

import datarecords.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import persistence.DatabaseConnection;

class LoginController {
    DatabaseConnection dbconnect = new DatabaseConnection();
    Logger logger = new Logger();

    private final Supplier<SceneManager> sceneManagerSupplier;
    public LoginController(Supplier<SceneManager> sceneManagerSupplier) {
        this.sceneManagerSupplier = sceneManagerSupplier;
    }

    @FXML private VBox loginPage;
    @FXML private Button loginButton;
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Label loginMess;

    @FXML
    void login(ActionEvent event){
        Account account = dbconnect.authenticateUser(userField.getText(), passField.getText());
        if(account != null){
            sceneManagerSupplier.get().setAccount(account);
            sceneManagerSupplier.get().changeScene("SearchFlight");
        } else {
            logger.display(dbconnect.getExeption());

            loginMess.setVisible(true);
        }
    }

    @FXML
    private void initialize(){
    }
    
    @FXML
    private void switchToPrimary() {
        
    }
}