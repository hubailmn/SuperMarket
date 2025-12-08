package sa.edu.uqu.supermarket.inventory;

import sa.edu.uqu.supermarket.registration.object.User;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private final User cartOwner;
    private final Map<String, Integer> cartItems;
    private double totalPrice;

    public Cart(User cartOwner) {
        this.cartOwner = cartOwner;
        this.cartItems = new HashMap<>();
        this.totalPrice = 0.0;
    }

    public void viewCart() {
        System.out.println("\n--- Shopping Cart for " + cartOwner.getUsername() + " ---");
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        double currentTotal = 0.0;
        InventoryManager manager = InventoryManager.getInstance();

        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String itemCode = entry.getKey();
            int quantityInCart = entry.getValue();

            Item item = manager.getItemByCode(itemCode);

            if (item != null) {
                double itemPrice = item.getPrice();
                double itemSubtotal = itemPrice * quantityInCart;
                currentTotal += itemSubtotal;

                System.out.printf("   %s (%s) x %d @ %.2f = %.2f%n",
                        item.getName(), itemCode, quantityInCart, itemPrice, itemSubtotal);
            }
        }

        this.totalPrice = currentTotal;
        System.out.printf("------------------------------------------%n");
        System.out.printf("Total Price: %.2f%n", totalPrice);
    }

    public boolean addItem(String itemCode, int quantity) {
        if (quantity <= 0) {
            System.out.println(" ❌ Quantity must be positive.");
            return false;
        }

        InventoryManager manager = InventoryManager.getInstance();
        Item item = manager.getItemByCode(itemCode);

        if (item == null) {
            System.out.println(" ❌ Item not found.");
            return false;
        }

        int currentStock = item.getQuantity();
        int quantityInCart = cartItems.getOrDefault(itemCode, 0);
        int requestedTotal = quantityInCart + quantity;

        if (requestedTotal > currentStock) {
            System.out.printf(" ❌ Not enough stock for %s. Available: %d, Requested: %d%n",
                    item.getName(), currentStock, requestedTotal);
            return false;
        }

        cartItems.put(itemCode, requestedTotal);
        System.out.printf(" ✅ Added %d units of %s to cart.%n", quantity, item.getName());
        return true;
    }

    public void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println(" ‼️ Cannot checkout. Cart is empty.");
            return;
        }

        InventoryManager manager = InventoryManager.getInstance();
        boolean success = true;

        System.out.println("\n--- Processing Checkout ---");

        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String itemCode = entry.getKey();
            int quantityToBuy = entry.getValue();
            Item item = manager.getItemByCode(itemCode);

            if (item.getQuantity() < quantityToBuy) {
                System.out.printf(" ❌ Checkout failed: Insufficient stock for %s. Available: %d, Needed: %d%n", item.getName(), item.getQuantity(), quantityToBuy);
                success = false;
                break;
            }
        }

        if (success) {
            for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                String itemCode = entry.getKey();
                int quantityToBuy = entry.getValue();
                Item item = manager.getItemByCode(itemCode);

                item.setQuantity(item.getQuantity() - quantityToBuy);
            }

            manager.saveItems();

            cartItems.clear();
            totalPrice = 0.0;

            System.out.println("\n ✅ Checkout complete!");
            viewCart();
        } else {
            System.out.println(" ❌ Checkout canceled due to inventory issues.");
        }
    }
}