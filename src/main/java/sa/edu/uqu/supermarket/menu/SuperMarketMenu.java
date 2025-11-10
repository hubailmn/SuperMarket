package sa.edu.uqu.supermarket.menu;

import sa.edu.uqu.supermarket.registration.objects.User;

import java.util.LinkedList;
import java.util.Scanner;

public class SuperMarketMenu {

    public static void showMainMenu(User user) {
        LinkedList<String> options = new LinkedList<>();

        System.out.println("\n 🌞 Welcome " + user.getName() + "! what would you like to do?");
        System.out.println("\nSuperMarket Menu:");

        switch (user.getRole()) {
            case ADMIN:
                options.add("Manage Users");
            case MANAGER:
                options.add("Check Inventory");
                options.add("View Sales Report");
                options.add("Manage Discounts");
                options.add("Manage Store Items");
            case CASHIER:
                options.add("Scan Item");
                options.add("Process Refund");
                options.add("Close Register");
            case CUSTOMER:
                options.add("Shop");
                options.add("View cart");
                options.add("Checkout");
                options.add("View Order History");
                options.add("Update Profile");
                options.add("View Deals");
                options.add("Logout");
                break;
            case GUEST:
                options.add("Shop");
                options.add("View cart");
                options.add("Checkout");
                break;
        }

        for (int i = 0; i < options.size(); i++) {
            System.out.printf("\n\t %2d" + ". %s", (i + 1), options.get(i));
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("\n\n\t ➤ Enter option: ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > options.size()) {
            System.out.println("‼️ Invalid option. Please try again.");
            showMainMenu(user);
            return;
        }

        String selectedOption = options.get(choice - 1);

        switch (selectedOption) {
            case "Manage Users" -> System.out.println("Navigating to User Management...");
            case "Manage Discounts" -> System.out.println("Navigating to Discount Management...");
            case "Manage Store Items" -> System.out.println("Navigating to Store Item Management...");
            case "Shop" -> System.out.println("Navigating to Shop...");
            default -> System.out.println("Option not implemented yet.");
        }
    }
}
