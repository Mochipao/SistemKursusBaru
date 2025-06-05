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

    private final Color backgroundColor = new Color(0xFFF3E0);
    private final Color buttonColor = new Color(0xFB8C00);
    private final Color titleColor = new Color(0xE65100);
    private final Color buttonTextColor = Color.WHITE;

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
        setBackground(backgroundColor);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        questionLabel.setForeground(titleColor);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBackground(backgroundColor);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            optionButtons[i].setOpaque(false);
            optionButtons[i].setFocusPainted(false);
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        add(optionsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(backgroundColor);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);

        submitButton = new JButton("Submit");
        nextButton = new JButton("Next");

        styleButton(submitButton);
        styleButton(nextButton);

        buttonPanel.add(submitButton);
        buttonPanel.add(nextButton);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        feedbackLabel.setForeground(titleColor);
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        submitButton.addActionListener(e -> checkAnswer());
        nextButton.addActionListener(e -> nextQuestion());

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(feedbackLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        loadQuestion();
    }

    private void styleButton(JButton button) {
        button.setBackground(buttonColor);
        button.setForeground(buttonTextColor);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
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
        setBackground(backgroundColor);

        JLabel finalLabel = new JLabel("🎉 Kuis Selesai! Skor kamu: " + score + "/" + questions.size(), SwingConstants.CENTER);
        finalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        finalLabel.setForeground(titleColor);
        finalLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(finalLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Kembali ke Dashboard");
        styleButton(backButton);
        backButton.addActionListener(e -> mainGUI.showStudentDashboard());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}

