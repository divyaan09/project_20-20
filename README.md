# 🔐 Secure Password Vault (Java, AES Encryption)

This is a simple **Java-based Secure Password Vault** that allows users to securely store and retrieve passwords using AES encryption. It's designed for learning and personal use.

---

## 📌 Features

- 🔑 Master password-based AES-128 encryption
- 💾 Save multiple website credentials (website, username, password)
- 🔓 Decrypt credentials with the correct master password
- ✅ Input validation and file-based storage
- 🔒 Encryption key is always 128-bit padded or derived securely

---

## 📁 File Structure

```

SecurePasswordVault/
├── SecurePasswordVault.java     # Main program
├── credentials.enc              # Encrypted credentials (created at runtime)
└── README.md                    # You're reading this!

````

---

## ⚙️ How It Works

1. **User enters a master password**
2. The password is converted into a 16-byte AES encryption key
3. Data is encrypted and saved to a file
4. To read data, the correct master password must be entered again

---

## 🔧 Setup Instructions

### ✅ Requirements

- Java Development Kit (JDK) 8 or later
- Text editor or IDE (e.g., IntelliJ, Eclipse, VSCode)

### 🚀 Compile & Run

```bash
javac SecurePasswordVault.java
java SecurePasswordVault
````

---

## 🧠 How Encryption Works

This app uses AES in ECB mode (for simplicity in this example).

### 🔐 Key Generation

To fix potential errors like:

```
Exception in thread "main" java.lang.IllegalArgumentException: Invalid offset/length combination
```

We ensure the key is **always 16 bytes** with this logic:

```java
byte[] keyBytes = new byte[16];
byte[] passwordBytes = masterPassword.getBytes();
for (int i = 0; i < Math.min(passwordBytes.length, 16); i++) {
    keyBytes[i] = passwordBytes[i];
}
SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
```

---

## 🧪 Sample Input/Output

```
Welcome to Secure Password Vault
Enter master password: *****

1. Save credentials
2. View credentials
3. Exit
Choice: 1

Enter website: example.com
Enter username: testuser
Enter password: mySecurePass123
Credentials saved successfully.
```

---

## 🚫 Limitations

* No password strength checks
* No UI (CLI-based only)
* Uses ECB mode (not suitable for production — use CBC or GCM in real apps)

---

## ✅ To-Do & Improvements

* GUI with Swing or JavaFX
* Store data using a database (SQLite)
* Master password hashing (SHA-256 + PBKDF2)
* Add password strength meter
* Use AES/CBC or AES/GCM with IV

---

## ⚠ Disclaimer

> 🛡️ This tool is intended for **educational purposes only**. Do not use this in production or to store real sensitive data.

---

## 👨‍💻 Author

**Divyanshu**
Java & Cybersecurity Enthusiast





Happy coding!
