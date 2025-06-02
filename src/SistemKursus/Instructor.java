package SistemKursus;

public class Instructor extends User {

    public Instructor(int id, String name, String email, String password, boolean alreadyHashed) {
        super(id, name, email, password, alreadyHashed);
    }

    @Override
    public String getName() {
        return "Instructor: " + super.getName();
    }

    @Override
    public String getRole() {
        return "Instructor";
    }

    public void createCourse() {
        System.out.println(getName() + " sedang membuat kursus.");
    }

    public void gradeQuiz() {
        System.out.println(getName() + " sedang menilai kuis.");
    }
}
