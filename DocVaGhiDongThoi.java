package btkethop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DocVaGhiDongThoi {
    // Hang doi de chuyen du lieu giua cac thread
    static BlockingQueue<String> hangDoi = new LinkedBlockingQueue<>();

    // Thread doc tu file nguon
    static class ThreadDoc extends Thread {
        private String fileNguon;

        public ThreadDoc(String fileNguon) {
            this.fileNguon = fileNguon;
        }

        @Override
        public void run() {
            try {
                // Tao reader de doc file
                BufferedReader reader = new BufferedReader(new FileReader(fileNguon));
                String dong;

                System.out.println("Thread Doc: Bat dau doc tu file " + fileNguon);

                // Doc tung dong va dua vao hang doi
                while ((dong = reader.readLine()) != null) {
                    hangDoi.put(dong);
                    System.out.println("Thread Doc: Da doc dong: " + dong);
                    Thread.sleep(100);  // Tam dung de mo phong xu ly
                }

                // Danh dau ket thuc file
                hangDoi.put("KET_THUC");
                reader.close();

                System.out.println("Thread Doc: Da doc xong file nguon");
            } catch (Exception e) {
                System.out.println("Loi tai Thread Doc: " + e.getMessage());
            }
        }
    }

    // Thread ghi vao file dich
    static class ThreadGhi extends Thread {
        private String fileDich;

        public ThreadGhi(String fileDich) {
            this.fileDich = fileDich;
        }

        @Override
        public void run() {
            try {
                // Tao writer de ghi file
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileDich));

                System.out.println("Thread Ghi: Bat dau ghi vao file " + fileDich);

                while (true) {
                    // Lay dong tu hang doi
                    String dong = hangDoi.take();

                    // Kiem tra neu la tin hieu ket thuc
                    if (dong.equals("KET_THUC")) {
                        break;
                    }

                    // Ghi dong vao file dich
                    writer.write(dong);
                    writer.newLine();
                    System.out.println("Thread Ghi: Da ghi dong: " + dong);

                    Thread.sleep(200);  // Tam dung de mo phong xu ly
                }

                // Dong file
                writer.close();
                System.out.println("Thread Ghi: Da ghi xong file dich");
            } catch (Exception e) {
                System.out.println("Loi tai Thread Ghi: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Ten file nguon va dich
        String fileNguon = "nguon.txt";
        String fileDich = "dich.txt";

        // Tao va khoi dong thread
        ThreadDoc threadDoc = new ThreadDoc(fileNguon);
        ThreadGhi threadGhi = new ThreadGhi(fileDich);

        threadDoc.start();
        threadGhi.start();

        // Cho cac thread hoan thanh
        try {
            threadDoc.join();
            threadGhi.join();
            System.out.println("Da hoan thanh viec doc va ghi file!");
        } catch (InterruptedException e) {
            System.out.println("Thread bi gian doan: " + e.getMessage());
        }
    }
}