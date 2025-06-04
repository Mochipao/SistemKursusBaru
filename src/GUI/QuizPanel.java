package GUI;

import SistemKursus.Course;
import SistemKursus.Question;
import SistemKursus.Quiz;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class QuizPanel extends JPanel {
    private MainGUI mainGUI;  
    private Quiz quiz;
    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JButton submitButton, nextButton;
    private JLabel feedbackLabel;

    public QuizPanel(MainGUI mainGUI, Course course) {
        this.mainGUI = mainGUI;
        List<Quiz> quizList = course.getQuizzes();
        if (quizList == null || quizList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kursus ini belum memiliki kuis.");
            mainGUI.showStudentDashboard(); 
            return;
        }

        this.quiz = quizList.get(0);
        this.questions = quiz.getQuestions();

        setLayout(new BorderLayout());

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        add(optionsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(); 
        submitButton = new JButton("Submit");
        nextButton = new JButton("Next");
        buttonPanel.add(submitButton);
        buttonPanel.add(nextButton);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);

        submitButton.addActionListener(e -> checkAnswer());
        nextButton.addActionListener(e -> nextQuestion());

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(feedbackLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        loadQuestion();
    }

    private void loadQuestion() {
        if (currentIndex >= questions.size()) {
            showFinalScore();
            return;
        }

        Question q = questions.get(currentIndex);
        questionLabel.setText("Q" + (currentIndex + 1) + ": " + q.getQuestionText());

        List<String> options = q.getOptions();
        for (int i = 0; i < optionButtons.length; i++) {
            if (i < options.size()) {
                optionButtons[i].setVisible(true);
                optionButtons[i].setText((char)('A' + i) + ". " + options.get(i));
            } else {
                optionButtons[i].setVisible(false);
            }
        }

        optionsGroup.clearSelection();
        submitButton.setEnabled(true);
        nextButton.setEnabled(false);
        feedbackLabel.setText("");
    }

    private void checkAnswer() {
        int selectedIndex = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedIndex = i;
                break;
            }
        }

        if (selectedIndex == -1) {
            feedbackLabel.setText("⚠️ Pilih salah satu jawaban.");
            return;
        }

        Question currentQuestion = questions.get(currentIndex);
        if (currentQuestion.isCorrect(selectedIndex)) {
            feedbackLabel.setText("✅ Jawaban benar!");
            score++;
        } else {
            int correct = currentQuestion.getCorrectAnswerIndex();
            feedbackLabel.setText("❌ Salah! Jawaban benar: " + (char)('A' + correct));
        }

        submitButton.setEnabled(false);
        nextButton.setEnabled(true);
    }

    private void nextQuestion() {
        currentIndex++;
        loadQuestion();
    }

    private void showFinalScore() {
        removeAll();
        setLayout(new BorderLayout());

        JLabel finalLabel = new JLabel("🎉 Kuis Selesai! Skor kamu: " + score + "/" + questions.size(), SwingConstants.CENTER);
        finalLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(finalLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Kembali ke Dashboard");
        backButton.addActionListener(e -> mainGUI.showStudentDashboard());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}
