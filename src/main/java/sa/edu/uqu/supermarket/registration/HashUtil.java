package sa.edu.uqu.supermarket.registration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

public class HashUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = 16;
    private static final MessageDigest SHA_256;

    static {
        try {
            SHA_256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hashPassword(String password) {
        byte[] salt = new byte[SALT_LENGTH];
        SECURE_RANDOM.nextBytes(salt);

        SHA_256.update(salt);
        byte[] hash = SHA_256.digest(password.getBytes());

        HexFormat hex = HexFormat.of();
        return hex.formatHex(salt) + ":" + hex.formatHex(hash);
    }

    public static boolean checkPassword(String storedHash, String password) {
        String[] parts = storedHash.split(":");
        if (parts.length != 2) {
            return false;
        }

        HexFormat hex = HexFormat.of();
        byte[] salt = hex.parseHex(parts[0]);
        String originalHash = parts[1];

        SHA_256.update(salt);
        byte[] comparisonHashBytes = SHA_256.digest(password.getBytes());
        String comparisonHash = hex.formatHex(comparisonHashBytes);

        return originalHash.equals(comparisonHash);
    }

}
