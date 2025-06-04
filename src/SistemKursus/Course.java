package SistemKursus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private int courseId;
    private String title;
    private String description;
    private String category;
    private List<Lesson> lessons;
    private List<Quiz> quizzes;
    private double price;
    private String instructorName;
    private static final int JUMLAH_MAX_KONTEN = 10;

    public Course(int courseId, String title, String desc, String category, double price, String instructorName) {
        this.courseId = courseId;
        this.title = title;
        this.description = desc;
        this.category = category;
        this.price = price;
        this.instructorName = instructorName;
        this.lessons = new ArrayList<>();
        this.quizzes = new ArrayList<>();
    }

    public double getPrice() {
        return price;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getName() {
        return this.title;
    }

    public void addLesson(Lesson lesson) {
        try {
            if (lessons.size() < JUMLAH_MAX_KONTEN) {
                lessons.add(lesson);
                System.out.println(" Pelajaran ditambahkan ke kursus: " + title);
            } else {
                throw new IllegalStateException("Kursus sudah mencapai batas maksimal pelajaran.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat menambahkan pelajaran: " + e.getMessage());
        }
    }

    public void addQuiz(Quiz quiz) {
        try {
            if (quizzes.size() < JUMLAH_MAX_KONTEN) {
                quizzes.add(quiz);
                System.out.println("Kuis ditambahkan ke kursus: " + title);
            } else {
                throw new IllegalStateException("Kursus sudah mencapai batas maksimal kuis.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat menambahkan kuis: " + e.getMessage());
        }
    }

    public String getCategory() {
        return category;
    }

    public int getCourseId(){
        return courseId;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }
}
