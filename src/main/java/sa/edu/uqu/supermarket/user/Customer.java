package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.SuperMarket;
import sa.edu.uqu.supermarket.inventory.Cart;
import sa.edu.uqu.supermarket.inventory.InventoryManager;
import sa.edu.uqu.supermarket.inventory.Item;
import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Customer implements Menu {

    private final User user;
    private final Cart cart;
    private final Scanner scanner = new Scanner(System.in);

    public Customer(User user) {
        this.user = user;
        this.cart = new Cart(user);
    }

    @Override
    public void show() {
        System.out.println("\nWelcome, " + user.getUsername() + " (Customer)");
        System.out.println("Customer Menu:");

        System.out.println("""
                   1. Shop (View Products)
                   2. Add Item to Cart
                   3. View Cart
                   4. Checkout
                   5. Logout
                """);

        int choice = -1;
        try {
            System.out.print("\t ➤ Enter option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("‼️ Invalid input. Please enter a number.");
        }

        switch (choice) {
            case 1 -> viewProducts();
            case 2 -> addItemToCart();
            case 3 -> cart.viewCart();
            case 4 -> cart.checkout();
            case 5 -> {
                System.out.println("Goodbye!");
                SuperMarket.getAuthenticationService().showMenu();
                return;
            }
            default -> System.out.println("‼️ Invalid option.");
        }

        show();
    }

    private void viewProducts() {
        InventoryManager manager = InventoryManager.getInstance();
        List<Item> items = manager.getItems();

        System.out.println("\n--- Available Products ---");
        if (items.isEmpty()) {
            System.out.println("No products currently available.");
            return;
        }

        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    private void addItemToCart() {
        System.out.println("\n--- Add Item to Cart ---");

        System.out.print("\t ➤ Enter Item Code: ");
        String code = scanner.nextLine().trim();

        int quantity = 0;
        while (quantity <= 0) {
            try {
                System.out.print("\t ➤ Enter Quantity: ");
                quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity <= 0) {
                    System.out.println(" ❌ Quantity must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println(" ❌ Invalid quantity format. Please enter an integer.");
                quantity = 0;
            }
        }

        cart.addItem(code, quantity);
    }
}