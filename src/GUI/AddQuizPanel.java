package GUI;

import SistemKursus.Course;
import SistemKursus.CourseFileManager;
import SistemKursus.Question;
import SistemKursus.Quiz;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddQuizPanel extends JPanel {

    private static final Color BACKGROUND_COLOR   = new Color(0xFFF3E0); 
    private static final Color BUTTON_COLOR       = new Color(0xFB8C00); 
    private static final Color TITLE_COLOR        = new Color(0xE65100); 
    private static final Color BUTTON_TEXT_COLOR  = Color.WHITE;

    private final MainGUI mainGUI;
    private final Course selectedCourse;
    private final List<Question> questions = new ArrayList<>();

    public AddQuizPanel(MainGUI mainGUI, Course selectedCourse) {
        this.mainGUI = mainGUI;
        this.selectedCourse = selectedCourse;
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Tambah Kuis - " + selectedCourse.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(new EmptyBorder(10, 40, 10, 40));

        JTextField questionField = createTextField();

        JTextField option1Field = createTextField();
        JTextField option2Field = createTextField();
        JTextField option3Field = createTextField();
        JTextField option4Field = createTextField();

        JComboBox<String> correctAnswerComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        correctAnswerComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        correctAnswerComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JButton addQuestionButton = createButton("Tambah Soal");
        addQuestionButton.addActionListener(e -> {
            String questionText = questionField.getText().trim();
            if (questionText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pertanyaan tidak boleh kosong.");
                return;
            }
            List<String> options = new ArrayList<>();
            options.add(option1Field.getText());
            options.add(option2Field.getText());
            options.add(option3Field.getText());
            options.add(option4Field.getText());
            int correctAnswer = correctAnswerComboBox.getSelectedIndex();
            questions.add(new Question(questionText, options, correctAnswer));
            JOptionPane.showMessageDialog(this, "Soal berhasil ditambahkan!");

            questionField.setText("");
            option1Field.setText("");
            option2Field.setText("");
            option3Field.setText("");
            option4Field.setText("");
            correctAnswerComboBox.setSelectedIndex(0);
        });

        formPanel.add(createLabel("Pertanyaan:"));
        formPanel.add(questionField);
        formPanel.add(createLabel("Pilihan 1:"));
        formPanel.add(option1Field);
        formPanel.add(createLabel("Pilihan 2:"));
        formPanel.add(option2Field);
        formPanel.add(createLabel("Pilihan 3:"));
        formPanel.add(option3Field);
        formPanel.add(createLabel("Pilihan 4:"));
        formPanel.add(option4Field);
        formPanel.add(createLabel("Jawaban Benar:"));
        formPanel.add(correctAnswerComboBox);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(addQuestionButton);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        JButton saveQuizButton = createButton("Simpan Kuis");
        saveQuizButton.setPreferredSize(new Dimension(0, 50));
        saveQuizButton.addActionListener(e -> {
            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada soal untuk disimpan.");
                return;
            }
            int quizId = 6000 + selectedCourse.getQuizzes().size();
            Quiz newQuiz = new Quiz(quizId, questions);
            selectedCourse.addQuiz(newQuiz);
            CourseFileManager.simpanCourseKeFile(selectedCourse, "courses/" + selectedCourse.getName() + ".dat");
            JOptionPane.showMessageDialog(this, "Kuis berhasil ditambahkan ke course.");
            mainGUI.showInstructorDashboard();
        });
        add(saveQuizButton, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(TITLE_COLOR);
        label.setAlignmentX(LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(6, 0, 4, 0));
        return label;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("SansSerif", Font.PLAIN, 18));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return tf;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}