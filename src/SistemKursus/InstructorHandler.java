package SistemKursus;

import java.util.*;

public class InstructorHandler implements RoleHandler {
    private Scanner scanner;
    private List<Course> allCourses;
    private Quiz quiz1;

    public InstructorHandler(Scanner scanner, List<Course> allCourses) {
        this.scanner = scanner;
        this.allCourses = allCourses;
    }

    public void showMenu() {
        boolean kembali = false;
        
        while (!kembali){
            System.out.println("\n=== Menu Instruktur ===");
            System.out.println("1. Tambah Course");
            System.out.println("2. Tambah Soal Kuis");
            System.out.println("3. Tambah Pelajaran ke Course");
            System.out.println("4. Lihat Pembayaran");
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

        int courseId = 1000 + allCourses.size();
        Course newCourse = new Course(courseId, courseName, courseDesc, courseCategory, coursePrice, instructorName);
        allCourses.add(newCourse);
        System.out.println("Course berhasil ditambahkan: " + newCourse.getName());
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
            System.out.println("Tidak ada soal yang ditambahkan.");
        }
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
                System.out.println("Pilihan course tidak valid.");
                return;
            }

            Course selectedCourse = allCourses.get(courseIndex);

            System.out.print("Masukkan judul pelajaran: ");
            String lessonTitle = scanner.nextLine();
            if (lessonTitle.trim().isEmpty()) {
                throw new IllegalArgumentException("Judul pelajaran tidak boleh kosong.");
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


    public Quiz getQuiz() {
        return quiz1;
    }
}

