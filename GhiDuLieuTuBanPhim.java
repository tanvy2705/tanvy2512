package btkethop;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GhiDuLieuTuBanPhim {
    // Hang doi luu tru du lieu giua thread doc va thread ghi
    private static BlockingQueue<String> hangDoi = new LinkedBlockingQueue<>();
    private static boolean dangChay = true;

    // Thread doc du lieu tu ban phim
    static class ThreadDocBanPhim extends Thread {
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nhap du lieu (nhap 'exit' de thoat):");

            try {
                while (dangChay) {
                    // Doc dong du lieu nhap vao
                    String dong = scanner.nextLine();

                    // Kiem tra neu nguoi dung muon thoat
                    if (dong.equalsIgnoreCase("exit")) {
                        // Gui tin hieu ket thuc
                        hangDoi.put("KET_THUC");
                        dangChay = false;
                        break;
                    }

                    // Dua dong du lieu vao hang doi
                    hangDoi.put(dong);
                    System.out.println("Da them vao hang doi: " + dong);
                }

                scanner.close();
            } catch (InterruptedException e) {
                System.out.println("Thread doc bi gian doan: " + e.getMessage());
            }
        }
    }

    // Thread ghi du lieu vao file
    static class ThreadGhiFile extends Thread {
        private String tenFile;

        public ThreadGhiFile(String tenFile) {
            this.tenFile = tenFile;
        }

        @Override
        public void run() {
            try {
                // Tao writer voi che do append
                BufferedWriter writer = new BufferedWriter(new FileWriter(tenFile, true));

                while (true) {
                    // Lay du lieu tu hang doi
                    String dong = hangDoi.take();

                    // Kiem tra neu la tin hieu ket thuc
                    if (dong.equals("KET_THUC")) {
                        break;
                    }

                    // Ghi du lieu vao file
                    writer.write(dong);
                    writer.newLine();
                    writer.flush();

                    System.out.println("Da ghi vao file: " + dong);
                }

                // Dong file
                writer.close();
                System.out.println("Da dong file");

            } catch (Exception e) {
                System.out.println("Loi khi ghi file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Ten file can ghi
        String tenFile = "nhap_du_lieu.txt";

        // Tao thread doc va ghi
        ThreadDocBanPhim threadDoc = new ThreadDocBanPhim();
        ThreadGhiFile threadGhi = new ThreadGhiFile(tenFile);

        // Bat dau thread
        threadDoc.start();
        threadGhi.start();

        // Cho threads hoan thanh
        try {
            threadDoc.join();
            threadGhi.join();
        } catch (InterruptedException e) {
            System.out.println("Loi khi cho threads hoan thanh: " + e.getMessage());
        }

        System.out.println("Chuong trinh da ket thuc");
    }
}