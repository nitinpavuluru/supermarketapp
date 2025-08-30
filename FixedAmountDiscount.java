package market;

public class FixedAmountDiscount implements Discount {
    private double discountAmount;
    private String description;
    private double minPurchase;
    
    public FixedAmountDiscount(double discountAmount, String description, double minPurchase) {
        this.discountAmount = discountAmount;
        this.description = description;
        this.minPurchase = minPurchase;
    }
    
    @Override
    public double applyDiscount(double originalPrice, int quantity) {
        return Math.max(0, originalPrice - discountAmount);
    }
    
    @Override
    public String getDiscountDescription() {
        return description + " ($" + discountAmount + " off)";
    }
    
    @Override
    public boolean isApplicable(product product, int quantity) {
        return (product.getPrice() * quantity) >= minPurchase;
    }
}