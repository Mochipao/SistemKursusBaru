package GUI;

import SistemKursus.Course;
import SistemKursus.Instructor;
import SistemKursus.Student;
import java.awt.*;
import javax.swing.*;

public class MainGUI extends JFrame {
    private Student currentStudent;
    private CardLayout cardLayout;
    private JPanel mainPanel; 
    private StudentDashboardPanel studentDashboardPanel;
    private InstructorDashboardPanel instructorDashboardPanel;
    private QuizPanel quizPanel;
    private Instructor currentInstructor;

    public MainGUI() {
        setTitle("Course9 - Sistem Kursus Online");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        OpeningPanel openingPanel = new OpeningPanel(this);
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);
        studentDashboardPanel = new StudentDashboardPanel(this);
        instructorDashboardPanel = new InstructorDashboardPanel(this);

        mainPanel.add(openingPanel, "Opening");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(studentDashboardPanel, "StudentDashboard");
        mainPanel.add(instructorDashboardPanel, "InstructorDashboard");

        add(mainPanel);
        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void showStudentDashboard() {
        cardLayout.show(mainPanel, "StudentDashboard");
    }

    public void showInstructorDashboard() {
        instructorDashboardPanel.populateCourses();
        cardLayout.show(mainPanel, "InstructorDashboard");
    }

    public void showLessonPanel(Course course) {
        LessonPanel lessonPanel = new LessonPanel(this, course);
        mainPanel.add(lessonPanel, "LessonPanel"); 
        cardLayout.show(mainPanel, "LessonPanel"); 
    }

    public void setCurrentStudent(Student student) {
        this.currentStudent = student;  
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void showPaymentPanel(Course course) {
        Student student = getCurrentStudent();
        String panelName = "PaymentPanel_" + course.getCourseId();

        PaymentPanel paymentPanel = new PaymentPanel(this, course, student);
        paymentPanel.setName(panelName);  
        mainPanel.add(paymentPanel, panelName);
        showPanel(panelName); 
    }

    public void showQuizPanel(Course course) {
        if (quizPanel != null) {
            mainPanel.remove(quizPanel);
        }

        quizPanel = new QuizPanel(this, course);
        mainPanel.add(quizPanel, "QuizPanel");
        cardLayout.show(mainPanel, "QuizPanel");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showAddQuizPanel(Course selectedCourse) {
        AddQuizPanel addQuizPanel = new AddQuizPanel(this, selectedCourse);
        mainPanel.add(addQuizPanel, "AddQuizPanel");
        cardLayout.show(mainPanel, "AddQuizPanel");
    }

    public void showAddLessonPanel(Course selectedCourse) {
        AddLessonPanel addLessonPanel = new AddLessonPanel(this, selectedCourse);
        mainPanel.add(addLessonPanel, "AddLessonPanel");
        cardLayout.show(mainPanel, "AddLessonPanel");
    }

    public void setLoggedInInstructor(Instructor instructor) {
        this.currentInstructor = instructor;
    }

    public Instructor getLoggedInInstructor() {
        return this.currentInstructor;
    }

    public InstructorDashboardPanel getInstructorDashboardPanel() {
        return instructorDashboardPanel; 
    }

    public void logout() {
        this.currentStudent = null;
        this.currentInstructor = null;
        showPanel("Opening");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
