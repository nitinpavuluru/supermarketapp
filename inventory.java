package market;
import java.util.*;

public class inventory {
    private Map<String, product> products;
    
    public inventory() {
        this.products = new HashMap<>();
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Add sample beverages
        addProduct(new beverage("BEV001", "Coca Cola", 2.50, 50, "Soft Drink", 500));
        addProduct(new beverage("BEV002", "Orange Juice", 4.99, 30, "Juice", 1000));
        addProduct(new beverage("BEV003", "Water Bottle", 1.25, 100, "Water", 500));
        addProduct(new beverage("BEV004", "Pepsi", 2.45, 45, "Soft Drink", 500));
        addProduct(new beverage("BEV005", "Apple Juice", 3.99, 25, "Juice", 750));
        
        // Add sample dairy products
        addProduct(new dairy("DAI001", "Whole Milk", 3.49, 25, "2024-09-15", false));
        addProduct(new dairy("DAI002", "Organic Yogurt", 5.99, 20, "2024-09-10", true));
        addProduct(new dairy("DAI003", "Cheddar Cheese", 7.99, 15, "2024-10-01", false));
        addProduct(new dairy("DAI004", "Greek Yogurt", 4.49, 18, "2024-09-12", false));
        addProduct(new dairy("DAI005", "Organic Milk", 4.99, 20, "2024-09-18", true));
        
        // Add sample grocery items
        addProduct(new groceries("GRO001", "Basmati Rice", 8.99, 40, "Uncle Ben's", 2.0));
        addProduct(new groceries("GRO002", "Pasta", 2.99, 35, "Barilla", 0.5));
        addProduct(new groceries("GRO003", "Bread", 3.49, 20, "Wonder", 0.7));
        addProduct(new groceries("GRO004", "Cereal", 5.99, 30, "Kellogg's", 0.4));
        addProduct(new groceries("GRO005", "Cooking Oil", 6.49, 25, "Wesson", 1.0));
    }
    
    public void addProduct(product product) {
        products.put(product.getId(), product);
    }
    
    public product getProduct(String id) {
        return products.get(id);
    }
    
    public void displayAllProducts() {
        System.out.println("\n=== SUPERMARKET INVENTORY ===");
        
        // Group products by category
        Map<String, List<product>> categorized = new HashMap<>();
        for (product product : products.values()) {
            categorized.computeIfAbsent(product.getCategory(), k -> new ArrayList<>()).add(product);
        }
        
        // Display each category
        for (String category : Arrays.asList("Beverages", "Dairy", "Groceries")) {
            System.out.println("\n--- " + category.toUpperCase() + " ---");
            List<product> categoryProducts = categorized.get(category);
            if (categoryProducts != null) {
                for (product product : categoryProducts) {
                    System.out.println(product.getProductInfo());
                }
            }
        }
    }
    
    public void displayProductsByCategory(String category) {
        System.out.println("\n--- " + category.toUpperCase() + " ---");
        products.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .forEach(p -> System.out.println(p.getProductInfo()));
    }
    
    public void updateStock(String productId, int newQuantity) {
        product product = products.get(productId);
        if (product != null) {
            product.setQuantity(newQuantity);
            System.out.println("Stock updated for " + product.getName());
        } else {
            System.out.println("Product not found!");
        }
    }
    
    public boolean processOrder(ShoppingCart cart) {
        // Check availability first
        for (CartItem item : cart.getItems()) {
            product product = products.get(item.getProduct().getId());
            if (product == null) {
                System.out.println("Order failed: Product not found - " + item.getProduct().getId());
                return false;
            }
            if (product.getQuantity() < item.getQuantity()) {
                System.out.println("Order failed: Insufficient stock for " + product.getName() + 
                                 " (Available: " + product.getQuantity() + ", Requested: " + item.getQuantity() + ")");
                return false;
            }
        }
        
        // Update inventory after successful validation
        for (CartItem item : cart.getItems()) {
            product product = products.get(item.getProduct().getId());
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }
        
        return true;
    }
    
    public List<product> searchProducts(String keyword) {
        List<product> results = new ArrayList<>();
        for (product product : products.values()) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                product.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }
    
    public Map<String, product> getAllProducts() {
        return new HashMap<>(products);
    }
}