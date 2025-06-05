package SistemKursus;

import java.io.*;

public class CourseReader {
    public static void main(String[] args) {
        String folderPath = "courses"; 

        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".dat"));

        if (files == null || files.length == 0) {
            System.out.println("Tidak ada file .dat ditemukan.");
            return;
        }

        for (File file : files) {
            System.out.println("=== Membaca: " + file.getName() + " ===");
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Course course = (Course) ois.readObject();
                System.out.println("Nama: " + course.getName());
                System.out.println("Kategori: " + course.getCategory());
                System.out.println("Instruktur: " + course.getInstructorName());
                System.out.println("Harga: Rp " + course.getPrice());
                System.out.println("Jumlah Lesson: " + course.getLessons().size());
                System.out.println("Jumlah Quiz: " + course.getQuizzes().size());
                System.out.println();
            } catch (Exception e) {
                System.out.println("Gagal membaca: " + e.getMessage());
            }
        }
    }
}

