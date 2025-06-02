package SistemKursus;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public boolean isCorrect(int userAnswerIndex) {
        return (userAnswerIndex - 1) == correctAnswerIndex;
    }

    public void displayQuestion() {
        System.out.println(questionText);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }
}
