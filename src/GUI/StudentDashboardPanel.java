package GUI;

import SistemKursus.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class StudentDashboardPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(0xFFF3E0);
    private static final Color BUTTON_COLOR      = new Color(0xFB8C00);
    private static final Color TITLE_COLOR       = new Color(0xE65100);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color CARD_BORDER_COLOR = new Color(0xFFB74D);

    private final MainGUI mainGUI;
    private final JPanel courseListPanel;

    public StudentDashboardPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel("📚 Course Marketplace", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(TITLE_COLOR);
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        topContainer.add(title, BorderLayout.CENTER);

        JButton logoutButton = createButton("Logout");
        logoutButton.addActionListener(e -> {
            mainGUI.setLoggedInInstructor(null);
            mainGUI.showPanel("Opening");
        });

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topBar.setOpaque(false);
        topBar.add(logoutButton);
        topContainer.add(topBar, BorderLayout.EAST);

        add(topContainer, BorderLayout.NORTH);

        courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        courseListPanel.setBackground(BACKGROUND_COLOR);

        JScrollPane scrollPane = new JScrollPane(courseListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        populateCourses();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void populateCourses() {
        courseListPanel.removeAll();

        String folderPath = "courses";
        List<Course> courseList = CourseFileManager.loadAllCourses(folderPath);

        if (courseList.isEmpty()) {
            JLabel emptyLabel = new JLabel("Belum ada kursus tersedia.");
            emptyLabel.setForeground(TITLE_COLOR);
            courseListPanel.add(emptyLabel);
            return;
        }

        for (Course course : courseList) {
            JPanel card = new JPanel(new BorderLayout(10, 10));
            card.setBackground(Color.WHITE);
            card.setBorder(new CompoundBorder(new EmptyBorder(10, 15, 10, 15),
                                               new LineBorder(CARD_BORDER_COLOR, 2, true)));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

            JLabel nameLabel = new JLabel(course.getName());
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            nameLabel.setForeground(TITLE_COLOR);

            JLabel detailLabel = new JLabel(course.getLessons().size() + " Lessons • " +
                                             course.getQuizzes().size() + " Quizzes");
            detailLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.add(nameLabel);
            leftPanel.add(detailLabel);

            JLabel priceLabel = new JLabel("Rp " + (int) course.getPrice());
            priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

            JButton enrollButton = createButton("Enroll");
            enrollButton.addActionListener(e -> showPaymentPanel(course));

            card.add(leftPanel, BorderLayout.CENTER);
            card.add(priceLabel, BorderLayout.EAST);
            card.add(enrollButton, BorderLayout.SOUTH);

            courseListPanel.add(card);
            courseListPanel.add(Box.createVerticalStrut(15)); 
        }

        revalidate();
        repaint();
    }

    private void showPaymentPanel(Course course) {
        Student student = mainGUI.getCurrentStudent();
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Login dibutuhkan untuk melanjutkan.");
            return;
        }
        mainGUI.showPaymentPanel(course);
    }
}
