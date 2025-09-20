package datarecords;

import java.lang.String;
import java.util.ArrayList;

public record Ticket(int passenger, String documentType, String documentID, String seat, int booking, int flight, ArrayList<StaticDiscount> discounts, ArrayList<String> extras) {

}
