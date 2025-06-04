package SistemKursus;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private int paymentId;
    private double amount;
    private String status;
    private String studentName;
    private String instructorName;
    private String courseName;

    public Payment(int paymentId, double amount, String status, String studentName, String instructorName, String courseName) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
        this.studentName = studentName;
        this.instructorName = instructorName;
        this.courseName = courseName;
    }

    public void processPayment() {
        if (!status.equalsIgnoreCase("Paid")) {
            status = "Paid";
            System.out.println("Pembayaran sebesar Rp" + amount + " telah diproses");

            savePaymentToFile();
        } else {
            System.out.println("Pembayaran sudah dilakukan sebelumnya");
        }
    }

    private void savePaymentToFile() {
        try (FileWriter writer = new FileWriter("pembayaran_" + instructorName + ".txt", true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("=== Data Pembayaran ===\n");
            writer.write("Waktu       : " + timestamp + "\n");
            writer.write("ID Pembayaran: " + paymentId + "\n");
            writer.write("Siswa       : " + studentName + "\n");
            writer.write("Kursus      : " + courseName + "\n");
            writer.write("Jumlah      : Rp" + amount + "\n");
            writer.write("Status      : " + status + "\n");
            writer.write("---------------\n");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data pembayaran: " + e.getMessage());
        }
    }

    public int getPaymentID(){
        return paymentId;
    }

    public String getStatus() {
        return status;
    }
}
