package SistemKursus;

import java.util.*;

public class CourseSystemDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Course> allCourses = new ArrayList<>();

        Map<String, User> userMap = UserFileManager.loadUsers(); 

        Quiz quiz1 = null;
        InstructorHandler instructorHandler = new InstructorHandler(scanner, allCourses);
        StudentHandler studentHandler = new StudentHandler(scanner, allCourses); 

        System.out.println("=== SELAMAT DATANG DI SISTEM KURSUS ONLINE ===");

        User currentUser = null;
        while (currentUser == null) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Pilih pilihan (1/2): ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Masukkan email: ");
                String inputEmail = scanner.nextLine();
                System.out.print("Masukkan password: ");
                String inputPassword = scanner.nextLine();

                User user = userMap.get(inputEmail);
                if (user != null && user.login(inputEmail, inputPassword)) {
                    currentUser = user;
                    System.out.println("\nLogin berhasil sebagai " + user.getRole() + ": \n" + user.getName());
                } else {
                    System.out.println("Email atau password salah. Coba lagi.\n");
                }
            } else if (choice.equals("2")) {
                System.out.print("Masukkan nama: ");
                String name = scanner.nextLine();
                System.out.print("Masukkan email: ");
                String email = scanner.nextLine();
                System.out.print("Masukkan password: ");
                String password = scanner.nextLine();

                System.out.println("Pilih peran: ");
                System.out.println("1. Student");
                System.out.println("2. Instructor");
                System.out.print("Masukkan pilihan : ");
                String roleChoice = scanner.nextLine();

                if (userMap.containsKey(email)) {
                    System.out.println("Email sudah terdaftar. Silakan coba login.");
                } else {
                    User newUser;
                    if (roleChoice.equals("1")) {
                        newUser = new Student(userMap.size() + 1, name, email, password, false);
                    } else if (roleChoice.equals("2")) {
                        newUser = new Instructor(userMap.size() + 1, name, email, password, false);
                    } else {
                        System.out.println("Peran tidak valid, silakan pilih lagi");
                        continue;
                    }

                    userMap.put(email, newUser);  
                    UserFileManager.saveUser(newUser);  
                    System.out.println("Registrasi berhasil! Silakan login dengan email dan password Anda");
                }
            } else {
                System.out.println("Pilihan tidak valid. Coba lagi!");
            }
        }

        RoleHandler roleHandler;
        if (currentUser instanceof Instructor) {
            roleHandler = instructorHandler;
        } else if (currentUser instanceof Student) {
            studentHandler.setStudent((Student) currentUser);
            roleHandler = studentHandler;
        } else {
            throw new IllegalStateException("Peran tidak dikenali");
        }

        MainMenu menu = new MainMenu(scanner, roleHandler, currentUser);
        menu.run();
    }
}
