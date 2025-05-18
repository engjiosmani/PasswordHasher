package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordHasher {


    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    public static String getFullHashingProcessExplanation(String password) {
        StringBuilder sb = new StringBuilder();

        try {
            byte[] saltBytes = generateSalt();
            String saltBase64 = encodeSalt(saltBytes);

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hashBytes = factory.generateSecret(spec).getEncoded();
            String hashBase64 = Base64.getEncoder().encodeToString(hashBytes);

            sb.append("PASSWORD HASHING PROCESS\n");
            sb.append("------------------------------\n");
            sb.append("1. Input Password: ").append(password).append("\n\n");

            sb.append("2. Generated Salt (Raw Bytes):\n");
            for (byte b : saltBytes) {
                sb.append(String.format("%02x ", b));
            }
            sb.append("\n\n");

            sb.append("3. Generated Salt (Base64): ").append(saltBase64).append("\n\n");
            sb.append("4. Algorithm: ").append(ALGORITHM).append("\n\n");
            sb.append("5. Iterations: ").append(ITERATIONS).append("\n\n");
            sb.append("6. Key Length: ").append(KEY_LENGTH).append(" bits\n\n");