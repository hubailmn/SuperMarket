package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

public class CustomerUser implements Menu {

    private final User user;

    public CustomerUser(User user) {
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("Welcome, " + user.getUsername() + " (Customer)");
        System.out.println("Customer Menu:");
        // TODO: Implement customer-specific options
    }
}
