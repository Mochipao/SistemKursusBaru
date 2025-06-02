package SistemKursus;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StudentHandler implements RoleHandler {
    private Scanner scanner;
    private List<Course> allCourses;
    private Student student;
    private Quiz quiz;

    public StudentHandler(Scanner scanner, List<Course> allCourses, Student student, Quiz quiz) {
        this.scanner = scanner;
        this.allCourses = allCourses;
        this.student = student;
        this.quiz = quiz;
    }

    public StudentHandler(Scanner scanner, List<Course> allCourses) {
        this.scanner = scanner;
        this.allCourses = allCourses;
        this.student = null;  
        this.quiz = null;     
    }

    public void showMenu() {
        boolean kembali = false;

        while (!kembali) {
            System.out.println("\n=== Menu Siswa ===");
            System.out.println("1. Pilih Course");
            System.out.println("2. Mulai Kuis");
            System.out.println("3. Belajar"); 
            System.out.println("0. Kembali ke Menu Utama");

            System.out.print("Pilih opsi: ");
            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        pilihCourse();
                        break;
                    case 2:
                        mulaiKuis();
                        break;
                    case 3:
                        belajarLesson();
                        break;
                    case 0:
                        kembali = true;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    private void pilihCourse() {
        if (allCourses.isEmpty()) {
            System.out.println(" Belum ada course yang tersedia");
            return;
        }

        System.out.println("\nDaftar Course yang Tersedia:");
        for (int i = 0; i < allCourses.size(); i++) {
            Course c = allCourses.get(i);
            System.out.println((i + 1) + ". " + c.getName() + " - " + c.getCategory());
        }

        System.out.print("Pilih nomor course yang ingin diikuti: ");
        int pilih = scanner.nextInt();
        scanner.nextLine();

        if (pilih >= 1 && pilih <= allCourses.size()) {
            Course selected = allCourses.get(pilih - 1);
            Enrollment enrollment = new Enrollment(3000 + pilih, student, selected);
            enrollment.enroll();
            System.out.println(" Anda telah terdaftar di: " + selected.getName());
        } else {
            System.out.println(" Pilihan tidak valid.");
        }
    }

    private void mulaiKuis() {
        if (allCourses.isEmpty()) {
            System.out.println(" Belum ada course yang tersedia");
            return;
        }

        try {
            System.out.println("\nPilih course untuk kuis:");
            for (int i = 0; i < allCourses.size(); i++) {
                System.out.println((i + 1) + ". " + allCourses.get(i).getName());
            }

            System.out.print("Pilih nomor course: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); 

            if (pilihan < 1 || pilihan > allCourses.size()) {
                System.out.println(" Pilihan tidak valid");
                return;
            }

            Course selectedCourse = allCourses.get(pilihan - 1);
            List<Quiz> quizzes = selectedCourse.getQuizzes();

            if (quizzes == null || quizzes.isEmpty()) {
                System.out.println(" Belum ada kuis untuk course ini");
                return;
            }

            System.out.println("\nDaftar Kuis untuk " + selectedCourse.getName() + ":");
            for (int i = 0; i < quizzes.size(); i++) {
                System.out.println((i + 1) + ". Kuis ID: " + quizzes.get(i).getQuizId());
            }

            System.out.print("Pilih nomor kuis untuk dimulai: ");
            int quizPilihan = scanner.nextInt();
            scanner.nextLine(); 

            if (quizPilihan < 1 || quizPilihan > quizzes.size()) {
                System.out.println(" Pilihan tidak valid.");
                return;
            }

            Quiz quizTerpilih = quizzes.get(quizPilihan - 1);
            quizTerpilih.startQuiz();

        } catch (InputMismatchException e) {
            System.out.println(" Input harus berupa angka. Silakan coba lagi!!");
            scanner.nextLine(); 
        } catch (Exception e) {
            System.out.println(" Terjadi kesalahan: " + e.getMessage());
        }
    }


    private void belajarLesson() {
        if (allCourses.isEmpty()) {
            System.out.println("Belum ada course yang tersedia.");
            return;
        }

        System.out.println("\n=== Daftar Course ===");
        for (int i = 0; i < allCourses.size(); i++) {
            Course course = allCourses.get(i);
            System.out.println((i + 1) + ". " + course.getName() + " - " + course.getCategory());
        }

        System.out.print("Pilih nomor course untuk belajar: ");
        int pilih;
        try {
            pilih = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            return;
        }

        if (pilih < 1 || pilih > allCourses.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        Course selectedCourse = allCourses.get(pilih - 1);
        List<Lesson> lessons = selectedCourse.getLessons();

        if (lessons.isEmpty()) {
            System.out.println("Belum ada pelajaran dalam course ini.");
            return;
        }

        System.out.println("\n=== Belajar: " + selectedCourse.getName() + " ===");

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            lesson.display();

            if (i < lessons.size() - 1) {
                System.out.print("Lanjut ke pelajaran berikutnya? (y/n): ");
                String lanjut = scanner.nextLine();
                if (!lanjut.equalsIgnoreCase("y")) {
                    break;
                }
            }
        }
    }


    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
