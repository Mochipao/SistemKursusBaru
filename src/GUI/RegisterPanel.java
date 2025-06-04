package GUI;

import SistemKursus.*;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class RegisterPanel extends JPanel {
    public RegisterPanel(MainGUI mainGUI) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Register", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));

        JLabel nameLabel = new JLabel("Nama:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel roleLabel = new JLabel("Daftar sebagai:");
        String[] roles = {"Student", "Instructor"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(roleLabel);
        formPanel.add(roleCombo);

        JButton registerButton = new JButton("Daftar");
        JButton backButton = new JButton("Kembali");

        JPanel buttonPanel = new JPanel();
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
}
