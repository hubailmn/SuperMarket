package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.SuperMarket;
import sa.edu.uqu.supermarket.inventory.InventoryManager;
import sa.edu.uqu.supermarket.inventory.Item;
import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Guest implements Menu {

    private final Scanner scanner = new Scanner(System.in);

    public Guest() {
        User user = new User();
    }

    @Override
    public void show() {
        System.out.println("\nWelcome, Guest!");
        System.out.println("Guest Menu:");

        System.out.println("""
                   1. View Products
                   2. Register
                   3. Back to Login Menu
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
            case 2 -> SuperMarket.getAuthenticationService().showMenu();
            case 3 -> {
                SuperMarket.getAuthenticationService().showMenu();
                return;
            }
            default -> System.out.println("‼️ Invalid option.");
        }

        if (choice != 2) {
            show();
        }
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
            System.out.printf("Code: %s, Name: %s, Price: %.2f%n", item.getCode(), item.getName(), item.getPrice());
        }
    }
}