package sa.edu.uqu.supermarket.registration;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashUtil {

    private static final MessageDigest SHA_256;

    static {
        try {
            SHA_256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hashPassword(String password) {
        byte[] hash = SHA_256.digest(password.getBytes(StandardCharsets.UTF_8));

        return HexFormat.of().formatHex(hash);
    }

    public static boolean checkPassword(String hashedPassword, String password) {
        byte[] hashBytes = SHA_256.digest(password.getBytes(StandardCharsets.UTF_8));
        String hash = HexFormat.of().formatHex(hashBytes);

        return hashedPassword.equals(hash);
    }

}
