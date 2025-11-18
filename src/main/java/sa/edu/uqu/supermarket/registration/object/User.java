package sa.edu.uqu.supermarket.registration.object;

import sa.edu.uqu.supermarket.registration.AccountsManager;
import sa.edu.uqu.supermarket.registration.HashUtil;
import sa.edu.uqu.supermarket.registration.data.Role;

public class User extends Person {

    private String username;
    private String passwordHash;
    private Role role;

    public User() {
        super(null, null, null, null);
        this.username = "Guest";
        this.passwordHash = null;
        this.role = Role.GUEST;
    }

    public User(String name, String lastName, String phoneNumber, String email, String username, String password, Role role) {
        super(name, lastName, phoneNumber, email);
        this.username = username;
        this.passwordHash = HashUtil.hashPassword(password);
        this.role = role;
    }

    public User(String name, String lastName, String phoneNumber, String email, String username, String passwordHash, Role role, boolean isHashed) {
        super(name, lastName, phoneNumber, email);
        this.username = username;

        if (isHashed) {
            this.passwordHash = passwordHash;
        } else {
            this.passwordHash = HashUtil.hashPassword(passwordHash);
        }

        this.role = role;
    }

    public User(User user) {
        super(user.getName(), user.getLastName(), user.getPhoneNumber(), user.getEmail());
        this.username = user.getUsername();
        this.passwordHash = user.getPasswordHash();
        this.role = user.getRole();
    }

    public void setPassword(String password) {
        this.passwordHash = HashUtil.hashPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void save() {
        AccountsManager.getInstance().saveUser(this);
    }

    public User copy() {
        return new User(this);
    }

    public String toString() {
        return String.format("User={username=%s, role=%s}", getUsername(), getRole().name());
    }

}
