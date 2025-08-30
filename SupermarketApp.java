package market;
import java.util.*;

public class SupermarketApp {
    private inventory inventory;
    private ShoppingCart cart;
    private Scanner scanner;
    
    public SupermarketApp() {
        this.inventory = new inventory();
        this.cart = new ShoppingCart();
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("🏪 Welcome to JavaMart Supermarket! 🏪");
        System.out.println("Your one-stop shop with automatic discounts!");
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    inventory.displayAllProducts();
                    break;
                case 2:
                    displayCategoryMenu();
                    break;
                case 3:
                    searchProducts();
                    break;
                case 4:
                    addToCart();
                    break;
                case 5:
                    cart.displayCart();
                    break;
                case 6:
                    removeFromCart();
                    break;
                case 7:
                    cart.displayAvailableDiscounts();
                    break;
                case 8:
                    checkout();
                    break;
                case 9:
                    System.out.println("🎉 Thank you for shopping with JavaMart!");
                    System.out.println("Have a great day! 🌟");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Invalid option! Please try again.");
            }
            
            System.out.println("\n📱 Press Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n========== 🏪 JAVAMART SUPERMARKET 🏪 ==========");
        System.out.println("1. 📋 View All Products");
        System.out.println("2. 🗂️  Browse by Category");
        System.out.println("3. 🔍 Search Products");
        System.out.println("4. 🛒 Add to Cart");
        System.out.println("5. 👀 View Cart");
        System.out.println("6. ❌ Remove from Cart");
        System.out.println("7. 💰 View Current Offers");
        System.out.println("8. 💳 Checkout");
        System.out.println("9. 🚪 Exit");
        System.out.println("================================================");
        System.out.print("Choose an option (1-9): ");
    }
    
    private void displayCategoryMenu() {
        System.out.println("\n🗂️ Select Category:");
        System.out.println("1. 🥤 Beverages");
        System.out.println("2. 🥛 Dairy Products");
        System.out.println("3. 🍞 Groceries");
        System.out.print("Choose category (1-3): ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                inventory.displayProductsByCategory("Beverages");
                break;
            case 2:
                inventory.displayProductsByCategory("Dairy");
                break;
            case 3:
                inventory.displayProductsByCategory("Groceries");
                break;
            default:
                System.out.println("❌ Invalid category selection!");
        }
    }
    
    private void searchProducts() {
        System.out.print("🔍 Enter search keyword: ");
        String keyword = scanner.nextLine().trim();
        
        if (keyword.isEmpty()) {
            System.out.println("❌ Please enter a search term!");
            return;
        }
        
        List<product> results = inventory.searchProducts(keyword);
        
        if (results.isEmpty()) {
            System.out.println("😔 No products found matching '" + keyword + "'");
        } else {
            System.out.println("\n🎯 Search Results for '" + keyword + "':");
            System.out.println("Found " + results.size() + " product(s):");
            for (product product : results) {
                System.out.println("  " + product.getProductInfo());
            }
        }
    }
    
    private void addToCart() {
        System.out.print("🛒 Enter product ID: ");
        String productId = scanner.nextLine().trim().toUpperCase();
        
        product product = inventory.getProduct(productId);
        if (product == null) {
            System.out.println("❌ Product not found! Please check the product ID.");
            return;
        }
        
        System.out.println("✅ Selected: " + product.getProductInfo());
        
        if (product.getQuantity() == 0) {
            System.out.println("😔 Sorry, this product is out of stock!");
            return;
        }
        
        System.out.print("Enter quantity (max " + product.getQuantity() + "): ");
        int quantity = getIntInput();
        
        if (quantity <= 0) {
            System.out.println("❌ Invalid quantity!");
        } else if (quantity > product.getQuantity()) {
            System.out.println("❌ Not enough stock! Available: " + product.getQuantity());
        } else {
            cart.addItem(product, quantity);
            System.out.println("🎉 Item added successfully!");
        }
    }
    
    private void removeFromCart() {
        if (cart.getItems().isEmpty()) {
            System.out.println("🛒 Your cart is empty!");
            return;
        }
        
        cart.displayCart();
        System.out.print("❌ Enter product ID to remove: ");
        String productId = scanner.nextLine().trim().toUpperCase();
        
        boolean found = false;
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                found = true;
                break;
            }
        }
        
        if (found) {
            cart.removeItem(productId);
            System.out.println("✅ Item removed successfully!");
        } else {
            System.out.println("❌ Product not found in cart!");
        }
    }
    
    private void checkout() {
        if (cart.getItems().isEmpty()) {
            System.out.println("🛒 Your cart is empty! Add some items first.");
            return;
        }
        
        System.out.println("\n🧾 ================ CHECKOUT ================");
        cart.displayCart();
        
        double totalSavings = cart.getTotalSavings();
        if (totalSavings > 0) {
            System.out.println("\n💰 Congratulations! You're saving $" + 
                             String.format("%.2f", totalSavings) + " with our offers!");
        }
        
        System.out.print("\n💳 Proceed with checkout? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            if (inventory.processOrder(cart)) {
                System.out.println("\n🎉 ================ ORDER SUCCESSFUL! ================");
                System.out.printf("💵 Total Paid: $%.2f\n", cart.getTotalAmount());
                if (totalSavings > 0) {
                    System.out.printf("💰 Total Saved: $%.2f\n", totalSavings);
                }
                System.out.println("📦 Your order is being prepared!");
                System.out.println("🙏 Thank you for shopping with JavaMart!");
                cart.clear();
            } else {
                System.out.println("❌ Checkout failed! Please try again.");
            }
        } else {
            System.out.println("🔄 Checkout cancelled. Items remain in your cart.");
        }
    }
    
    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("❌ Please enter a valid number: ");
            scanner.nextLine(); // consume invalid input
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return input;
    }
    
    // Main method to run the application
    public static void main(String[] args) {
        SupermarketApp app = new SupermarketApp();
        app.run();
    }
}