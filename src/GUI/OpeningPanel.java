package GUI;

import java.awt.*;
import javax.swing.*;

public class OpeningPanel extends JPanel {
    public OpeningPanel(MainGUI mainGUI) {
        Color backgroundColor = new Color(0xFFF3E0);
        Color buttonColor = new Color(0xFB8C00);
        Color buttonText = Color.WHITE;

        setBackground(backgroundColor);
        setLayout(new GridBagLayout()); 

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("Selamat Datang di Course9!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40)); 
        title.setForeground(new Color(0xE65100));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); 
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JButton loginButton = createStyledButton("Login", buttonColor, buttonText);
        JButton registerButton = createStyledButton("Register", buttonColor, buttonText);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        contentPanel.add(title);
        contentPanel.add(buttonPanel);

        add(contentPanel);
        
        loginButton.addActionListener(e -> mainGUI.showPanel("Login"));
        registerButton.addActionListener(e -> mainGUI.showPanel("Register"));
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }
}
