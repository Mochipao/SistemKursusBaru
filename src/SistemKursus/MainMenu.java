package SistemKursus;

import java.util.Scanner;

public class MainMenu {
    private Scanner scanner;
    private RoleHandler roleHandler;
    private User currentUser;

    public MainMenu(Scanner scanner, RoleHandler roleHandler, User currentUser) {
        this.scanner = scanner;
        this.roleHandler = roleHandler;
        this.currentUser = currentUser;
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== Menu Pengguna =====");
            System.out.println("1. Lanjutkan sebagai " + currentUser.getRole());
            System.out.println("2. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    roleHandler.showMenu();
                    break;
                case 2:
                    exit = true;
                    System.out.println("Terima kasih telah menggunakan sistem kursus");
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }
}
