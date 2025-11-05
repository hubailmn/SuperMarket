package sa.edu.uqu.supermarket;

import sa.edu.uqu.supermarket.registration.AccountsManager;
import sa.edu.uqu.supermarket.registration.CredentialValidator;
import sa.edu.uqu.supermarket.registration.data.Role;
import sa.edu.uqu.supermarket.registration.objects.User;

import java.util.LinkedList;
import java.util.Scanner;

public class SuperMarket {

    public static void main(String[] args) {
        System.out.println("Welcome to the SuperMarket application 🛒");

        Scanner scan = new Scanner(System.in);

        if (AccountsManager.getInstance().getUsers().isEmpty()) {

            System.out.println("\n ‼️ Please create an Admin account");

            String username;
            String password;

            do {
                boolean validUsername = true;

                System.out.print("\t ➤ Enter username: ");
                username = scan.nextLine().toLowerCase().trim();

                if (username.contains(" ")) {
                    System.out.println(" ❌ Username cannot contain spaces");
                    validUsername = false;
                }

                if (username.length() < 3 || username.length() >= 16) {
                    System.out.println(" ❌ Username must be between 3 and 16 characters");
                    validUsername = false;
                }

                if (AccountsManager.getInstance().getUsers().containsKey(username)) {
                    System.out.println(" ❌ Username already exists");
                    validUsername = false;
                }

                if (!validUsername) {
                    System.out.println("\n 🔁 Invalid username. Please try using another username.");
                }

            } while (
                    AccountsManager.getInstance().getUsers().containsKey(username) ||
                            (username.length() < 3 || username.length() >= 16) ||
                            username.contains(" ")

            );

            System.out.print("\t ➤ Enter password: ");
            password = scan.nextLine().trim();

            User adminUser = new User("AdminName", "AdminLastNme", "96655xxxxxx", "admin@super.market", username, password, Role.ADMIN);
            adminUser.save();

            System.out.println("\n ✅ Admin account created successfully");
        }

        loginMenu();

    }

    private static void loginMenu() {
        System.out.println("""
                
                Login Menu:
                   1. Login to an account
                   2. Create a new account
                   3. Continue as a guest.
                   4. Quit
                """);

        Scanner scanner = new Scanner(System.in);

        System.out.print("\t ➤ Enter option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> {
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
                    }

                    if (tries == 0) {
                        System.out.println("\n 🚨 You have exceeded the maximum number of tries.");
                        System.out.println(" 🚨 Exiting the program.");
                        System.exit(0);
                    }

                } while (user == null);

                mainMenu(user);
            }
            case "2" -> {
                System.out.println("\n Create a new account");

                User user;
                do {
                    System.out.print("\t ➤ Enter username: ");
                    String username = scanner.nextLine().trim();

                    if (!CredentialValidator.isValidUsername(username)) {
                        System.out.println(" ❌ Invalid username. Please ensure it is 3-16 characters long and contains no spaces.");
                        continue;
                    }

                    System.out.print("\t ➤ Enter password: ");
                    String password = scanner.nextLine().trim();

                    if (!CredentialValidator.isValidPassword(password)) {
                        System.out.println(" ❌ Invalid password. Please ensure it is at least 8 characters long and contains no spaces.");
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
                        continue;
                    }

                    System.out.print("\t ➤ Enter your email: ");
                    String email = scanner.nextLine().trim();

                    if (!CredentialValidator.isValidEmail(email)) {
                        System.out.println(" ❌ Invalid email. Please ensure it contains '@' and '.'");
                        continue;
                    }

                    user = new User(firstName, lastName, phoneNumber, email, username, password, Role.CUSTOMER);
                    AccountsManager.getInstance().saveUser(user);
                    System.out.println("\n ✅ Account created successfully!");
                    break;

                } while (true);
            }
            case "3" -> {
                System.out.println("\n Continue as guest");
            }
            case "4" -> {
                System.out.println("\n Quitting the program.");

                System.exit(0);
            }
            default -> {
                System.out.println(" ‼️ Invalid option");
                loginMenu();

            }
        }

    }

    private static void mainMenu(User user) {

        LinkedList<String> options = new LinkedList<>();

        System.out.println("\n 🌞 Welcome " + user.getName() + "! what would you like to do?");

        System.out.println("SuperMarket Menu:");

        if (user.getRole() == Role.ADMIN) {
            //TODO: show admin options
            // able to edit users and manage accounts
            options.add("Manage Users");
        }

        if (user.getRole() == Role.MANAGER) {
            //TODO: show manager options
            // able to edit store items,
            // and apply discounts
            options.add("Manage Store Items");
            options.add("Manage Discounts");
        }

        if (user.getRole() == Role.CASHIER) {
            //TODO: show catchier options
        }

        //TODO: print normal menu
        options.add("Shop");

        for (int i = 0; i < options.toArray().length; i++) {
            System.out.println("\t " + (i + 1) + ". " + options.toArray()[i]);
        }

        Scanner scanner = new Scanner(System.in);
        String option = String.valueOf(options.toArray()[scanner.nextInt() - 1]);

        switch (option) {
            case "Manage Users" -> {
                System.out.println("Manage Users");
            }
            case "Manage Discounts" -> {
                System.out.println("Manage Discounts");
            }
            case "Manage Store Items" -> {
                System.out.println("Manage Store Items");
            }
            case "shop" -> {
                System.out.println("Shop");
            }
        }

    }
}
