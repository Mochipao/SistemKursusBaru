package GUI;

import SistemKursus.*;
import java.awt.*;
import javax.swing.*;

public class PaymentPanel extends JPanel {
    private MainGUI mainGUI;
    private Course course;
    private Student student;
    private Payment payment;

    private final Color backgroundColor = new Color(0xFFF3E0);
    private final Color buttonColor = new Color(0xFB8C00);
    private final Color titleColor = new Color(0xE65100);
    private final Color buttonTextColor = Color.WHITE;

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
        setBackground(backgroundColor);

        JLabel title = new JLabel("💳 Pembayaran Kursus", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(titleColor);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel paymentInfoPanel = new JPanel();
        paymentInfoPanel.setLayout(new BoxLayout(paymentInfoPanel, BoxLayout.Y_AXIS));
        paymentInfoPanel.setBackground(backgroundColor);
        paymentInfoPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JLabel courseNameLabel = new JLabel("Nama Kursus: " + course.getName());
        JLabel coursePriceLabel = new JLabel("Harga: Rp" + (int) course.getPrice());
        JLabel instructorLabel = new JLabel("Instruktur: " + course.getInstructorName());

        Font infoFont = new Font("SansSerif", Font.PLAIN, 16);
        courseNameLabel.setFont(infoFont);
        coursePriceLabel.setFont(infoFont);
        instructorLabel.setFont(infoFont);

        courseNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        coursePriceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        paymentInfoPanel.add(courseNameLabel);
        paymentInfoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        paymentInfoPanel.add(coursePriceLabel);
        paymentInfoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        paymentInfoPanel.add(instructorLabel);

        add(paymentInfoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);

        JButton confirmPaymentButton = new JButton("Konfirmasi Pembayaran");
        JButton backButton = new JButton("Kembali");

        styleButton(confirmPaymentButton);
        styleButton(backButton);

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

    private void styleButton(JButton button) {
        button.setBackground(buttonColor);
        button.setForeground(buttonTextColor);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
