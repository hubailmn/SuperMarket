package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

import java.util.LinkedList;
import java.util.Scanner;

public class Admin implements Menu {

    private final User user;

    public Admin(User user) {
        this.user = user;
        System.out.println("\nWelcome, " + user.getUsername() + " (Admin)");
    }

    @Override
    public void show() {

        LinkedList<String> options = new LinkedList<>();

        System.out.println("\nAdmin Menu:");

        options.add("Manage Users");
        options.add("sss");
        options.add("ssss");
        options.add("sssss");
        options.add("ssssss");

        for (String option : options) {
            System.out.printf("\t%2d" + ". %s%n", (options.indexOf(option) + 1), option);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("\n\t ➤ Enter option: ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > options.size()) {
            System.out.println("‼️ Invalid option. Please try again.");
            show();
            return;
        }

        String selectedOption = options.get(choice - 1);

        switch (selectedOption) {
            case "Manage Users" -> System.out.println("Navigating to User Management...");
            case "Manage Discounts" -> System.out.println("Navigating to Discount Management...");
            default -> System.out.println("Option not implemented yet.");
        }
    }

}
