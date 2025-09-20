package gui;

import java.util.ArrayList;

import datarecords.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import persistence.DatabaseConnection;

public class SearchCustomerController {

    DatabaseConnection dbconnect = new DatabaseConnection();

    @FXML private TableView<GUIClient> table;
    @FXML private TableColumn<GUIClient, String> id_col;
    @FXML private TableColumn<GUIClient, String> nme_col;
    @FXML private TableColumn<GUIClient, String> bdy_col;

    public TableView<GUIClient> getTable() {
        return table;
    }
    
    @FXML private TextField textField;
    @FXML private Button search;

    @FXML void searchFor(ActionEvent event) {
        ArrayList<Client> arrayList = dbconnect.findClient(textField.getText());
        table.getItems().clear();
        for (Client client : arrayList){
            table.getItems().add(new GUIClient(client));
        }
    }

    @FXML private Button selectPass;

    public Button getSelectPass() {
        return selectPass;
    }

    @FXML
    void initialize(){
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        nme_col.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        bdy_col.setCellValueFactory(new PropertyValueFactory<>("birthday"));
    }
}
