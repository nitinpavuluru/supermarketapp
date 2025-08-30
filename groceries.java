package market;

public class groceries extends product {
    private String brand;
    private double weight; // in kg
    
    public groceries(String id, String name, double price, int quantity, String brand, double weight) {
        super(id, name, price, quantity, "Groceries");
        this.brand = brand;
        this.weight = weight;
    }
    
    public String getBrand() { 
        return brand; 
    }
    
    public double getWeight() { 
        return weight; 
    }
    
    @Override
    public String getProductInfo() {
        return toString() + String.format(", Brand: %s, Weight: %.2fkg", brand, weight);
    }
}