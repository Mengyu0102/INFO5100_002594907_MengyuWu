import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.*;
import java.util.Base64;

public class CryptographyDemo {

    // --- 1. Symmetric Encryption (AES-256 GCM) ---
    private static void demonstrateSymmetricEncryption() throws Exception {
        System.out.println("\n=== 1. Symmetric Encryption (AES-256 GCM) ===");

        // 1.1 Alice Generates a Shared Secret Key (Must be shared securely)
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        SecretKey sharedSecretKey = keyGen.generateKey();

        String originalMessage = "Hello Bob, this is Alice's secret symmetric message!";
        System.out.println("Alice's original message: " + originalMessage);

        // 1.2 Alice Encrypts the Message
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");

        // GCM requires an Initialization Vector (IV) for security
        byte[] iv = new byte[12]; // 96 bits for GCM
        new SecureRandom().nextBytes(iv);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); // 128 bit authentication tag length
        aesCipher.init(Cipher.ENCRYPT_MODE, sharedSecretKey, parameterSpec);

        byte[] encryptedBytes = aesCipher.doFinal(originalMessage.getBytes("UTF-8"));
        String encryptedB64 = Base64.getEncoder().encodeToString(encryptedBytes);

        System.out.println("Encrypted (Base64): " + encryptedB64);

        // 1.3 Bob Decrypts the Message (Must have the same sharedSecretKey and IV)

        // Create a new cipher instance for decryption
        Cipher decryptCipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Bob must use the same key and the same IV (IV must be sent alongside the ciphertext)
        decryptCipher.init(Cipher.DECRYPT_MODE, sharedSecretKey, parameterSpec);

        byte[] decryptedBytes = decryptCipher.doFinal(encryptedBytes);
        String decryptedMessage = new String(decryptedBytes, "UTF-8");

        System.out.println("Bob's decrypted message:  " + decryptedMessage);
        System.out.println("Verification: " + originalMessage.equals(decryptedMessage));
    }

    // --- 2. Asymmetric Encryption (RSA-2048) ---
    private static void demonstrateAsymmetricEncryption() throws Exception {
        System.out.println("\n=== 2. Asymmetric Encryption (RSA-2048) ===");

        // 2.1 Bob Generates Key Pair (Public Key shared, Private Key kept secret)
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // RSA-2048
        KeyPair bobKeyPair = keyPairGenerator.generateKeyPair();
        PublicKey bobPublicKey = bobKeyPair.getPublic();
        PrivateKey bobPrivateKey = bobKeyPair.getPrivate();

        String originalMessage = "Secret message for Bob using RSA!";
        System.out.println("Alice's original message: " + originalMessage);

        // 2.2 Alice Encrypts the Message using Bob's Public Key
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, bobPublicKey);

        byte[] encryptedBytes = rsaCipher.doFinal(originalMessage.getBytes("UTF-8"));
        String encryptedB64 = Base64.getEncoder().encodeToString(encryptedBytes);

        System.out.println("Encrypted (Base64): " + encryptedB64.substring(0, 40) + "..."); // Shorten for display

        // 2.3 Bob Decrypts the Message using his Private Key
        Cipher decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, bobPrivateKey);

        byte[] decryptedBytes = decryptCipher.doFinal(encryptedBytes);
        String decryptedMessage = new String(decryptedBytes, "UTF-8");

        System.out.println("Bob's decrypted message:  " + decryptedMessage);
        System.out.println("Verification: " + originalMessage.equals(decryptedMessage));
    }

    // --- 3. Digital Signature (RSA-2048) ---
    private static void demonstrateDigitalSignature() throws Exception {
        System.out.println("\n=== 3. Digital Signature (RSA-2048) ===");

        // 3.1 Alice Generates Key Pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair aliceKeyPair = keyPairGenerator.generateKeyPair();
        PrivateKey alicePrivateKey = aliceKeyPair.getPrivate();
        PublicKey alicePublicKey = aliceKeyPair.getPublic(); // Shared with Bob for verification

        String messageToSign = "This document confirms that Alice approves the contract.";
        System.out.println("Message to be signed: " + messageToSign);

        // 3.2 Alice Signs the Message using her Private Key
        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(alicePrivateKey);
        signer.update(messageToSign.getBytes("UTF-8"));
        byte[] signatureBytes = signer.sign();
        String signatureB64 = Base64.getEncoder().encodeToString(signatureBytes);

        System.out.println("Signature (Base64): " + signatureB64.substring(0, 40) + "...");

        // 3.3 Bob Verifies the Signature using Alice's Public Key

        // Scenario 1: Successful Verification
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(alicePublicKey);
        verifier.update(messageToSign.getBytes("UTF-8"));
        boolean isVerified = verifier.verify(signatureBytes);

        System.out.println("\nBob's verification result (Original Message): " + isVerified);

        // Scenario 2: Verification Failure (Message Tampered)
        String tamperedMessage = "This document confirms that Alice approves the contract. (TAMPERED)";

        verifier.initVerify(alicePublicKey);
        verifier.update(tamperedMessage.getBytes("UTF-8"));
        boolean isTamperedVerified = verifier.verify(signatureBytes);

        System.out.println("Bob's verification result (Tampered Message): " + isTamperedVerified);
    }

    public static void main(String[] args) {
        try {
            demonstrateSymmetricEncryption();
            demonstrateAsymmetricEncryption();
            demonstrateDigitalSignature();
        } catch (Exception e) {
            System.err.println("An error occurred during cryptography demonstration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}