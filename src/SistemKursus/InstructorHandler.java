package SistemKursus;

import java.io.File;
import java.util.*;

public class InstructorHandler implements RoleHandler {
    private Scanner scanner;
    private List<Course> allCourses;
    private Quiz quiz1;

    public InstructorHandler(Scanner scanner, List<Course> allCourses) {
        this.scanner = scanner;
        this.allCourses = allCourses;
        new File("courses").mkdirs();
    }

    public void showMenu() {
        boolean kembali = false;
        
        while (!kembali){
            System.out.println("\n=== Menu Instruktur ===");
            System.out.println("1. Tambah Course");
            System.out.println("2. Tambah Soal Kuis");
            System.out.println("3. Tambah Pelajaran ke Course");
            System.out.println("4. Lihat Isi Course");
            System.out.println("5. Hapus Isi Course");
            System.out.println("6. Lihat Pembayaran");
            System.out.println("0. Kembali ke Menu Utama"); 
            System.out.print("Pilih opsi: ");
            int instrChoice = scanner.nextInt();
            scanner.nextLine();

            switch (instrChoice) {
                case 1:
                    tambahCourse();
                    break;
                case 2:
                    tambahSoalKuis();
                    break;
                case 3:
                    tambahLessonKeCourse();
                    break;
                case 4:
                    lihatIsiCourse();
                    break;
                case 5:
                    hapusIsiCourse();
                    break;
                case 6:
                    lihatPembayaran();  
                    break;
                case 0:
                    kembali = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }


    private void tambahCourse() {
        System.out.print("Masukkan nama course baru: ");
        String courseName = scanner.nextLine();
        System.out.print("Masukkan deskripsi: ");
        String courseDesc = scanner.nextLine();
        System.out.print("Masukkan kategori (misal: Informatika, Bisnis, Desain, dll): ");
        String courseCategory = scanner.nextLine();
        System.out.print("Masukkan harga course: ");
        double coursePrice = Double.parseDouble(scanner.nextLine());
        System.out.print("Masukkan nama instruktur: ");
        String instructorName = scanner.nextLine();
        System.out.print("Masukkan email instruktur: ");
        String instructorEmail = scanner.nextLine();

        int courseId = 1000 + allCourses.size();
        Course newCourse = new Course(courseId, courseName, courseDesc, courseCategory, coursePrice, instructorName, instructorEmail); 
        allCourses.add(newCourse);
        System.out.println("Course berhasil ditambahkan: " + newCourse.getName());

        CourseFileManager.simpanCourseKeFile(newCourse, "courses/" + newCourse.getName() + ".dat");
    }

    private void tambahSoalKuis() {
        if (allCourses.isEmpty()) {
            System.out.println("Belum ada course yang tersedia. Tambahkan course terlebih dahulu!!");
            return;
        }

        System.out.println("\nPilih course untuk menambahkan kuis:");
        for (int i = 0; i < allCourses.size(); i++) {
            System.out.println((i + 1) + ". " + allCourses.get(i).getName());
        }

        System.out.print("Masukkan nomor course: ");
        int courseIndex;
        try {
            courseIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (courseIndex < 0 || courseIndex >= allCourses.size()) {
                System.out.println("Pilihan course tidak valid");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka");
            return;
        }

        Course selectedCourse = allCourses.get(courseIndex);

        List<Question> dynamicQuestions = new ArrayList<>();
        boolean tambahSoal = true;

        while (tambahSoal) {
            try {
                System.out.print("\nTulis pertanyaan: ");
                String qText = scanner.nextLine();

                List<String> options = new ArrayList<>();
                for (int i = 1; i <= 4; i++) {
                    System.out.print("Pilihan " + i + ": ");
                    options.add(scanner.nextLine());
                }

                int correctIndex;
                while (true) {
                    try {
                        System.out.print("Masukkan nomor jawaban benar (1-4): ");
                        correctIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        if (correctIndex < 0 || correctIndex > 3) {
                            throw new IndexOutOfBoundsException();
                        }
                        break;
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println(" Masukkan angka antara 1 sampai 4");
                    }
                }

                Question newQuestion = new Question(qText, options, correctIndex);
                dynamicQuestions.add(newQuestion);
                System.out.println(" Soal berhasil ditambahkan");

                System.out.print("Tambah soal lagi? (y/n): ");
                String lanjut = scanner.nextLine();
                tambahSoal = lanjut.equalsIgnoreCase("y");

            } catch (Exception e) {
                System.out.println(" Terjadi kesalahan: " + e.getMessage());
            }
        }

        if (!dynamicQuestions.isEmpty()) {
            int quizId = 6000 + selectedCourse.getQuizzes().size();  
            Quiz newQuiz = new Quiz(quizId, dynamicQuestions);
            selectedCourse.addQuiz(newQuiz);
            System.out.println("Kuis berhasil ditambahkan ke course '" + selectedCourse.getName() + "'.");
        } else {
            System.out.println("Tidak ada soal yang ditambahkan");
        }

        CourseFileManager.simpanCourseKeFile(selectedCourse, "courses/" + selectedCourse.getName() + ".dat");
    }

    private void tambahLessonKeCourse() {
        try {
            if (allCourses.isEmpty()) {
                System.out.println("Belum ada course yang tersedia. Tambah course terlebih dahulu!");
                return;
            }

            System.out.println("\nDaftar Course yang Tersedia:");
            for (int i = 0; i < allCourses.size(); i++) {
                Course course = allCourses.get(i);
                System.out.println((i + 1) + ". " + course.getName());
            }

            System.out.print("Pilih course (nomor): ");
            int courseIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (courseIndex < 0 || courseIndex >= allCourses.size()) {
                System.out.println("Pilihan course tidak valid");
                return;
            }

            Course selectedCourse = allCourses.get(courseIndex);

            System.out.print("Masukkan judul pelajaran: ");
            String lessonTitle = scanner.nextLine();
            if (lessonTitle.trim().isEmpty()) {
                throw new IllegalArgumentException("Judul pelajaran tidak boleh kosong");
            }

            int lessonId = 2000 + selectedCourse.getLessons().size();
            Lesson lesson = new Lesson(lessonId, lessonTitle);

            boolean tambahKonten = true;
            while (tambahKonten) {
                System.out.print("Masukkan konten (paragraf/kalimat): ");
                String content = scanner.nextLine();
                lesson.addContent(content);  

                System.out.print("Tambah konten lagi? (y/n): ");
                String lanjut = scanner.nextLine();
                tambahKonten = lanjut.equalsIgnoreCase("y");
            }

            selectedCourse.addLesson(lesson);
            System.out.println("Pelajaran '" + lessonTitle + "' berhasil ditambahkan ke course " + selectedCourse.getName());

            CourseFileManager.simpanCourseKeFile(selectedCourse, "courses/" + selectedCourse.getName() + ".dat");

        } catch (NumberFormatException e) {
            System.out.println("Masukkan angka yang valid untuk pilihan course");
        } catch (IllegalArgumentException e) {
            System.out.println("Input tidak valid: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat menambahkan pelajaran: " + e.getMessage());
        }
    }

    private void lihatPembayaran() {
        System.out.print("Masukkan nama Anda (instruktur): ");
        String instructorName = scanner.nextLine();
        String fileName = "pembayaran_" + instructorName + ".txt";

        try (Scanner fileScanner = new Scanner(new java.io.File(fileName))) {
            System.out.println("\n=== Riwayat Pembayaran ===");
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println(" Gagal membaca file: " + e.getMessage());
        }
    }

    private void lihatIsiCourse() {
        if (allCourses.isEmpty()) {
            System.out.println("Belum ada course yang tersedia");
            return;
        }

        System.out.println("\n=== Daftar Course Anda ===");
        for (int i = 0; i < allCourses.size(); i++) {
            Course c = allCourses.get(i);
            System.out.println((i + 1) + ". " + c.getName() + " - " + c.getCategory());
        }

        System.out.print("Pilih nomor course untuk melihat detailnya: ");
        int pilihan;
        try {
            pilihan = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka");
            return;
        }

        if (pilihan < 1 || pilihan > allCourses.size()) {
            System.out.println("Pilihan tidak valid");
            return;
        }

        Course selected = allCourses.get(pilihan - 1);

        List<Lesson> lessons = selected.getLessons();
        System.out.println("\n=== Pelajaran dalam " + selected.getName() + " ===");
        if (lessons.isEmpty()) {
            System.out.println("Belum ada pelajaran");
        } else {
            for (Lesson lesson : lessons) {
                System.out.println("- " + lesson.getTitle());
            }
        }

        List<Quiz> quizzes = selected.getQuizzes();
        System.out.println("\n=== Kuis dalam " + selected.getName() + " ===");
        if (quizzes.isEmpty()) {
            System.out.println("Belum ada kuis");
        } else {
            for (Quiz quiz : quizzes) {
                System.out.println("- Kuis ID: " + quiz.getQuizId() + ", Jumlah Soal: " + quiz.getQuestions().size());
            }
        }
    }

    private void hapusIsiCourse() {
        if (allCourses.isEmpty()) {
            System.out.println("Belum ada course");
            return;
        }

        System.out.println("\n=== Hapus Isi Course ===");
        for (int i = 0; i < allCourses.size(); i++) {
            System.out.println((i + 1) + ". " + allCourses.get(i).getName());
        }

        System.out.print("Pilih nomor course: ");
        int courseIndex;
        try {
            courseIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (courseIndex < 0 || courseIndex >= allCourses.size()) {
                System.out.println("Pilihan tidak valid");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka");
            return;
        }

        Course selectedCourse = allCourses.get(courseIndex);

        System.out.println("\n1. Hapus Pelajaran");
        System.out.println("2. Hapus Kuis");
        System.out.print("Pilih isi yang ingin dihapus: ");
        String isi = scanner.nextLine();

        switch (isi) {
            case "1":
                List<Lesson> lessons = selectedCourse.getLessons();
                if (lessons.isEmpty()) {
                    System.out.println("Tidak ada pelajaran untuk dihapus.");
                    return;
                }

                for (int i = 0; i < lessons.size(); i++) {
                    System.out.println((i + 1) + ". " + lessons.get(i).getTitle());
                }

                System.out.print("Pilih nomor pelajaran untuk dihapus: ");
                try {
                    int delIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (delIndex >= 0 && delIndex < lessons.size()) {
                        Lesson removed = lessons.remove(delIndex);
                        System.out.println("Pelajaran '" + removed.getTitle() + "' berhasil dihapus.");
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka.");
                }
                break;

            case "2":
                List<Quiz> quizzes = selectedCourse.getQuizzes();
                if (quizzes.isEmpty()) {
                    System.out.println("Tidak ada kuis untuk dihapus.");
                    return;
                }

                for (int i = 0; i < quizzes.size(); i++) {
                    System.out.println((i + 1) + ". Kuis ID: " + quizzes.get(i).getQuizId());
                }

                System.out.print("Pilih nomor kuis untuk dihapus: ");
                try {
                    int delIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (delIndex >= 0 && delIndex < quizzes.size()) {
                        Quiz removed = quizzes.remove(delIndex);
                        System.out.println("Kuis ID " + removed.getQuizId() + " berhasil dihapus.");
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka.");
                }
                break;

            default:
                System.out.println("Pilihan tidak valid.");
                return;
        }

        CourseFileManager.simpanCourseKeFile(selectedCourse, "courses/" + selectedCourse.getName() + ".dat");
    }

    public Quiz getQuiz() {
        return quiz1;
    }
}

