package GUI;

import SistemKursus.*;
import javax.swing.*;
import java.awt.*;

public class PaymentPanel extends JPanel {
    private MainGUI mainGUI;
    private Course course;
    private Student student;
    private Payment payment;

    public PaymentPanel(MainGUI mainGUI, Course course, Student student) {
        this.mainGUI = mainGUI;
        this.course = course;
        this.student = student;

        this.payment = new Payment(
            3000 + course.getCourseId(),
            course.getPrice(),
            "Unpaid",
            student.getName(),
            course.getInstructorName(),
            course.getName()
        );

        setLayout(new BorderLayout());

        JLabel title = new JLabel("💳 Pembayaran Kursus", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel paymentInfoPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JLabel courseNameLabel = new JLabel("Nama Kursus: " + course.getName());
        JLabel coursePriceLabel = new JLabel("Harga: Rp" + (int) course.getPrice());
        JLabel instructorLabel = new JLabel("Instruktur: " + course.getInstructorName());

        paymentInfoPanel.add(courseNameLabel);
        paymentInfoPanel.add(coursePriceLabel);
        paymentInfoPanel.add(instructorLabel);

        add(paymentInfoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton confirmPaymentButton = new JButton("Konfirmasi Pembayaran");
        JButton backButton = new JButton("Kembali");

        confirmPaymentButton.addActionListener(e -> {
            String message = "Apakah Anda sudah melakukan pembayaran untuk kursus " + course.getName() + "?";
            int confirm = JOptionPane.showConfirmDialog(this, message, "Konfirmasi Pembayaran", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                payment.processPayment();
                JOptionPane.showMessageDialog(this, "Pembayaran berhasil dilakukan!\nAnda akan diarahkan ke kursus.");
                mainGUI.showLessonPanel(course);  
            } else {
                JOptionPane.showMessageDialog(this, "Silakan lakukan pembayaran terlebih dahulu.");
            }
        });

        backButton.addActionListener(e -> mainGUI.showStudentDashboard());  

        buttonPanel.add(confirmPaymentButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
