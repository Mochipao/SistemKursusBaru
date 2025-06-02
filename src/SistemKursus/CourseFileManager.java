package SistemKursus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseFileManager {

    public static void simpanCourseKeFile(Course course, String namaFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(namaFile))) {
            oos.writeObject(course);
            System.out.println("Course berhasil disimpan ke file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan course: " + e.getMessage());
        }
    }

    public static Course bacaCourseDariFile(String namaFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(namaFile))) {
            Course course = (Course) ois.readObject();
            System.out.println("Course berhasil dibaca dari file: " + namaFile);
            return course;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Gagal membaca course: " + e.getMessage());
            return null;
        }
    }

    public static List<Course> loadAllCourses(String folderPath) {
        List<Course> courses = new ArrayList<>();
        File folder = new File(folderPath);
        if (!folder.exists()) return courses;

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".dat"));
        if (files == null) return courses;

        for (File file : files) {
            Course c = bacaCourseDariFile(file.getPath());
            if (c != null) courses.add(c);
        }

        return courses;
    }
}
