package GUI;

import java.awt.*;
import javax.swing.*;

public class OpeningPanel extends JPanel {
    public OpeningPanel(MainGUI mainGUI) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Selamat Datang di Course9!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        loginButton.addActionListener(e -> mainGUI.showPanel("Login"));
        registerButton.addActionListener(e -> mainGUI.showPanel("Register"));

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
