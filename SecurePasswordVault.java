import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.io.*;

public class SecurePasswordVault {

    private static final String FILE_NAME = "vault.txt";
    private static final String AES = "AES";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter master password: ");
        String masterPassword = scanner.nextLine();
        SecretKey secretKey = new SecretKeySpec(masterPassword.getBytes(), 0, 16, AES);  // 16-byte key

        while (true) {
            System.out.println("\n--- Secure Password Vault ---");
            System.out.println("1. Save new password");
            System.out.println("2. View saved passwords");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter account name: ");
                    String account = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    String encrypted = encrypt(password, secretKey);
                    saveToFile(account + ":" + encrypted);
                    System.out.println("✅ Password saved securely.");
                    break;

                case 2:
                    System.out.println("\n🔐 Stored Passwords:");
                    readAndDecryptFile(secretKey);
                    break;

                case 3:
                    System.out.println("👋 Exiting vault.");
                    return;

                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private static String encrypt(String data, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encVal);
        } catch (Exception e) {
            return null;
        }
    }

    private static String decrypt(String encryptedData, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            byte[] decVal = cipher.doFinal(decoded);
            return new String(decVal);
        } catch (Exception e) {
            return "[Decryption failed]";
        }
    }

    private static void saveToFile(String line) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(line + "\n");
        } catch (IOException e) {
            System.out.println("⚠️ Failed to save password.");
        }
    }

    private static void readAndDecryptFile(SecretKey key) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String account = parts[0];
                    String decryptedPassword = decrypt(parts[1], key);
                    System.out.println(account + " ➤ " + decryptedPassword);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("📂 No saved passwords found.");
        } catch (IOException e) {
            System.out.println("⚠️ Error reading file.");
        }
    }
}
