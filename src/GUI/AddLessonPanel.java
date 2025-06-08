package GUI;

import SistemKursus.Course;
import SistemKursus.CourseFileManager;
import SistemKursus.Lesson;
import java.awt.*;
import javax.swing.*;

public class AddLessonPanel extends JPanel {
    private MainGUI mainGUI;
    private Course selectedCourse;
    private JTextField titleField;
    private JTextArea contentArea;
    private DefaultListModel<String> contentListModel;
    private JList<String> contentList;
    private JButton addContentButton, saveLessonButton;

    private final Color backgroundColor = new Color(0xFFF3E0);
    private final Color buttonColor = new Color(0xFB8C00);
    private final Color titleColor = new Color(0xE65100);
    private final Color buttonTextColor = Color.WHITE;

    public AddLessonPanel(MainGUI mainGUI, Course selectedCourse) {
        this.mainGUI = mainGUI;
        this.selectedCourse = selectedCourse;
        setLayout(new BorderLayout());
        setBackground(backgroundColor);

        JLabel titleLabel = new JLabel("Tambah Pelajaran - " + selectedCourse.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(titleColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        JLabel judulLabel = new JLabel("Judul Pelajaran:");
        judulLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        topPanel.add(judulLabel, BorderLayout.NORTH);

        titleField = new JTextField();
        titleField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        topPanel.add(titleField, BorderLayout.CENTER);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        contentArea = new JTextArea(4, 20);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane contentScroll = new JScrollPane(contentArea);

        addContentButton = new JButton("Tambah Konten");
        addContentButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addContentButton.setBackground(buttonColor);
        addContentButton.setForeground(buttonTextColor);
        addContentButton.addActionListener(e -> {
            String konten = contentArea.getText().trim();
            if (!konten.isEmpty()) {
                contentListModel.addElement(konten);
                contentArea.setText("");
            }
        });

        JPanel contentInputPanel = new JPanel(new BorderLayout(10, 10));
        contentInputPanel.setBackground(backgroundColor);
        contentInputPanel.add(contentScroll, BorderLayout.CENTER);
        contentInputPanel.add(addContentButton, BorderLayout.EAST);
        contentInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        contentListModel = new DefaultListModel<>();
        contentList = new JList<>(contentListModel);
        contentList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane listScrollPane = new JScrollPane(contentList);

        JPanel contentListPanel = new JPanel(new BorderLayout());
        contentListPanel.setBackground(backgroundColor);
        JLabel daftarKontenLabel = new JLabel("Daftar Konten:");
        daftarKontenLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        contentListPanel.add(daftarKontenLabel, BorderLayout.NORTH);
        contentListPanel.add(listScrollPane, BorderLayout.CENTER);
        contentListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(backgroundColor);
        centerPanel.add(contentInputPanel, BorderLayout.NORTH);
        centerPanel.add(contentListPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        saveLessonButton = new JButton("Simpan Pelajaran");
        saveLessonButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        saveLessonButton.setBackground(buttonColor);
        saveLessonButton.setForeground(buttonTextColor);
        saveLessonButton.setPreferredSize(new Dimension(200, 40));
        saveLessonButton.addActionListener(e -> simpanLesson());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bottomPanel.add(saveLessonButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void simpanLesson() {
        String judul = titleField.getText().trim();

        if (judul.isEmpty() || contentListModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pastikan judul dan konten terisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int lessonId = 2000 + selectedCourse.getLessons().size();
        Lesson lesson = new Lesson(lessonId, judul);
        for (int i = 0; i < contentListModel.size(); i++) {
            lesson.addContent(contentListModel.get(i));
        }

        selectedCourse.addLesson(lesson);
        CourseFileManager.simpanCourseKeFile(selectedCourse, "courses/" + selectedCourse.getName() + ".dat");
        JOptionPane.showMessageDialog(this, "Pelajaran berhasil disimpan ke course!");
        mainGUI.showInstructorDashboard();

        titleField.setText("");
        contentListModel.clear();
    }
}