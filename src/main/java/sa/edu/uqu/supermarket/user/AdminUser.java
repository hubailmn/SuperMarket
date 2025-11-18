package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

public class AdminUser implements Menu {

    private final User user;

    public AdminUser(User user) {
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("Welcome, " + user.getUsername() + " (Admin)");
        System.out.println("Admin Menu:");
        // TODO: Implement admin-specific options
    }
}
