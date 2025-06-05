package GUI;

import SistemKursus.*;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class LoginPanel extends JPanel {
    public LoginPanel(MainGUI mainGUI) {
        Color backgroundColor = new Color(0xFFF3E0);
        Color buttonColor = new Color(0xFB8C00);
        Color buttonTextColor = Color.WHITE;
        Color titleColor = new Color(0xE65100);

        setBackground(backgroundColor);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(titleColor);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JPasswordField passwordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        JButton loginButton = createStyledButton("Login", buttonColor, buttonTextColor);
        JButton backButton = createStyledButton("Kembali", buttonColor, buttonTextColor);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);
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

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }
}
