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

    public AddLessonPanel(MainGUI mainGUI, Course selectedCourse) {
        this.mainGUI = mainGUI;
        this.selectedCourse = selectedCourse;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        titleField = new JTextField();

        topPanel.add(new JLabel("Judul Pelajaran:"));
        topPanel.add(titleField);

        contentArea = new JTextArea(4, 20);
        contentListModel = new DefaultListModel<>();
        contentList = new JList<>(contentListModel);
        addContentButton = new JButton("Tambah Konten");
        addContentButton.addActionListener(e -> {
            String konten = contentArea.getText().trim();
            if (!konten.isEmpty()) {
                contentListModel.addElement(konten);
                contentArea.setText("");
            }
        });

        JPanel contentInputPanel = new JPanel(new BorderLayout());
        contentInputPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        contentInputPanel.add(addContentButton, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(contentInputPanel, BorderLayout.NORTH);
        centerPanel.add(new JLabel("Daftar Konten:"), BorderLayout.CENTER);
        centerPanel.add(new JScrollPane(contentList), BorderLayout.SOUTH);

        saveLessonButton = new JButton("Simpan Pelajaran");
        saveLessonButton.addActionListener(e -> simpanLesson());

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(saveLessonButton, BorderLayout.SOUTH);
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


