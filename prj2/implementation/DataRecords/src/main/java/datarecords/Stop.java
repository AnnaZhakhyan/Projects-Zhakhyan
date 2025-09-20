package datarecords;

public class Stop {
    String airport;
    String date;
    String arrival;
    String departure;

    public Stop(String airport, String date, String arrival, String departure){
        this.airport = airport;
        this.date = date;
        this.arrival = arrival;
        this.departure = departure;
    }

    public String getAirport() {
        return airport;
    }
    public String getDate() {
        return date;
    }
    public String getArrival() {
        return arrival;
    }
    public String getDeparture() {
        return departure;
    }
}
