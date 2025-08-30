package market;

public interface Discount {
	double applyDiscount(double originalPrice, int quantity);
    String getDiscountDescription();
    boolean isApplicable(product product, int quantity);
}
