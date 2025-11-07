package sa.edu.uqu.supermarket.menu;

import sa.edu.uqu.supermarket.registration.objects.User;

import java.util.LinkedList;
import java.util.Scanner;

public class SuperMarketMenu {

    public static void showMainMenu(User user) {
        LinkedList<String> options = new LinkedList<>();

        System.out.println("\nSuperMarket Menu:");

        switch (user.getRole()) {
            case ADMIN:
                options.add("Manage Users");
            case MANAGER:
                options.add("Manage Store Items");
                options.add("Manage Discounts");
            case CASHIER:
            case CUSTOMER:
                options.add("Shop");
                options.add("Logout");
                break;
            case GUEST:
                options.add("Shop");
                break;
        }

        for (int i = 0; i < options.size(); i++) {
            System.out.println("\t " + (i + 1) + ". " + options.get(i));
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("\n\t ➤ Enter option: ");
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
