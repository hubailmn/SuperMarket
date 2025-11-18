package sa.edu.uqu.supermarket.menu;

import sa.edu.uqu.supermarket.registration.object.User;
import sa.edu.uqu.supermarket.user.*;

public class MenuFactory {

    public static Menu getMenu(User user) {
        if (user == null) {
            return new GuestUser();
        }

        return switch (user.getRole()) {
            case ADMIN -> new AdminUser(user);
            case MANAGER -> new ManagerUser(user);
            case CASHIER -> new CashierUser(user);
            case CUSTOMER -> new CustomerUser(user);
            default -> new GuestUser();
        };
    }
}
