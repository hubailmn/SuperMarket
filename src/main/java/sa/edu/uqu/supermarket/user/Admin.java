package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.SuperMarket;
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
        options.add("Logout");

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
            case "Manage Users" -> manageUsers();
            case "Logout" -> {
                System.out.println("Goodbye!");
                SuperMarket.getAuthenticationService().showMenu();
            }
            default -> System.out.println("Option not implemented yet.");
        }
    }

    private void manageUsers() {

        System.out.println("""
                Manage Users Menu:
                    1. View Users
                    2. Add User
                    3. Remove User
                    4. Back
                """);

        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            default -> System.out.println("Invalid option. Enter 1 - 4");
        }
    }

}
