package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StaticDiscountsController {

    @FXML private TableView<GUIStaticDiscount> discTable;
    @FXML private TableColumn<GUIStaticDiscount, ?> discount_col;
    @FXML private TableColumn<GUIStaticDiscount, ?> amount_col;
    @FXML private TableColumn<GUIStaticDiscount, ?> proc_col;

    @FXML private Button addButton;
    @FXML private Button rmvButton;
    @FXML private Button conButton;

    @FXML private Label alertLabel;

    public Button getConButton() {return conButton;};
    public TableView<GUIStaticDiscount> getDiscTable() {return discTable;}

    @FXML
    void initialize(){
        addButton.requestFocus();

        discount_col.setCellValueFactory(new PropertyValueFactory<>("discount"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));
        proc_col.setCellValueFactory(new PropertyValueFactory<>("proc"));
    }

    @FXML
    void addDiscount(ActionEvent event) {
        discTable.getItems().add(new GUIStaticDiscount("",0,false, discTable));
    }

    @FXML
    void removeDiscount(ActionEvent event) {
        discTable.getItems().remove(discTable.getSelectionModel().getSelectedItem());
    }
   
    public void alert(String string){
        alertLabel.setText(string);
    }
}
