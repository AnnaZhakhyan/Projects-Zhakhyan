package gui;

import datarecords.Flight;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

public class GUIFlight {
    Flight flight;
    int id;
    String source;
    String connection;
    String destination;
    String date;
    String time;
    int price;
    Button button = new Button("Book Now");

    public GUIFlight(Flight flight, TabPane tabPane) {
        this.flight = flight;
        this.id = flight.getFlightID(0);
        if(flight.getAirportsSize() > 2){
            this.source = flight.getAirport(0);
            this.connection = flight.getAirport(1);
            this.destination = flight.getAirport(2);
        } else {
            this.source = flight.getAirport(0);
            this.connection = "None";
            this.destination = flight.getAirport(1);
        }
        
        this.date = flight.getDeparutre(0).split(" ")[0];
        this.time = flight.getDeparutre(0).split(" ")[1];
        this.price = flight.getPrice();

        button.setMaxWidth(Double.MAX_VALUE);
    }

    public int getId() {return id;}
    public String getSource() {return source;}
    public String getConnection() {return connection;}
    public String getDestination() {return destination;}
    public String getDate() {return date;}
    public String getTime() {return time;}
    public int getPrice() {return price;}
    public Button getButton() {return button;}
}