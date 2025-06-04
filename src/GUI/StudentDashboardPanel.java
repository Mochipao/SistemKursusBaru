package GUI;

import SistemKursus.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class StudentDashboardPanel extends JPanel {
    private MainGUI mainGUI;
    private JPanel courseListPanel;

    public StudentDashboardPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout());

        JPanel topContainer = new JPanel(new BorderLayout());

        // Panel logout di kanan
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            mainGUI.setLoggedInInstructor(null);
            mainGUI.showPanel("Opening");
        });
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.add(logoutButton);

        JLabel title = new JLabel("📚 Course Marketplace", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        topContainer.add(title, BorderLayout.CENTER);
        topContainer.add(topBar, BorderLayout.EAST);
        add(topContainer, BorderLayout.NORTH);

        // Panel untuk daftar kursus
        courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(courseListPanel);
        add(scrollPane, BorderLayout.CENTER);

        populateCourses();
    }

    private void populateCourses() {
        // Ganti dengan folder path yang sesuai
        String folderPath = "courses";
        List<Course> courseList = CourseFileManager.loadAllCourses(folderPath);

        if (courseList.isEmpty()) {
            courseListPanel.add(new JLabel("Belum ada kursus tersedia."));
            return;
        }

        for (Course course : courseList) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            JLabel nameLabel = new JLabel("<html><b>" + course.getName() + "</b></html>");
            JLabel detailLabel = new JLabel(course.getLessons().size() + " Lessons • " +
                                            course.getQuizzes().size() + " Quizzes");
            JLabel priceLabel = new JLabel("Rp " + (int) course.getPrice());

            JPanel leftPanel = new JPanel(new GridLayout(2, 1));
            leftPanel.add(nameLabel);
            leftPanel.add(detailLabel);

            JButton enrollButton = new JButton("Enroll");
            enrollButton.addActionListener(e -> showPaymentPanel(course));

            panel.add(leftPanel, BorderLayout.CENTER);
            panel.add(priceLabel, BorderLayout.EAST);
            panel.add(enrollButton, BorderLayout.SOUTH);

            courseListPanel.add(panel);
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
