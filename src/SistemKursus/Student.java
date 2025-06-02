package SistemKursus;

public class Student extends User {

    public Student(int id, String name, String email, String password, boolean alreadyHashed) {
        super(id, name, email, password, alreadyHashed);
    }

    @Override
    public String getName() {
        return "Siswa: " + super.getName();
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public void viewCourse() {
        System.out.println(getName() + " sedang melihat daftar kursus.");
    }

    public void takeQuiz() {
        System.out.println(getName() + " sedang mengikuti kuis.");
    }
}
