package datarecords;

import java.util.ArrayList;

public class Flight {
    ArrayList<Integer> flightIDs;
    ArrayList<String> airlines;
    ArrayList<String> airports;
    ArrayList<String> departures;
    ArrayList<String> arrivals;
    String timeBetween;
    int price;
    ArrayList<String> timezones;
   
    public Flight(ArrayList<Integer> flightIDs, ArrayList<String> airlines, ArrayList<String> airports, ArrayList<String> departures, ArrayList<String> arrivals, String timeBetween, int price, ArrayList<String> timezones){
        this.flightIDs = flightIDs;
        this.airlines = airlines;
        this.airports = airports;
        this.departures = departures;
        this.arrivals = arrivals;
        this.timeBetween = timeBetween;
        this.price = price;
        this.timezones = timezones;
    }
    public ArrayList<Integer> getFlights(){return flightIDs;}
    public int getFlightID(int index) {return flightIDs.get(index);}
    public String getAirline(int index) {return airlines.get(index);}
    public String getAirport(int index) {return airports.get(index);}
    public String getDeparutre(int index) {return departures.get(index).substring(0, departures.get(index).length()-3);}
    public String getArrival(int index) {return arrivals.get(index).substring(0, arrivals.get(index).length()-3);}
    public String getTimeBetween() {return timeBetween;}
    public int getPrice() {return price;}
    public String getTimezone(int index) {return timezones.get(index);}
    public int getAirportsSize(){return airports.size();}

    public String getSource(){return getAirport(0);};
    public String getDestination(){return getAirport(airports.size()-1);};
}
