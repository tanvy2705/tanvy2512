package btkethop;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadGhiLog extends Thread {
    private String fileLog;

    public ThreadGhiLog(String fileLog) {
        this.fileLog = fileLog;
    }

    // Ham ghi log
    public void ghiLog(String thongDiep) {
        try {
            // Tao doi tuong ghi file
            FileWriter fw = new FileWriter(fileLog, true);

            // Dinh dang thoi gian
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String thoiGian = formatter.format(new Date());

            // Ghi thong diep va thoi gian vao file
            fw.write("[" + thoiGian + "] " + thongDiep + "\n");

            // Dong file
            fw.close();

            System.out.println("Da ghi log: " + thongDiep);
        } catch (IOException e) {
            System.out.println("Loi khi ghi log: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            ghiLog("Thong diep su kien " + i);
            try {
                // Tam dung 1 giay
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread bi gian doan: " + e.getMessage());
            }
        }
        ghiLog("Ket thuc thread ghi log");
    }

    public static void main(String[] args) {
        ThreadGhiLog logger = new ThreadGhiLog("application.log");
        logger.start();

        // Mo phong cac hoat dong khac
        for (int i = 1; i <= 5; i++) {
            System.out.println("Chuong trinh chinh dang chay: " + i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
