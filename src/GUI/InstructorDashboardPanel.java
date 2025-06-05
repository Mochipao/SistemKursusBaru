package GUI;

import SistemKursus.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;

public class InstructorDashboardPanel extends JPanel {
    private MainGUI mainGUI;
    private JPanel courseListPanel;

    private final Color backgroundColor = new Color(0xFFF3E0);
    private final Color buttonColor = new Color(0xFB8C00);
    private final Color titleColor = new Color(0xE65100);
    private final Color buttonTextColor = Color.WHITE;

    public InstructorDashboardPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout());
        setBackground(backgroundColor); 

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(backgroundColor);

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);

        logoutButton.addActionListener(e -> {
            mainGUI.setLoggedInInstructor(null);
            mainGUI.showPanel("Opening");
        });

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setBackground(backgroundColor);
        topBar.add(logoutButton);

        JLabel title = new JLabel("👨‍🏫 Instructor Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(titleColor);
        topContainer.add(title, BorderLayout.CENTER);
        topContainer.add(topBar, BorderLayout.EAST);
        add(topContainer, BorderLayout.NORTH);

        JButton addCourseButton = new JButton("+ Add Course");
        styleButton(addCourseButton);
        addCourseButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addCourseButton.addActionListener(e -> showAddCourseDialog());
        add(addCourseButton, BorderLayout.SOUTH);

        courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        courseListPanel.setBackground(backgroundColor);

        JScrollPane scrollPane = new JScrollPane(courseListPanel);
        scrollPane.getViewport().setBackground(backgroundColor);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void populateCourses() {
        courseListPanel.removeAll();        
        Instructor instructor = mainGUI.getLoggedInInstructor();
        if (instructor == null) return;

        String folderPath = "courses";
        List<Course> allCourses = CourseFileManager.loadAllCourses(folderPath);

        for (Course course : allCourses) {
            if (course.getInstructorEmail().trim().equalsIgnoreCase(instructor.getEmail().trim())) {

                JPanel cardPanel = new JPanel();
                cardPanel.setLayout(new BorderLayout());
                cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
                cardPanel.setBackground(Color.WHITE);
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createLineBorder(new Color(0xE0E0E0), 1)
                ));

                JLabel nameLabel = new JLabel(course.getName());
                nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
                nameLabel.setForeground(titleColor);

                JLabel detailLabel = new JLabel(
                    course.getLessons().size() + " Lessons • " +
                    course.getQuizzes().size() + " Quizzes • Rp " + course.getPrice()
                );
                detailLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                detailLabel.setForeground(Color.DARK_GRAY);

                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                infoPanel.setBackground(Color.WHITE);
                infoPanel.add(nameLabel);
                infoPanel.add(Box.createVerticalStrut(5));
                infoPanel.add(detailLabel);

                JButton detailButton = new JButton("Detail");
                JButton manageButton = new JButton("Manage");
                styleButton(detailButton);
                styleButton(manageButton);

                detailButton.setPreferredSize(new Dimension(90, 28));
                manageButton.setPreferredSize(new Dimension(90, 28));

                detailButton.addActionListener(e -> showCourseDialog(course.getName()));
                manageButton.addActionListener(e -> showManageCourseDialog(course.getName()));

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
                buttonPanel.setBackground(Color.WHITE);
                buttonPanel.add(detailButton);
                buttonPanel.add(manageButton);

                cardPanel.add(infoPanel, BorderLayout.CENTER);
                cardPanel.add(buttonPanel, BorderLayout.EAST);

                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setBackground(backgroundColor);
                wrapper.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                wrapper.add(cardPanel, BorderLayout.CENTER);

                courseListPanel.add(wrapper);
            }
        }

        courseListPanel.revalidate();
        courseListPanel.repaint();
    }


    private void styleButton(JButton button) {
        button.setBackground(buttonColor);
        button.setForeground(buttonTextColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void showAddCourseDialog() {
        JDialog dialog = new JDialog((Frame) null, "Add New Course", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(backgroundColor);

        JLabel titleLabel = new JLabel("Create New Course");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(titleColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        dialog.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel nameLabel = new JLabel("Course Name:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField nameField = new JTextField(20);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextArea descriptionArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descriptionArea);

        JLabel priceLabel = new JLabel("Price (Rp):");
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField priceField = new JTextField(10);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField categoryField = new JTextField(15);

        formPanel.add(nameLabel, gbc); gbc.gridx = 1;
        formPanel.add(nameField, gbc); gbc.gridx = 0; gbc.gridy++;

        formPanel.add(descLabel, gbc); gbc.gridx = 1;
        formPanel.add(descScroll, gbc); gbc.gridx = 0; gbc.gridy++;

        formPanel.add(priceLabel, gbc); gbc.gridx = 1;
        formPanel.add(priceField, gbc); gbc.gridx = 0; gbc.gridy++;

        formPanel.add(categoryLabel, gbc); gbc.gridx = 1;
        formPanel.add(categoryField, gbc); gbc.gridx = 0; gbc.gridy++;

        dialog.add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Create Course");
        styleButton(submitButton);
        submitButton.setPreferredSize(new Dimension(150, 35));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.add(submitButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

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
                populateCourses();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid price!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error adding course!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

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

    private void showManageCourseDialog(String courseName) {
        JDialog dialog = new JDialog((Frame) null, "Manage Course: " + courseName, true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(backgroundColor);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton addLessonButton = new JButton("+ Add Lesson");
        JButton addQuizButton = new JButton("+ Add Quiz");
        JButton deleteLessonButton = new JButton("- Delete Lesson");
        JButton deleteQuizButton = new JButton("- Delete Quiz");

        styleButton(addLessonButton);
        styleButton(addQuizButton);
        styleButton(deleteLessonButton);
        styleButton(deleteQuizButton);

        addLessonButton.addActionListener(e -> {
            Course selectedCourse = findCourseByName(courseName);
            if (selectedCourse != null) {
                dialog.dispose();
                mainGUI.showAddLessonPanel(selectedCourse);
            } else {
                JOptionPane.showMessageDialog(dialog, "Course tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addQuizButton.addActionListener(e -> {
            Course selectedCourse = findCourseByName(courseName);
            if (selectedCourse != null) {
                dialog.dispose();
                mainGUI.showAddQuizPanel(selectedCourse);
            } else {
                JOptionPane.showMessageDialog(dialog, "Course tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

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
        dialog.getContentPane().setBackground(backgroundColor);

        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setBackground(Color.WHITE);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 13));

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
        scrollPane.getViewport().setBackground(backgroundColor);

        JButton closeButton = new JButton("Tutup");
        styleButton(closeButton);
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(closeButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
