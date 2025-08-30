package market;

public class beverage extends product {
    private String type; // soft drink, juice, water, etc.
    private int volume; // in ml
    
    public beverage(String id, String name, double price, int quantity, String type, int volume) {
        super(id, name, price, quantity, "Beverages");
        this.type = type;
        this.volume = volume;
    }
    
    public String getType() { 
        return type; 
    }
    
    public int getVolume() { 
        return volume; 
    }
    
    @Override
    public String getProductInfo() {
        return toString() + String.format(", Type: %s, Volume: %dml", type, volume);
    }
}