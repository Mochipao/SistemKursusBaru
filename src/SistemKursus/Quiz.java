package SistemKursus;

import java.util.List;
import java.util.Scanner;

public class Quiz {
    private int quizId;
    private List<Question> questions;

    public Quiz(int quizId, List<Question> questions) {
        this.quizId = quizId;
        this.questions = questions;
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        System.out.println("=== Mulai Kuis ID: " + quizId + " ===");

        for (int i = 0; i < questions.size(); i++) {
            System.out.println("\nPertanyaan " + (i + 1) + ":");
            Question q = questions.get(i);
            q.displayQuestion();

            int jawaban = -1;
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.print("Jawaban Anda (1-" + q.getOptions().size() + "): ");
                    jawaban = Integer.parseInt(scanner.nextLine());
                    if (jawaban < 1 || jawaban > q.getOptions().size()) {
                        throw new IndexOutOfBoundsException("Pilihan di luar jangkauan");
                    }
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println(" Masukkan angka yang valid!");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(" Pilihan tidak tersedia!");
                }
            }

            if (q.isCorrect(jawaban)) {
                System.out.println("Benar!");
                score++;
            } else {
                System.out.println("Salah!");
            }
        }

        System.out.println("\nKuis selesai! Skor Anda: " + score + "/" + questions.size());
    }

    public int getQuizId() {
        return quizId;
    }
}
