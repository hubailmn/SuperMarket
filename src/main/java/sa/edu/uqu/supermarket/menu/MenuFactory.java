package sa.edu.uqu.supermarket.menu;

import sa.edu.uqu.supermarket.registration.object.User;
import sa.edu.uqu.supermarket.user.*;

public class MenuFactory {

    public static Menu getMenu(User user) {
        if (user == null) {
            return new Guest();
        }

        return switch (user.getRole()) {
            case ADMIN -> new Admin(user);
            case MANAGER -> new Manager(user);
            case CASHIER -> new Cashier(user);
            case CUSTOMER -> new Customer(user);
            default -> new Guest();
        };
    }
}
