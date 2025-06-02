package SistemKursus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public abstract class User {
    protected int id;
    protected String name;
    protected String email;
    protected String password;

    public User(int id, String name, String email, String password, boolean alreadyHashed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = alreadyHashed ? password : hashPassword(password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    protected String getPasswordForStorage() {
        return password;
    }

    public String toDataString() {
        return email + "," + name + "," + getPasswordForStorage() + "," + getRole();
    }

    public boolean login(String email, String enteredPassword) {
        return this.email.equals(email) && verifyPassword(enteredPassword, this.password);
    }

    public void setPassword(String newPassword) {
        this.password = hashPassword(newPassword);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing gagal: " + e.getMessage());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        return Objects.equals(hashPassword(enteredPassword), storedPassword);
    }

    public abstract String getRole();
}
