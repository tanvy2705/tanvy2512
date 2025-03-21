package btkethop;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TaiDuLieuTuURL extends Thread {
    private String urlNguon;
    private String fileLuu;

    public TaiDuLieuTuURL(String urlNguon, String fileLuu) {
        this.urlNguon = urlNguon;
        this.fileLuu = fileLuu;
    }

    @Override
    public void run() {
        try {
            // Tao ket noi toi URL
            URL url = new URL(urlNguon);
            URLConnection connection = url.openConnection();

            System.out.println("Dang ket noi toi " + urlNguon + "...");

            // Lay luong du lieu tu URL
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            // Tao writer de ghi vao file
            FileWriter writer = new FileWriter(fileLuu);

            System.out.println("Bat dau tai du lieu...");

            // Doc tung dong va ghi vao file
            String line;
            int dongDaDoc = 0;

            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
                dongDaDoc++;

                // Hien thi tien trinh
                if (dongDaDoc % 100 == 0) {
                    System.out.println("Da tai " + dongDaDoc + " dong...");
                }

                // Sleep de giam tai tren may chu
                if (dongDaDoc % 50 == 0) {
                    Thread.sleep(10);
                }
            }

            // Dong luong doc va ghi
            reader.close();
            writer.close();

            System.out.println("Da tai xong " + dongDaDoc + " dong tu " + urlNguon);
            System.out.println("Du lieu da duoc luu vao " + fileLuu);

        } catch (Exception e) {
            System.out.println("Loi khi tai du lieu: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // URL can tai va file luu
        String urlNguon = "https://www.studocu.vn/vn/course/truong-dai-hoc-cong-nghe-giao-thong-van-tai/java-nang-cao/5130253";
        String fileLuu = "trang_web.html";

        // Tao va khoi dong thread
        TaiDuLieuTuURL thread = new TaiDuLieuTuURL(urlNguon, fileLuu);
        thread.start();

        // Cho thread hoan thanh
        try {
            thread.join();
            System.out.println("Qua trinh tai du lieu da hoan thanh!");
        } catch (InterruptedException e) {
            System.out.println("Thread bi gian doan: " + e.getMessage());
        }
    }
}