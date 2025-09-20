package gui;

import datarecords.Flight;
import datarecords.Stop;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StopsController {
    @FXML private TableView<Stop> stopsTable;
    @FXML private TableColumn<Stop, String> ap_col;
    @FXML private TableColumn<Stop, String> dt_col;
    @FXML private TableColumn<Stop, String> arr_col;
    @FXML private TableColumn<Stop, String> dep_col;

    public void setup(Flight flight){
        if(flight.getAirportsSize()>2){
            stopsTable.getItems().add(new Stop(flight.getAirport(0),flight.getDeparutre(0).split(" ")[0],"",flight.getDeparutre(0).split(" ")[1]));
            stopsTable.getItems().add(new Stop(flight.getAirport(1),flight.getArrival(0).split(" ")[0],flight.getArrival(0).split(" ")[1],flight.getDeparutre(1).split(" ")[1]));
            stopsTable.getItems().add(new Stop(flight.getAirport(2),flight.getArrival(1).split(" ")[0],flight.getArrival(1).split(" ")[1],""));
        }
    }

    @FXML
    void initialize(){
        System.out.println("hehe");

        ap_col.setCellValueFactory(new PropertyValueFactory<>("airport"));
        dt_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        arr_col.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        dep_col.setCellValueFactory(new PropertyValueFactory<>("departure"));
    }

}
