package gui;

import datarecords.Discount;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GUIDiscount {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty percentage;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty endDate;

    public GUIDiscount(Discount discount) {
        this.name = new SimpleStringProperty(discount.getName());
        this.percentage = new SimpleIntegerProperty(discount.getPercentage());
        this.startDate = new SimpleStringProperty(discount.getStartDate());
        this.endDate = new SimpleStringProperty(discount.getEndDate());
    }

    public String getName() { return name.get(); }
    public int getPercentage() { return percentage.get(); }
    public String getStartDate() { return startDate.get(); }
    public String getEndDate() { return endDate.get(); }
}