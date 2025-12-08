package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.SuperMarket;
import sa.edu.uqu.supermarket.inventory.InventoryManager;
import sa.edu.uqu.supermarket.inventory.Item;
import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.AccountsManager;
import sa.edu.uqu.supermarket.registration.CredentialValidator;
import sa.edu.uqu.supermarket.registration.data.Role;
import sa.edu.uqu.supermarket.registration.object.User;

import java.util.*;

public class Admin implements Menu {

    private final User user;
    private final Scanner scanner = new Scanner(System.in);
    private final AccountsManager accountsManager = AccountsManager.getInstance();

    public Admin(User user) {
        this.user = user;
        System.out.println("\nWelcome, " + user.getUsername() + " (Admin)");
    }

    @Override
    public void show() {

        LinkedList<String> options = new LinkedList<>();

        System.out.println("\nAdmin Menu:");

        options.add("Manage Users");
        options.add("Manage Inventory");
        options.add("Logout");

        for (String option : options) {
            System.out.printf("\t%2d" + ". %s%n", (options.indexOf(option) + 1), option);
        }

        int choice = -1;
        try {
            System.out.print("\n\t ➤ Enter option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }

        if (choice < 1 || choice > options.size()) {
            System.out.println("‼️ Invalid option. Please try again.");
            show();
            return;
        }

        String selectedOption = options.get(choice - 1);

        switch (selectedOption) {
            case "Manage Users" -> manageUsers();
            case "Manage Inventory" -> manageInventory();
            case "Logout" -> {
                System.out.println("Goodbye!");
                SuperMarket.getAuthenticationService().showMenu();
            }
            default -> System.out.println("Option not implemented yet.");
        }
    }

    private void manageInventory() {
        InventoryManager inventoryManager = InventoryManager.getInstance();

        System.out.println("\nManage Inventory Menu:");
        System.out.println("""
                    1. View All Items
                    2. Add New Item
                    3. Search Item
                    4. Back to Admin Menu
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
            case 1 -> {
                viewAllItems(inventoryManager.getItems());
                manageInventory();
            }
            case 2 -> {
                addNewItem(inventoryManager);
                manageInventory();
            }
            case 3 -> {
                searchItem(inventoryManager);
                manageInventory();
            }
            case 4 -> show();
            default -> {
                System.out.println("‼️ Invalid option. Enter 1 - 4");
                manageInventory();
            }
        }
    }

    private void viewAllItems(List<Item> items) {
        System.out.println("\n--- Current Inventory ---");
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    private void searchItem(InventoryManager manager) {
        System.out.print("\t ➤ Enter item name to search: ");
        String name = scanner.nextLine();
        List<Item> results = manager.searchItem(name);

        if (results.isEmpty()) {
            System.out.println(" ❌ No items found matching '" + name + "'");
        } else {
            System.out.println("\n--- Search Results ---");
            viewAllItems(results);
        }
    }

    private void addNewItem(InventoryManager manager) {
        System.out.println("\n--- Add New Item ---");

        String code;
        while (true) {
            System.out.print("\t ➤ Enter item code (unique): ");
            code = scanner.nextLine().trim();
            if (manager.getItemByCode(code) == null) {
                break;
            }
            System.out.println(" ❌ Item code already exists. Please enter a unique code.");
        }

        System.out.print("\t ➤ Enter item name: ");
        String name = scanner.nextLine().trim();

        System.out.print("\t ➤ Enter item description: ");
        String description = scanner.nextLine().trim();

        double price = 0.0;
        while (price <= 0.0) {
            try {
                System.out.print("\t ➤ Enter item price: ");
                price = scanner.nextDouble();
                scanner.nextLine();
                if (price <= 0.0) {
                    System.out.println(" ❌ Price must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println(" ❌ Invalid price format. Please enter a number.");
                price = 0.0;
            }
        }

        int quantity = -1;
        while (quantity < 0) {
            try {
                System.out.print("\t ➤ Enter item quantity: ");
                quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity < 0) {
                    System.out.println(" ❌ Quantity cannot be negative.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println(" ❌ Invalid quantity format. Please enter an integer.");
                quantity = -1;
            }
        }

        Item newItem = new Item(name, description, price, quantity, code);
        manager.addItem(newItem);
    }

    private void manageUsers() {
        System.out.println("\nManage Users Menu:");
        System.out.println("""
                    1. View All Users
                    2. Add New User
                    3. Remove User
                    4. Search User by Username
                    5. Back to Admin Menu
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
            case 1 -> viewAllUsers();
            case 2 -> addNewUser();
            case 3 -> removeUser();
            case 4 -> searchUser();
            case 5 -> show();
            default -> System.out.println("‼️ Invalid option. Enter 1 - 5");
        }

        if (choice != 5) {
            manageUsers();
        }
    }

    private void viewAllUsers() {
        System.out.println("\n--- Current Users ---");
        Map<String, User> users = accountsManager.getUsers();

        if (users.isEmpty()) {
            System.out.println("No user accounts available.");
            return;
        }

        users.values().stream()
                .filter(u -> u.getRole() != Role.GUEST)
                .forEach(user -> System.out.printf("Username: %s, Role: %s, Name: %s %s, Email: %s%n",
                        user.getUsername(),
                        user.getRole().name(),
                        user.getName(),
                        user.getLastName(),
                        user.getEmail()));
    }

    private void addNewUser() {
        System.out.println("\n--- Add New User ---");

        String username;
        do {
            System.out.print("\t ➤ Enter username: ");
            username = scanner.nextLine().trim();

            if (!CredentialValidator.isValidUsername(username)) {
                System.out.println(" ❌ Invalid username. Please ensure it is 3-16 characters long and contains no spaces.");
                continue;
            }

            if (accountsManager.getUser(username) != null) {
                System.out.println(" ❌ Username already exists. Please choose a different username.");
                continue;
            }
            break;
        } while (true);

        String password;
        do {
            System.out.print("\t ➤ Enter password: ");
            password = scanner.nextLine().trim();

            if (!CredentialValidator.isValidPassword(password)) {
                System.out.println(" ❌ Invalid password. Please ensure it is at least 8 characters long and contains no spaces.");
                continue;
            }
            break;
        } while (true);

        System.out.print("\t ➤ Enter user's first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("\t ➤ Enter user's last name: ");
        String lastName = scanner.nextLine().trim();

        String phoneNumber;
        do {
            System.out.print("\t ➤ Enter user's phone number (10 digits): ");
            phoneNumber = scanner.nextLine().trim();
            if (!CredentialValidator.isValidPhoneNumber(phoneNumber)) {
                System.out.println(" ❌ Invalid phone number. Please ensure it is 10 digits long.");
                continue;
            }
            break;
        } while (true);

        String email;
        do {
            System.out.print("\t ➤ Enter user's email: ");
            email = scanner.nextLine().trim();
            if (!CredentialValidator.isValidEmail(email)) {
                System.out.println(" ❌ Invalid email. Please ensure it contains '@' and '.'");
                continue;
            }
            break;
        } while (true);

        Role role = null;
        while (role == null) {
            System.out.println("\t ➤ Select Role:");
            Role[] roles = Role.values();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i] != Role.ADMIN && roles[i] != Role.GUEST) {
                    System.out.printf("\t\t%d. %s%n", (i + 1), roles[i].name());
                }
            }

            try {
                System.out.print("\t ➤ Enter role number: ");
                int roleChoice = scanner.nextInt();
                scanner.nextLine();

                if (roleChoice > 0 && roleChoice <= roles.length) {
                    Role selectedRole = roles[roleChoice - 1];
                    if (selectedRole != Role.ADMIN && selectedRole != Role.GUEST) {
                        role = selectedRole;
                    } else {
                        System.out.println(" ❌ Cannot assign role " + selectedRole.name() + " here.");
                    }
                } else {
                    System.out.println(" ❌ Invalid role number.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println(" ❌ Invalid input. Please enter a number.");
            }
        }

        User newUser = new User(firstName, lastName, phoneNumber, email, username, password, role);
        accountsManager.saveUser(newUser);
        System.out.println("\n ✅ User account created successfully for " + newUser.getUsername() + " with role " + newUser.getRole().name());
    }

    private void removeUser() {
        System.out.println("\n--- Remove User ---");
        System.out.print("\t ➤ Enter username to remove: ");
        String username = scanner.nextLine().trim();

        User userToRemove = accountsManager.getUser(username);

        if (userToRemove == null) {
            System.out.println(" ❌ User with username '" + username + "' not found.");
            return;
        }

        if (userToRemove.getRole() == Role.ADMIN) {
            System.out.println(" ❌ Cannot remove an Admin account through this menu.");
            return;
        }

        if (userToRemove.getUsername().equalsIgnoreCase(this.user.getUsername())) {
            System.out.println(" ❌ Cannot remove your own account while logged in.");
            return;
        }

        accountsManager.getUsers().remove(username);
        accountsManager.saveUsers();
        System.out.println("\n ✅ User '" + username + "' removed successfully.");
    }

    private void searchUser() {
        System.out.println("\n--- Search User by Username ---");
        System.out.print("\t ➤ Enter username to search: ");
        String username = scanner.nextLine().trim();

        User foundUser = accountsManager.getUser(username);

        if (foundUser == null || foundUser.getRole() == Role.GUEST) {
            System.out.println(" ❌ User with username '" + username + "' not found.");
        } else {
            System.out.println("\n--- User Found ---");
            System.out.printf("Username: %s%n", foundUser.getUsername());
            System.out.printf("Role: %s%n", foundUser.getRole().name());
            System.out.printf("Name: %s %s%n", foundUser.getName(), foundUser.getLastName());
            System.out.printf("Phone: %s%n", foundUser.getPhoneNumber());
            System.out.printf("Email: %s%n", foundUser.getEmail());
        }
    }

}