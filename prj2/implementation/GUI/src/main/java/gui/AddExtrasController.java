package gui;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddExtrasController {

    @FXML public Button conButton;

    @FXML
    public TableView<GUIFeature> xtraTable;
    @FXML private TableColumn<?, ?> feat_col;
    @FXML private TableColumn<?, ?> price_col;
    @FXML private TableColumn<?, ?> check_col;

    
    public void setup(String string){
        ArrayList<String> featureList = new ArrayList<>(Arrays.asList(string.split(",")));

        for(GUIFeature feature : xtraTable.getItems()){
            if(featureList.contains(feature.getFeature())){
                feature.getCheck().setSelected(true);
            }
        }
    }
    
    @FXML
    void initialize(){

        xtraTable.getItems().add(new GUIFeature("Extra Legroom", 25));
        xtraTable.getItems().add(new GUIFeature("Additional Baggage", 15));
        xtraTable.getItems().add(new GUIFeature("Extra Weight Baggage", 20));
        xtraTable.getItems().add(new GUIFeature("In-Flight Meals", 25));
        xtraTable.getItems().add(new GUIFeature("Alcoholic Beverages", 35));
        xtraTable.getItems().add(new GUIFeature("Wi-Fi Access", 35));
        xtraTable.getItems().add(new GUIFeature("In-Flight Entertainment", 10));
        xtraTable.getItems().add(new GUIFeature("Priority Boarding", 45));
        xtraTable.getItems().add(new GUIFeature("Lounge Access", 75));
        
        feat_col.setCellValueFactory(new PropertyValueFactory<>("feature"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        check_col.setCellValueFactory(new PropertyValueFactory<>("check"));
    }

}
