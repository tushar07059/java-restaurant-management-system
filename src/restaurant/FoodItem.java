package restaurant;

public class FoodItem {
    private String name;
    private double price;
    private String category;

    public FoodItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return name + " (" + category + ") - ₹" + String.format("%.2f", price);
    }
}

