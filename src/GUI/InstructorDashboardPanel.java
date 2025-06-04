package GUI;

import java.awt.*;
import javax.swing.*;

public class InstructorDashboardPanel extends JPanel {
    private MainGUI mainGUI;
    private JPanel courseListPanel;

    public InstructorDashboardPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout());

        // Judul Panel
        JLabel title = new JLabel("👨‍🏫 Instructor Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Tombol "+ Add Course" di bagian bawah panel
        JButton addCourseButton = new JButton("+ Add Course");
        addCourseButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addCourseButton.addActionListener(e -> showAddCourseDialog());
        add(addCourseButton, BorderLayout.SOUTH);

        // Panel daftar course dengan layout vertikal
        courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(courseListPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Mengisi daftar course dengan data dummy
        populateCourses();
    }

    private void populateCourses() {
        // Data dummy course yang sudah dibuat oleh instruktur
        String[][] dummyCourses = {
            {"Belajar Java Lanjutan", "10 Lessons • 4 Quizzes • Rp 100.000"},
            {"Design Grafis", "8 Lessons • 2 Quizzes • Rp 80.000"}
        };

        for (String[] course : dummyCourses) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            // Informasi course
            JLabel nameLabel = new JLabel("<html><b>" + course[0] + "</b></html>");
            JLabel detailLabel = new JLabel(course[1]);
            JPanel leftPanel = new JPanel(new GridLayout(2, 1));
            leftPanel.add(nameLabel);
            leftPanel.add(detailLabel);

            // Tombol untuk mengelola course (misalnya tambah lesson/quiz)
            JButton manageButton = new JButton("Manage");
            manageButton.addActionListener(e -> showManageCourseDialog(course[0]));

            panel.add(leftPanel, BorderLayout.CENTER);
            panel.add(manageButton, BorderLayout.EAST);

            courseListPanel.add(panel);
        }
    }

    // Method untuk menampilkan form tambah course
    private void showAddCourseDialog() {
        JDialog dialog = new JDialog((Frame) null, "Add New Course", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Panel form dengan grid layout
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextArea descriptionArea = new JTextArea(3, 20);
        JTextField priceField = new JTextField();
        JTextField categoryField = new JTextField();

        formPanel.add(new JLabel("Course Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);
        // Baris kosong untuk penyesuaian layout
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        dialog.add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Di sini nanti logika untuk menambah course ke sistem
            JOptionPane.showMessageDialog(dialog,
                    "Course \"" + nameField.getText() + "\" added successfully!",
                    "Add Course", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
            // Opsional: segarkan daftar course agar course baru tampil
        });
        dialog.add(submitButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Method untuk mengelola course (contoh: tambah lesson atau quiz)
    private void showManageCourseDialog(String courseName) {
        JDialog dialog = new JDialog((Frame) null, "Manage Course: " + courseName, true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout());
        JButton addLessonButton = new JButton("+ Add Lesson");
        JButton addQuizButton = new JButton("+ Add Quiz");

        addLessonButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(dialog,
                    "Add Lesson for course: " + courseName,
                    "Manage Course", JOptionPane.INFORMATION_MESSAGE));
        addQuizButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(dialog,
                    "Add Quiz for course: " + courseName,
                    "Manage Course", JOptionPane.INFORMATION_MESSAGE));

        panel.add(addLessonButton);
        panel.add(addQuizButton);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
