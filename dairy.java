package market;

public class dairy extends product {
    private String expiryDate;
    private boolean organic;
    
    public dairy(String id, String name, double price, int quantity, String expiryDate, boolean organic) {
        super(id, name, price, quantity, "Dairy");
        this.expiryDate = expiryDate;
        this.organic = organic;
    }
    
    public String getExpiryDate() { 
        return expiryDate; 
    }
    
    public boolean isOrganic() { 
        return organic; 
    }
    
    @Override
    public String getProductInfo() {
        return toString() + String.format(", Expires: %s, Organic: %s", 
                                        expiryDate, organic ? "Yes" : "No");
    }
}