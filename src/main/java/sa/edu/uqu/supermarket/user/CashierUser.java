package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

public class CashierUser implements Menu {

    private final User user;

    public CashierUser(User user) {
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("Welcome, " + user.getUsername() + " (Cashier)");
        System.out.println("Cashier Menu:");
        // TODO: Implement cashier-specific options
    }
}
