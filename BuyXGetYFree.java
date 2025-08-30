package market;

public class BuyXGetYFree implements Discount {
    private int buyQuantity;
    private int freeQuantity;
    private String description;
    private String applicableCategory;
    
    public BuyXGetYFree(int buyQuantity, int freeQuantity, String description, String applicableCategory) {
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.description = description;
        this.applicableCategory = applicableCategory;
    }
    
    @Override
    public double applyDiscount(double originalPrice, int quantity) {
        if (quantity < buyQuantity) return originalPrice;
        
        int freeItems = (quantity / buyQuantity) * freeQuantity;
        int paidItems = quantity - freeItems;
        return (originalPrice / quantity) * paidItems;
    }
    
    @Override
    public String getDiscountDescription() {
        return description + " (Buy " + buyQuantity + " Get " + freeQuantity + " Free)";
    }
    
    @Override
    public boolean isApplicable(product product, int quantity) {
        return (applicableCategory.equals("ALL") || product.getCategory().equalsIgnoreCase(applicableCategory))
               && quantity >= buyQuantity;
    }
}