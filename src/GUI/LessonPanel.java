package GUI;

import SistemKursus.Course;
import SistemKursus.Lesson;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class LessonPanel extends JPanel {
    private MainGUI mainGUI;
    private Course course;

    public LessonPanel(MainGUI mainGUI, Course course) {
        this.mainGUI = mainGUI;
        this.course = course;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📘 Pelajaran dari Kursus: " + course.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // --- Lesson List ---
        List<Lesson> lessons = course.getLessons();
        if (lessons.isEmpty()) {
            centerPanel.add(new JLabel("Belum ada pelajaran dalam kursus ini."));
        } else {
            for (Lesson lesson : lessons) {
                JPanel lessonPanel = new JPanel(new BorderLayout());
                lessonPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                lessonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

                JLabel lessonTitle = new JLabel("📖 " + lesson.getTitle());
                lessonTitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
                lessonPanel.add(lessonTitle, BorderLayout.CENTER);

                JButton viewButton = new JButton("Lihat Konten");
                viewButton.addActionListener(e -> showLessonContent(lesson));
                lessonPanel.add(viewButton, BorderLayout.EAST);

                centerPanel.add(lessonPanel);
            }
        }

        // --- Quiz Button ---
        if (!course.getQuizzes().isEmpty()) {
            JButton quizButton = new JButton("📝 Mulai Kuis");
            quizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            quizButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            quizButton.addActionListener(e -> mainGUI.showQuizPanel(course));

            centerPanel.add(Box.createVerticalStrut(20)); // Spacer
            centerPanel.add(quizButton);
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("⬅ Kembali ke Dashboard");
        backButton.addActionListener(e -> mainGUI.showStudentDashboard());
        add(backButton, BorderLayout.SOUTH);
    }

    private void showLessonContent(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        sb.append("📖 ").append(lesson.getTitle()).append("\n\n");

        List<String> contents = lesson.getContents();
        if (contents.isEmpty()) {
            sb.append("Belum ada konten dalam pelajaran ini.");
        } else {
            for (int i = 0; i < contents.size(); i++) {
                sb.append((i + 1)).append(". ").append(contents.get(i)).append("\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Konten Pelajaran", JOptionPane.INFORMATION_MESSAGE);
    }
}
