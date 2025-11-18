package sa.edu.uqu.supermarket.registration;

import sa.edu.uqu.supermarket.registration.data.Role;
import sa.edu.uqu.supermarket.registration.object.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountsManager {

    public static final AccountsManager instance = new AccountsManager();

    private Map<String, User> users = new HashMap<>();

    public AccountsManager() {

        File file = new File("users.csv");
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println(" ❌ Failed to create users.csv file");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        loadUsers();
    }

    public static AccountsManager getInstance() {
        return instance;
    }

    public void loadUsers() {
        String csvFile = "users.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                if (data.length == 7) {
                    String username = data[0];
                    String hashedPassword = data[1];
                    String name = data[2];
                    String lastName = data[3];
                    String phoneNumber = data[4];
                    String email = data[5];
                    Role role = Role.valueOf(data[6].toUpperCase());

                    User user = new User(name, lastName, phoneNumber, email, username, hashedPassword, role, true);
                    users.put(username, user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load users from file");
        }

    }

    public void saveUsers() {
        String csvFile = "users.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            for (User user : users.values()) {
                String line = String.join(",",
                        user.getUsername(),
                        user.getPasswordHash(),
                        user.getName(),
                        user.getLastName(),
                        user.getPhoneNumber(),
                        user.getEmail(),
                        user.getRole().name());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save users to file");
        }
    }

    public void saveUser(User user) {
        users.put(user.getUsername(), user);
        saveUsers();
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public User login(String username, String password) {
        User user = users.get(username);

        if (user == null) {
            return null;
        }

        if (!HashUtil.checkPassword(user.getPasswordHash(), password)) {
            return null;
        }

        return user;
    }

}
