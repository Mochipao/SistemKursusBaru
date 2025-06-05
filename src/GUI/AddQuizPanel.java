package GUI;

import SistemKursus.Course;
import SistemKursus.CourseFileManager;
import SistemKursus.Question;
import SistemKursus.Quiz;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AddQuizPanel extends JPanel {
    private MainGUI mainGUI;
    private Course selectedCourse;
    private List<Question> questions;

    public AddQuizPanel(MainGUI mainGUI, Course selectedCourse) {
        this.mainGUI = mainGUI;
        this.selectedCourse = selectedCourse;
        this.questions = new ArrayList<>();

        setLayout(new BorderLayout());
        
        // Panel Title
        JLabel titleLabel = new JLabel("Tambah Kuis - " + selectedCourse.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Form untuk menambahkan soal
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // TextField untuk pertanyaan
        JTextField questionField = new JTextField();
        questionField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        // TextFields untuk pilihan jawaban
        JTextField option1Field = new JTextField();
        JTextField option2Field = new JTextField();
        JTextField option3Field = new JTextField();
        JTextField option4Field = new JTextField();

        
        // ComboBox untuk jawaban yang benar
        JComboBox<String> correctAnswerComboBox = new JComboBox<>(new String[] {"1", "2", "3", "4"});

        // Tombol untuk menambahkan soal
        JButton addQuestionButton = new JButton("Tambah Soal");
        addQuestionButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addQuestionButton.addActionListener(e -> {
            String questionText = questionField.getText().trim();
            List<String> options = new ArrayList<>();
            options.add(option1Field.getText());
            options.add(option2Field.getText());
            options.add(option3Field.getText());
            options.add(option4Field.getText());
            int correctAnswer = Integer.parseInt((String) correctAnswerComboBox.getSelectedItem()) - 1;
            Question question = new Question(questionText, options, correctAnswer);
            questions.add(question);
            JOptionPane.showMessageDialog(this, "Soal berhasil ditambahkan!");
            questionField.setText("");
            option1Field.setText("");
            option2Field.setText("");
            option3Field.setText("");
            option4Field.setText("");
            correctAnswerComboBox.setSelectedIndex(0);
        });

        // Menambahkan komponen ke form
        formPanel.add(new JLabel("Pertanyaan:"));
        formPanel.add(questionField);
        formPanel.add(new JLabel("Pilihan 1:"));
        formPanel.add(option1Field);
        formPanel.add(new JLabel("Pilihan 2:"));
        formPanel.add(option2Field);
        formPanel.add(new JLabel("Pilihan 3:"));
        formPanel.add(option3Field);
        formPanel.add(new JLabel("Pilihan 4:"));
        formPanel.add(option4Field);
        formPanel.add(new JLabel("Jawaban Benar:"));
        formPanel.add(correctAnswerComboBox);
        formPanel.add(addQuestionButton);

        // Tombol untuk menyimpan kuis
        JButton saveQuizButton = new JButton("Simpan Kuis");
        saveQuizButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        saveQuizButton.addActionListener(e -> {
            if (!questions.isEmpty()) {
                int quizId = 6000 + selectedCourse.getQuizzes().size();
                Quiz newQuiz = new Quiz(quizId, questions);
                selectedCourse.addQuiz(newQuiz);
                CourseFileManager.simpanCourseKeFile(selectedCourse, "courses/" + selectedCourse.getName() + ".dat");
                JOptionPane.showMessageDialog(this, "Kuis berhasil ditambahkan ke course.");
                mainGUI.showInstructorDashboard(); 
            } else {
                JOptionPane.showMessageDialog(this, "Tidak ada soal untuk disimpan.");
                mainGUI.showInstructorDashboard();
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(saveQuizButton, BorderLayout.SOUTH);
    }
}
