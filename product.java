package market;

public abstract class product {
    protected String id;
    protected String name;
    protected double price;
    protected int quantity;
    protected String category;
    
    public product(String id, String name, double price, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    
    // Getters
    public String getId() { 
        return id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public double getPrice() { 
        return price; 
    }
    
    public int getQuantity() { 
        return quantity; 
    }
    
    public String getCategory() { 
        return category; 
    }
    
    // Setters
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
    
    public void setPrice(double price) { 
        this.price = price; 
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract String getProductInfo();
    
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Price: $%.2f, Stock: %d", 
                           id, name, price, quantity);
    }
}