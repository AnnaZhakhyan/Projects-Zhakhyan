package datarecords;

public class Discount {
    private String name;
    private int discountPercentage;
    private String startDate;
    private String endDate;

    // Constructor, getters, and setters
    public Discount(String name, int percentage, String startDate, String endDate) {
        this.name = name;
        this.discountPercentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() { return name; }
    public int getPercentage() { return discountPercentage; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }

    public void setName(String name) { this.name = name; }
    public void setDiscountPercentage(int discountPercentage) { this.discountPercentage = discountPercentage; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}