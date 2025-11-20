package sa.edu.uqu.supermarket.user;

import sa.edu.uqu.supermarket.menu.Menu;
import sa.edu.uqu.supermarket.registration.object.User;

public class Manager implements Menu {

    private final User user;

    public Manager(User user) {
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("Welcome, " + user.getUsername() + " (Manager)");
        System.out.println("Manager Menu:");
        // TODO: Implement manager-specific options

//        options.add("Manage Products");
//        options.add("Check Inventory");
//        options.add("View Sales Report");
    }
}
