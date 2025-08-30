package market;

public class PercentageDiscount implements Discount {
	private double percentage;
    private String description;
    private String applicableCategory;
    private int minQuantity;
    
    public PercentageDiscount(double percentage, String description, String applicableCategory, int minQuantity) {
        this.percentage = percentage;
        this.description = description;
        this.applicableCategory = applicableCategory;
        this.minQuantity = minQuantity;
    }
        @Override
        public double applyDiscount(double originalPrice, int quantity) {
            return originalPrice * (1 - percentage / 100);
        }
        
        @Override
        public String getDiscountDescription() {
            return description + " (" + percentage + "% off)";
        }
        
        @Override
        public boolean isApplicable(product product, int quantity) {
            return (applicableCategory.equals("ALL") || product.getCategory().equalsIgnoreCase(applicableCategory)) 
                   && quantity >= minQuantity;
        }

}
