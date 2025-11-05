package sa.edu.uqu.supermarket.registration;

public class CredentialValidator {

    public static boolean isValidUsername(String username) {
        return username.length() >= 3 && username.length() <= 16 && !username.contains(" ");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8 && !password.contains(" ");
    }

    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10) {
            return false;
        }

        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

}
