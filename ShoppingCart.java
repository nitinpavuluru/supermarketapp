package market;
import java.util.*;

public class ShoppingCart {
    private List<CartItem> items;
    private List<Discount> availableDiscounts;
    
    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.availableDiscounts = new ArrayList<>();
        initializeDiscounts();
    }
    
    private void initializeDiscounts() {
        // Add various discount offers
        availableDiscounts.add(new PercentageDiscount(10, "Dairy Special", "Dairy", 2));
        availableDiscounts.add(new PercentageDiscount(15, "Beverage Bulk Buy", "Beverages", 3));
        availableDiscounts.add(new BuyXGetYFree(2, 1, "Buy 2 Get 1 Free", "Groceries"));
        availableDiscounts.add(new FixedAmountDiscount(2.0, "Premium Product Discount", 10.0));
        availableDiscounts.add(new PercentageDiscount(5, "Student Discount", "ALL", 1));
    }
    
    public void addItem(product product, int quantity) {
        if (product.getQuantity() < quantity) {
            System.out.println("Insufficient stock! Available: " + product.getQuantity());
            return;
        }
        
        // Check if item already exists in cart
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                applyBestDiscount(item);
                System.out.println(quantity + " more " + product.getName() + "(s) added to cart!");
                return;
            }
        }
        
        // Add new item to cart
        CartItem newItem = new CartItem(product, quantity);
        items.add(newItem);
        applyBestDiscount(newItem);
        System.out.println(quantity + " " + product.getName() + "(s) added to cart!");
    }
    
    private void applyBestDiscount(CartItem item) {
        Discount bestDiscount = null;
        double maxSavings = 0;
        double originalPrice = item.getProduct().getPrice() * item.getQuantity();
        
        for (Discount discount : availableDiscounts) {
            if (discount.isApplicable(item.getProduct(), item.getQuantity())) {
                double discountedPrice = discount.applyDiscount(originalPrice, item.getQuantity());
                double savings = originalPrice - discountedPrice;
                
                if (savings > maxSavings) {
                    maxSavings = savings;
                    bestDiscount = discount;
                }
            }
        }
        
        if (bestDiscount != null) {
            item.applyDiscount(bestDiscount);
        }
    }
    
    public void removeItem(String productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
        System.out.println("Item removed from cart!");
    }
    
    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }
        
        System.out.println("\n--- SHOPPING CART ---");
        double totalOriginal = 0;
        double totalDiscounted = 0;
        
        for (CartItem item : items) {
            System.out.println(item);
            totalOriginal += item.getOriginalPrice();
            totalDiscounted += item.getTotalPrice();
        }
        
        double totalSavings = totalOriginal - totalDiscounted;
        
        System.out.println("----------------------------------------");
        if (totalSavings > 0) {
            System.out.printf("Subtotal: $%.2f\n", totalOriginal);
            System.out.printf("Discount: -$%.2f\n", totalSavings);
            System.out.printf("TOTAL: $%.2f\n", totalDiscounted);
            System.out.printf("You saved: $%.2f!\n", totalSavings);
        } else {
            System.out.printf("TOTAL: $%.2f\n", totalDiscounted);
        }
    }
    
    public void displayAvailableDiscounts() {
        System.out.println("\n--- CURRENT OFFERS ---");
        for (int i = 0; i < availableDiscounts.size(); i++) {
            System.out.println((i + 1) + ". " + availableDiscounts.get(i).getDiscountDescription());
        }
    }
    
    public double getTotalAmount() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
    
    public double getTotalSavings() {
        return items.stream().mapToDouble(CartItem::getSavings).sum();
    }
    
    public List<CartItem> getItems() { 
        return items; 
    }
    
    public void clear() {
        items.clear();
    }
}