package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

public class Guest implements Menu {

    private final User user;

    public Guest() {
        this.user = new User();
    }

    @Override
    public void show() {
        System.out.println("Welcome, Guest!");
        System.out.println("Guest Menu:");
        // TODO: Implement guest-specific options (e.g., view products, register)

//        options.add("View Products");
//        options.add("View cart");
//        options.add("Register");
//        options.add("Checkout");
    }
}
