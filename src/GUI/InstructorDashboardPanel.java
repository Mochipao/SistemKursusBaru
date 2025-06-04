package GUI;

import SistemKursus.Course;
import SistemKursus.CourseFileManager;
import SistemKursus.Instructor;
import SistemKursus.Lesson;
import SistemKursus.Quiz;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;

public class InstructorDashboardPanel extends JPanel {
    private MainGUI mainGUI;
    private JPanel courseListPanel;

    public InstructorDashboardPanel(MainGUI mainGUI) {
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

        // Judul Panel
        JLabel title = new JLabel("👨‍🏫 Instructor Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        topContainer.add(title, BorderLayout.CENTER);
        topContainer.add(topBar, BorderLayout.EAST);
        add(topContainer, BorderLayout.NORTH);

        // Tombol "+ Add Course" di bagian bawah panel
        JButton addCourseButton = new JButton("+ Add Course");
        addCourseButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addCourseButton.addActionListener(e -> showAddCourseDialog());
        add(addCourseButton, BorderLayout.SOUTH);

        // Panel daftar course dengan layout vertikal
        courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(courseListPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void populateCourses() {
        courseListPanel.removeAll();        
        Instructor instructor = mainGUI.getLoggedInInstructor();
        if (instructor == null) return;

        String folderPath = "courses";
        List<Course> allCourses = CourseFileManager.loadAllCourses(folderPath);

        for (Course course : allCourses) {
            if (course.getInstructorEmail().trim().equalsIgnoreCase(instructor.getEmail().trim())){
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                JLabel nameLabel = new JLabel("<html><b>" + course.getName() + "</b></html>");
                JLabel detailLabel = new JLabel(
                    course.getLessons().size() + " Lessons • " +
                    course.getQuizzes().size() + " Quizzes • Rp " + course.getPrice()
                );
                JPanel leftPanel = new JPanel(new GridLayout(2, 1));
                leftPanel.add(nameLabel);
                leftPanel.add(detailLabel);

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

                JButton detailButton = new JButton("Detail");
                detailButton.addActionListener(e -> showCourseDialog(course.getName()));

                JButton manageButton = new JButton("Manage");
                manageButton.addActionListener(e -> showManageCourseDialog(course.getName()));

                buttonPanel.add(detailButton); // tambahkan dulu
                buttonPanel.add(manageButton); // lalu tombol manage

                panel.add(leftPanel, BorderLayout.CENTER);
                panel.add(buttonPanel, BorderLayout.EAST);

                courseListPanel.add(panel);
            }
        }

        courseListPanel.revalidate();
        courseListPanel.repaint();
    }

    private void showAddCourseDialog() {
        JDialog dialog = new JDialog((Frame) null, "Add New Course", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextArea descriptionArea = new JTextArea(3, 20);
        JTextField priceField = new JTextField();
        JTextField categoryField = new JTextField();

        formPanel.add(new JLabel("Course Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);
        formPanel.add(new JLabel("")); 
        formPanel.add(new JLabel("")); 

        dialog.add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String desc = descriptionArea.getText().trim();
                String category = categoryField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());

                if (name.isEmpty() || desc.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Instructor instructor = mainGUI.getLoggedInInstructor();
                if (instructor == null) {
                    JOptionPane.showMessageDialog(dialog, "Instructor not logged in!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                File folder = new File("courses");
                if (!folder.exists()) folder.mkdirs();
                int courseId = 1000 + folder.listFiles().length;

                Course newCourse = new Course(
                    courseId,
                    name,
                    desc,
                    category,
                    price,
                    instructor.getName(),
                    instructor.getEmail()
                );

                CourseFileManager.simpanCourseKeFile(newCourse, "courses/" + newCourse.getName() + ".dat");

                JOptionPane.showMessageDialog(dialog,
                        "Course \"" + newCourse.getName() + "\" added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                dialog.dispose();
                populateCourses(); // Refresh list
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid price!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error adding course!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.add(submitButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private Course findCourseByName(String courseName) {
        List<Course> allCourses = CourseFileManager.loadAllCourses("courses");
        for (Course course : allCourses) {
            if (course.getName().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        return null;
    }

    // Method untuk mengelola course (contoh: tambah lesson atau quiz)
    // Method untuk mengelola course (contoh: tambah lesson/quiz)
    private void showManageCourseDialog(String courseName) {
        JDialog dialog = new JDialog((Frame) null, "Manage Course: " + courseName, true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2 baris, 2 kolom

        JButton addLessonButton = new JButton("+ Add Lesson");
        JButton addQuizButton = new JButton("+ Add Quiz");
        JButton deleteLessonButton = new JButton("- Delete Lesson");
        JButton deleteQuizButton = new JButton("- Delete Quiz");

        // Tambah pelajaran
        addLessonButton.addActionListener(e -> {
            Course selectedCourse = findCourseByName(courseName);
            if (selectedCourse != null) {
                dialog.dispose();
                mainGUI.showAddLessonPanel(selectedCourse);
            } else {
                JOptionPane.showMessageDialog(dialog, "Course tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tambah kuis
        addQuizButton.addActionListener(e -> {
            Course selectedCourse = findCourseByName(courseName);
            if (selectedCourse != null) {
                dialog.dispose();
                mainGUI.showAddQuizPanel(selectedCourse);
            } else {
                JOptionPane.showMessageDialog(dialog, "Course tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Hapus pelajaran
        deleteLessonButton.addActionListener(e -> {
            Course selectedCourse = findCourseByName(courseName);
            if (selectedCourse != null) {
                List<Lesson> lessons = selectedCourse.getLessons();
                if (lessons.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tidak ada pelajaran untuk dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String[] lessonTitles = lessons.stream().map(Lesson::getTitle).toArray(String[]::new);
                String selected = (String) JOptionPane.showInputDialog(dialog, "Pilih pelajaran untuk dihapus:", "Hapus Pelajaran", JOptionPane.PLAIN_MESSAGE, null, lessonTitles, lessonTitles[0]);
                if (selected != null) {
                    lessons.removeIf(lesson -> lesson.getTitle().equals(selected));
                    CourseFileManager.simpanCourseKeFile(selectedCourse, "courses/" + selectedCourse.getName() + ".dat");
                    JOptionPane.showMessageDialog(dialog, "Pelajaran berhasil dihapus.");
                    dialog.dispose(); 
                    mainGUI.showInstructorDashboard(); 
                }
            }
        });

        // Hapus kuis
        deleteQuizButton.addActionListener(e -> {
            Course selectedCourse = findCourseByName(courseName);
            if (selectedCourse != null) {
                List<Quiz> quizzes = selectedCourse.getQuizzes();
                if (quizzes.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tidak ada kuis untuk dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String[] quizIds = quizzes.stream().map(q -> "ID: " + q.getQuizId()).toArray(String[]::new);
                String selected = (String) JOptionPane.showInputDialog(dialog, "Pilih kuis untuk dihapus:", "Hapus Kuis", JOptionPane.PLAIN_MESSAGE, null, quizIds, quizIds[0]);
                if (selected != null) {
                    String idToRemove = selected.replace("ID: ", "").trim();
                    try {
                        int quizIdInt = Integer.parseInt(idToRemove);
                        quizzes.removeIf(q -> q.getQuizId() == quizIdInt);
                        CourseFileManager.simpanCourseKeFile(selectedCourse, "courses/" + selectedCourse.getName() + ".dat");
                        JOptionPane.showMessageDialog(dialog, "Kuis berhasil dihapus.");
                        dialog.dispose(); 
                        mainGUI.showInstructorDashboard(); 
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "ID kuis tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        panel.add(addLessonButton);
        panel.add(addQuizButton);
        panel.add(deleteLessonButton);
        panel.add(deleteQuizButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showCourseDialog(String courseName) {
        Course selectedCourse = findCourseByName(courseName);
        if (selectedCourse == null) {
            JOptionPane.showMessageDialog(this, "Course tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) null, "Detail Course: " + courseName, true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("Kategori: ").append(selectedCourse.getCategory()).append("\n\n");

        List<Lesson> lessons = selectedCourse.getLessons();
        sb.append("=== Pelajaran ===\n");
        if (lessons.isEmpty()) {
            sb.append("Belum ada pelajaran.\n");
        } else {
            for (Lesson lesson : lessons) {
                sb.append("- ").append(lesson.getTitle()).append("\n");
            }
        }

        sb.append("\n=== Kuis ===\n");
        List<Quiz> quizzes = selectedCourse.getQuizzes();
        if (quizzes.isEmpty()) {
            sb.append("Belum ada kuis.\n");
        } else {
            for (Quiz quiz : quizzes) {
                sb.append("- Kuis ID: ").append(quiz.getQuizId())
                .append(", Jumlah Soal: ").append(quiz.getQuestions().size()).append("\n");
            }
        }

        contentArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(contentArea);

        JButton closeButton = new JButton("Tutup");
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(closeButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

}
