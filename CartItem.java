package market;

public class CartItem {
    private product product;
    private int quantity;
    private Discount appliedDiscount;
    private double originalPrice;
    private double discountedPrice;
    
    public CartItem(product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.originalPrice = product.getPrice() * quantity;
        this.discountedPrice = originalPrice;
    }
    
    public product getProduct() { 
        return product; 
    }
    
    public int getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
        this.originalPrice = product.getPrice() * quantity;
        if (appliedDiscount != null) {
            this.discountedPrice = appliedDiscount.applyDiscount(originalPrice, quantity);
        } else {
            this.discountedPrice = originalPrice;
        }
    }
    
    public double getTotalPrice() {
        return discountedPrice;
    }
    
    public double getOriginalPrice() {
        return originalPrice;
    }
    
    public double getSavings() {
        return originalPrice - discountedPrice;
    }
    
    public void applyDiscount(Discount discount) {
        if (discount.isApplicable(product, quantity)) {
            this.appliedDiscount = discount;
            this.discountedPrice = discount.applyDiscount(originalPrice, quantity);
        }
    }
    
    public void removeDiscount() {
        this.appliedDiscount = null;
        this.discountedPrice = originalPrice;
    }
    
    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s x %d", product.getName(), quantity));
        
        if (appliedDiscount != null) {
            sb.append(String.format(" = $%.2f (was $%.2f) [%s]", 
                     discountedPrice, originalPrice, appliedDiscount.getDiscountDescription()));
        } else {
            sb.append(String.format(" = $%.2f", discountedPrice));
        }
        
        return sb.toString();
    }
}