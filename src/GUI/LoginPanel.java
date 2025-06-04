package GUI;

import SistemKursus.*;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class LoginPanel extends JPanel {
    public LoginPanel(MainGUI mainGUI) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Kembali");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            Map<String, User> userMap = UserFileManager.loadUsers();

            User user = userMap.get(email);
            if (user != null && user.login(email, password)) {
                if (user.getRole().equals("Student")) {
                    JOptionPane.showMessageDialog(this, "Login sebagai Student berhasil!");
                    mainGUI.setCurrentStudent((Student) user);  
                    mainGUI.showPanel("StudentDashboard"); 
                } else if (user.getRole().equals("Instructor")) {
                    JOptionPane.showMessageDialog(this, "Login sebagai Instructor berhasil!");
                    mainGUI.setLoggedInInstructor((Instructor) user);
                    mainGUI.getInstructorDashboardPanel().populateCourses();
                    mainGUI.showPanel("InstructorDashboard"); 
                }
            } else {
                JOptionPane.showMessageDialog(this, "Email atau password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainGUI.showPanel("Opening"));

        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
