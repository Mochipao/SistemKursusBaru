package GUI;

import SistemKursus.Course;
import SistemKursus.Lesson;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class LessonPanel extends JPanel {
    private final MainGUI mainGUI;
    private final Course  course;

    private static final Color BACKGROUND_COLOR = new Color(0xFFF3E0);
    private static final Color BUTTON_COLOR      = new Color(0xFB8C00);
    private static final Color TITLE_COLOR       = new Color(0xE65100);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color CARD_BORDER_COLOR = new Color(0xFFB74D);

    public LessonPanel(MainGUI mainGUI, Course course) {
        this.mainGUI = mainGUI;
        this.course   = course;

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("📘 Pelajaran dari Kursus: " + course.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(BACKGROUND_COLOR);
        listContainer.setBorder(new EmptyBorder(10, 20, 10, 20));

        List<Lesson> lessons = course.getLessons();
        if (lessons.isEmpty()) {
            JLabel emptyLabel = new JLabel("Belum ada pelajaran dalam kursus ini.");
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            listContainer.add(emptyLabel);
        } else {
            for (Lesson lesson : lessons) {
                JPanel card = createLessonCard(lesson);
                listContainer.add(card);
                listContainer.add(Box.createVerticalStrut(15)); // Spasi antar kartu
            }
        }

        if (!course.getQuizzes().isEmpty()) {
            JButton quizButton = createButton("📝 Mulai Kuis");
            quizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            quizButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            quizButton.addActionListener(e -> mainGUI.showQuizPanel(course));

            listContainer.add(Box.createVerticalStrut(10));
            listContainer.add(quizButton);
        }

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createButton("⬅ Kembali ke Dashboard");
        backButton.addActionListener(e -> mainGUI.showStudentDashboard());
        backButton.setPreferredSize(new Dimension(250, 40));

        JPanel south = new JPanel();
        south.setBackground(BACKGROUND_COLOR);
        south.add(backButton);
        add(south, BorderLayout.SOUTH);
    }

    private JPanel createLessonCard(Lesson lesson) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(CARD_BORDER_COLOR, 2, true),
                                          new EmptyBorder(10, 15, 10, 15)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel lessonTitle = new JLabel("📖 " + lesson.getTitle());
        lessonTitle.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JButton viewButton = createButton("Lihat Konten");
        viewButton.addActionListener(e -> showLessonContent(lesson));

        card.add(lessonTitle, BorderLayout.CENTER);
        card.add(viewButton, BorderLayout.EAST);

        return card;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void showLessonContent(Lesson lesson) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Konten Pelajaran", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(255, 248, 240));

        JLabel titleLabel = new JLabel("📖 " + lesson.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(255, 140, 0));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(titleLabel, BorderLayout.NORTH);

        StringBuilder sb = new StringBuilder();
        List<String> contents = lesson.getContents();
        if (contents.isEmpty()) {
            sb.append("Belum ada konten dalam pelajaran ini.");
        } else {
            for (int i = 0; i < contents.size(); i++) {
                sb.append((i + 1)).append(". ").append(contents.get(i)).append("\n\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("✔ Tutup");
        closeButton.setFocusPainted(false);
        closeButton.setBackground(new Color(255, 153, 51));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 40));
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 248, 240));
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
