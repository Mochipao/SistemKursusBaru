package SistemKursus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;
    private int lessonId;
    private String title;
    private List<String> contents;  

    public Lesson(int lessonId, String title) {
        this.lessonId = lessonId;
        this.title = title;
        this.contents = new ArrayList<>();
    }

    public void addContent(String content) {
        try {
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("Konten tidak boleh kosong");
            }
            contents.add(content);
            System.out.println(" Konten berhasil ditambahkan");
        } catch (Exception e) {
            System.out.println(" Gagal menambahkan konten: " + e.getMessage());
        }
    }

    public void display() {
        try {
            System.out.println("\n=== Pelajaran: " + title + " (ID: " + lessonId + ") ===");
            if (contents.isEmpty()) {
                System.out.println("Belum ada konten dalam pelajaran ini");
                return;
            }
            for (int i = 0; i < contents.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + contents.get(i));
            }
        } catch (Exception e) {
            System.out.println(" Gagal menampilkan pelajaran: " + e.getMessage());
        }
    }

    public int getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContents() {
        return contents;
    }
}
