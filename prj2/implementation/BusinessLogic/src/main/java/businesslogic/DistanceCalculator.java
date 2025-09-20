package businesslogic;

import java.lang.Math;

import persistence.DatabaseConnection;

public class DistanceCalculator {
    
    public static final double RADIUS_OF_EARTH = 6371; // Earth's radius in kilometers
    
    // Function to calculate distance between two points using Haversine formula
    public static Integer calculateDistance(Double lon1, Double lat1, Double lon2, Double lat2) {

        // Convert latitude and longitude from degrees to radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        
        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = RADIUS_OF_EARTH * c;
        return (int) distance;
    }
    
    // Example usage
    // public static void main(String[] args) {
    //     DatabaseConnection dbconnect = new DatabaseConnection();
    //     Double[] string = dbconnect.getLangLati("AMS", "FNJ");
    //     int distance = calculateDistance(string[0],string[1],string[2],string[3]); // New York City to Los Angeles
    //     System.out.println("Distance between is: " + distance + " km");
    // }
}
