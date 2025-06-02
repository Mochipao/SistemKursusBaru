package SistemKursus;

import java.io.*;
import java.util.*;

public class UserFileManager {

    private static final String FILE_NAME = "users.txt";

    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(user.toDataString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data pengguna: " + e.getMessage());
        }
    }


    public static Map<String, User> loadUsers() {
        Map<String, User> userMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String email = userData[0];
                String name = userData[1];
                String password = userData[2];
                String role = userData[3];
                int id = userMap.size() + 1;
                boolean isHashed = password.matches("[a-fA-F0-9]{64}");
                User user = createUserByRole(role, email, name, password, id, isHashed);
                userMap.put(email, user);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data pengguna: " + e.getMessage());
        }
        return userMap;
    }

    private static User createUserByRole(String role, String email, String name, String password, int id, boolean alreadyHashed) {
        if ("Instructor".equals(role)) {
            return new Instructor(id, name, email, password, alreadyHashed);
        } else {
            return new Student(id, name, email, password, alreadyHashed);
        }
    }
}
