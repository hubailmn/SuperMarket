package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

public class GuestUser implements Menu {

    private final User user;

    public GuestUser() {
        this.user = new User();
    }

    @Override
    public void show() {
        System.out.println("Welcome, Guest!");
        System.out.println("Guest Menu:");
        // TODO: Implement guest-specific options (e.g., view products, register)
    }
}
