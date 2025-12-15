package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.SuperMarket;
import sa.edu.uqu.supermarket.inventory.Cart;
import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.data.Role;
import sa.edu.uqu.supermarket.registration.object.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Cashier implements Menu {

    private final User user;
    private final Scanner scanner = new Scanner(System.in);
    private Cart currentTransactionCart;

    public Cashier(User user) {
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("\nWelcome, " + user.getUsername() + " (Cashier)");
        System.out.println("Cashier Menu:");

        if (currentTransactionCart == null) {
            System.out.println("""
                       1. Start New Transaction
                       2. Logout
                    """);
        } else {
            System.out.println("""
                       1. Scan Item (Add to Cart)
                       2. View Current Cart
                       3. Checkout Transaction
                       4. Cancel Transaction
                       5. Logout
                    """);
        }

        int choice = -1;
        try {
            System.out.print("\t ➤ Enter option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("‼️ Invalid input. Please enter a number.");
        }

        if (currentTransactionCart == null) {
            switch (choice) {
                case 1 -> startNewTransaction();
                case 2 -> {
                    System.out.println("Goodbye!");
                    SuperMarket.getAuthenticationService().showMenu();
                    return;
                }
                default -> System.out.println("‼️ Invalid option.");
            }
        } else {
            switch (choice) {
                case 1 -> scanItem();
                case 2 -> currentTransactionCart.viewCart();
                case 3 -> checkoutTransaction();
                case 4 -> cancelTransaction();
                case 5 -> {
                    System.out.println("Goodbye!");
                    SuperMarket.getAuthenticationService().showMenu();
                    return;
                }
                default -> System.out.println("‼️ Invalid option.");
            }
        }

        if (choice != 5) {
            show();
        }
    }

    private void startNewTransaction() {
        User guestCustomer = new User("Store", "Customer", "0000000000", "customer@super.market", "storecustomer", "none", Role.CUSTOMER);
        this.currentTransactionCart = new Cart(guestCustomer);
        System.out.println(" ✅ New transaction started.");
    }

    private void scanItem() {
        System.out.println("\n--- Scan Item ---");
        System.out.print("\t ➤ Enter Item Code: ");
        String code = scanner.nextLine().trim();

        int quantity = 0;
        while (quantity <= 0) {
            try {
                System.out.print("\t ➤ Enter Quantity (typically 1): ");
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

        currentTransactionCart.addItem(code, quantity);
    }

    private void checkoutTransaction() {
        currentTransactionCart.viewCart();
        System.out.print("\n\t ➤ Confirm checkout? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            currentTransactionCart.checkout();
            this.currentTransactionCart = null;
        } else {
            System.out.println(" ❌ Checkout cancelled by Cashier.");
        }
    }

    private void cancelTransaction() {
        this.currentTransactionCart = null;
        System.out.println(" ❌ Transaction cancelled. Cart cleared.");
    }
}