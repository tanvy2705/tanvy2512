package btkethop;
import java.io.FileWriter;
import java.io.IOException;

public class GhiFileBangNhieuThread {

    static class ThreadGhi extends Thread {
        private String noiDung;
        private String tenFile;

        public ThreadGhi(String noiDung, String tenFile) {
            this.noiDung = noiDung;
            this.tenFile = tenFile;
        }

        @Override
        public void run() {
            try {
                // Mo file voi tham so true cho phep ghi tiep vao cuoi file
                FileWriter fw = new FileWriter(tenFile, true);

                // Ghi noi dung vao file
                for (int i = 1; i <= 5; i++) {
                    fw.write(Thread.currentThread().getName() + ": " +
                            noiDung + " - Lan " + i + "\n");
                    // Tam dung
                    Thread.sleep(200);
                }

                // Dong file
                fw.close();

                System.out.println(Thread.currentThread().getName() + " da ghi xong!");
            } catch (IOException | InterruptedException e) {
                System.out.println("Loi khi ghi file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String tenFile = "output.txt";

        // Tao hai thread ghi du lieu khac nhau
        ThreadGhi thread1 = new ThreadGhi("Du lieu tu Thread 1", tenFile);
        ThreadGhi thread2 = new ThreadGhi("Du lieu tu Thread 2", tenFile);

        thread1.setName("Thread-1");
        thread2.setName("Thread-2");

        // Khoi dong thread
        thread1.start();
        thread2.start();
    }
}