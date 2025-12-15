package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.SuperMarket;
import sa.edu.uqu.supermarket.inventory.InventoryManager;
import sa.edu.uqu.supermarket.inventory.Item;
import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;
import sa.edu.uqu.supermarket.sales.SalesManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager implements Menu {

    private final User user;
    private final Scanner scanner = new Scanner(System.in);

    public Manager(User user) {
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("\nWelcome, " + user.getUsername() + " (Manager)");
        System.out.println("Manager Menu:");

        System.out.println("""
                   1. Check Inventory Levels
                   2. View Sales Report
                   3. Update Item Stock
                   4. Logout
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
            case 1 -> checkInventoryLevels();
            case 2 -> viewSalesReport();
            case 3 -> updateItemStock();
            case 4 -> {
                System.out.println("Goodbye!");
                SuperMarket.getAuthenticationService().showMenu();
                return;
            }
            default -> System.out.println("‼️ Invalid option.");
        }

        show();
    }

    private void checkInventoryLevels() {
        System.out.println("\n--- Low Stock Report ---");
        InventoryManager manager = InventoryManager.getInstance();

        int lowStockCount = 0;
        final int LOW_STOCK_THRESHOLD = 5;

        for (Item item : manager.getItems()) {
            if (item.getQuantity() <= LOW_STOCK_THRESHOLD) {
                System.out.printf(" ⚠️ Low Stock: %s (%s) - Quantity: %d%n",
                        item.getName(), item.getCode(), item.getQuantity());
                lowStockCount++;
            }
        }

        if (lowStockCount == 0) {
            System.out.println(" ✅ All items are above the low stock threshold of " + LOW_STOCK_THRESHOLD + ".");
        }
    }

    private void viewSalesReport() {
        SalesManager.getInstance().generateSalesReport();
    }

    private void updateItemStock() {
        System.out.println("\n--- Update Existing Item Stock ---");
        InventoryManager manager = InventoryManager.getInstance();

        System.out.print("\t ➤ Enter Item Code to update: ");
        String code = scanner.nextLine().trim();

        int quantityChange;
        while (true) {
            try {
                System.out.print("\t ➤ Enter Quantity Change (e.g., 5 to add 5, -3 to remove 3): ");
                quantityChange = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println(" ❌ Invalid input. Please enter an integer.");
            }
        }

        manager.updateItemQuantity(code, quantityChange);
    }
}