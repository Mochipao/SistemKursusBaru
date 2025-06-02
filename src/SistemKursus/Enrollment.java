package SistemKursus;

import java.util.Scanner;

public class Enrollment implements Enrollable {
    private int enrollmentId;
    private Student student;
    private Course course;
    private Payment payment;

    public Enrollment(int enrollmentId, Student student, Course course) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;

        this.payment = new Payment(
            enrollmentId, 
            course.getPrice(),
            "Unpaid",
            student.getName(),
            course.getInstructorName(),
            course.getName()
        );
    }

    @Override
    public void enroll() {
        Scanner scanner = new Scanner(System.in);

        try {
            if (course == null) throw new NullPointerException("Kursus tidak ditemukan");
            if (student == null) throw new NullPointerException("Data siswa tidak ditemukan");
            if (course.getPrice() < 0) throw new IllegalArgumentException("Harga kursus tidak valid");
            if (course.getInstructorName() == null || course.getInstructorName().trim().isEmpty()) {
                throw new IllegalArgumentException("Nama instruktur tidak boleh kosong");
            }

            System.out.println("\n--- Informasi Pembayaran ---");
            System.out.println("Nama Kursus       : " + course.getName());
            System.out.println("Kategori          : " + course.getCategory());
            System.out.println("Harga             : Rp" + course.getPrice());
            System.out.println("Rekening Pembayaran a.n: " + course.getInstructorName());

            System.out.print("Apakah Anda sudah membayar? (yes/no): ");
            String konfirmasi = scanner.nextLine().trim();

            if (konfirmasi.equalsIgnoreCase("yes")) {
                payment.processPayment();
                System.out.println(student.getName() + " telah berhasil mendaftar ke kursus: " + course.getName());
            } else if (konfirmasi.equalsIgnoreCase("no")) {
                System.out.println("Pendaftaran dibatalkan. Silakan lakukan pembayaran terlebih dahulu!!");
            } else {
                System.out.println("Input tidak dikenali. Silakan masukkan 'yes' atau 'no'");
            }

        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Gagal melakukan pendaftaran: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan tidak terduga: " + e.getMessage());
        }
    }
}
