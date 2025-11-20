package sa.edu.uqu.supermarket.registration;

import sa.edu.uqu.supermarket.menu.MenuFactory;
import sa.edu.uqu.supermarket.registration.data.Role;
import sa.edu.uqu.supermarket.registration.object.User;

import java.util.Scanner;

public class Authentication {

    private final AccountsManager accountsManager = AccountsManager.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void showAuthenticationMenu() {
        System.out.println("""
                
                Login Menu:
                   1. Login to an account
                   2. Create a new account
                   3. Continue as a guest
                   4. Quit
                """);

        System.out.print("\t ➤ Enter option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> login();
            case "2" -> createAccount();
            case "3" -> {
                User user = new User("Guest", "Guest", "05555555555", "Guest@super.market", "guest", "guest", Role.GUEST);
                MenuFactory.getMenu(user).show();
            }
            case "4" -> {
                System.out.println("\n Quitting the program");
                System.exit(0);
            }
            default -> {
                System.out.println(" ‼️ Invalid option");
                showAuthenticationMenu();
            }
        }
    }

    private void login() {
        System.out.println("\n Login to an account");

        User user;
        int tries = 3;

        do {
            System.out.print("\t ➤ Enter username: ");
            String username = scanner.nextLine().toLowerCase().trim();

            System.out.print("\t ➤ Enter password: ");
            String password = scanner.nextLine().trim();

            user = AccountsManager.getInstance().login(username, password);

            if (user == null) {
                System.out.println(" ❌ Invalid username or password. ");
                tries--;

                System.out.print("\n ⚠️ Do you want to try again? (y/n)");
                if (scanner.nextLine().equalsIgnoreCase("n")) {
                    showAuthenticationMenu();
                }
            }

            if (tries == 0) {
                System.out.println("\n 🚨 You have exceeded the maximum number of tries.");
                System.out.println(" 🚨 Exiting the program.");
                System.exit(0);
            }

        } while (user == null);

        MenuFactory.getMenu(user).show();
    }

    private void createAccount() {
        System.out.println("\n Create a new account");

        User user;
        int tries = 3;
        do {

            if (tries <= 0) {
                System.out.print(" ⚠️ Get back to the login menu? (y/n): ");
                if (!scanner.nextLine().equalsIgnoreCase("n")) {
                    showAuthenticationMenu();
                }

            }

            System.out.print("\t ➤ Enter username: ");
            String username = scanner.nextLine().trim();

            if (!CredentialValidator.isValidUsername(username)) {
                System.out.println(" ❌ Invalid username. Please ensure it is 3-16 characters long and contains no spaces.");
                tries--;
                continue;
            }

            if (accountsManager.getUser(username) != null) {
                System.out.println(" ❌ Username already exists. Please choose a different username.");
                tries--;
                continue;
            }

            System.out.print("\t ➤ Enter password: ");
            String password = scanner.nextLine().trim();

            if (!CredentialValidator.isValidPassword(password)) {
                System.out.println(" ❌ Invalid password. Please ensure it is at least 8 characters long and contains no spaces.");
                tries--;
                continue;
            }

            System.out.print("\t ➤ Enter your first name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("\t ➤ Enter your last name: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("\t ➤ Enter your phone number: ");
            String phoneNumber = scanner.nextLine().trim();

            if (!CredentialValidator.isValidPhoneNumber(phoneNumber)) {
                System.out.println(" ❌ Invalid phone number. Please ensure it is 10 digits long.");
                tries--;
                continue;
            }

            System.out.print("\t ➤ Enter your email: ");
            String email = scanner.nextLine().trim();

            if (!CredentialValidator.isValidEmail(email)) {
                System.out.println(" ❌ Invalid email. Please ensure it contains '@' and '.'");
                tries--;
                continue;
            }

            user = new User(firstName, lastName, phoneNumber, email, username, password, Role.CUSTOMER);
            AccountsManager.getInstance().saveUser(user);
            System.out.println("\n ✅ Account created successfully!");
            break;

        } while (true);

        MenuFactory.getMenu(user).show();
    }
}
