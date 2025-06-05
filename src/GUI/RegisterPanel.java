package GUI;

import SistemKursus.*;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class RegisterPanel extends JPanel {
    public RegisterPanel(MainGUI mainGUI) {
        Color backgroundColor = new Color(0xFFF3E0);
        Color buttonColor = new Color(0xFB8C00);
        Color titleColor = new Color(0xE65100);
        Color buttonTextColor = Color.WHITE;

        setLayout(new BorderLayout());
        setBackground(backgroundColor);

        JLabel title = new JLabel("Register", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(titleColor);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Nama:");
        JTextField nameField = new JTextField(20);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel roleLabel = new JLabel("Daftar sebagai:");
        String[] roles = {"Student", "Instructor"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roleCombo, gbc);

        JButton registerButton = createStyledButton("Daftar", buttonColor, buttonTextColor);
        JButton backButton = createStyledButton("Kembali", buttonColor, buttonTextColor);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleCombo.getSelectedItem();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, User> userMap = UserFileManager.loadUsers();

            if (userMap.containsKey(email)) {
                JOptionPane.showMessageDialog(this, "Email sudah terdaftar. Silakan coba login.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User newUser;
            if (role.equals("Student")) {
                newUser = new Student(userMap.size() + 1, name, email, password, false);
            } else if (role.equals("Instructor")) {
                newUser = new Instructor(userMap.size() + 1, name, email, password, false);
            } else {
                JOptionPane.showMessageDialog(this, "Peran tidak valid", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            userMap.put(email, newUser);
            UserFileManager.saveUser(newUser);

            JOptionPane.showMessageDialog(this, "Berhasil mendaftar sebagai " + role + "!");
            mainGUI.showPanel("Login");
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
