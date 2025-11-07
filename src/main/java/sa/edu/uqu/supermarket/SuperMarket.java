package sa.edu.uqu.supermarket;

import sa.edu.uqu.supermarket.registration.AccountsManager;
import sa.edu.uqu.supermarket.registration.Authentication;
import sa.edu.uqu.supermarket.registration.data.Role;
import sa.edu.uqu.supermarket.registration.objects.User;

import java.util.Scanner;

public class SuperMarket {

    public static void main(String[] args) {
        System.out.println("\nWelcome to the SuperMarket application 🛒");

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

                if (!validUsername) {
                    System.out.println("\n 🔁 Invalid username. Please try using another username");
                }

            } while (
                    AccountsManager.getInstance().getUsers().containsKey(username) ||
                            (username.length() < 3 || username.length() >= 16) ||
                            username.contains(" ")

            );

            System.out.print("\t ➤ Enter password: ");
            password = scan.nextLine().trim();

            User adminUser = new User("AdminName", "AdminLastNme", "0555555555", "admin@super.market", username, password, Role.ADMIN);
            adminUser.save();

            System.out.println("\n ✅ Admin account created successfully");
        }

        Authentication authenticationService = new Authentication();

        authenticationService.showAuthenticationMenu();

    }

}
