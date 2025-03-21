package btkethop;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaoChepFile extends Thread {
    private String fileNguon;
    private String fileDich;

    public SaoChepFile(String fileNguon, String fileDich) {
        this.fileNguon = fileNguon;
        this.fileDich = fileDich;
    }

    @Override
    public void run() {
        try {
            // Tao doi tuong doc file
            FileInputStream fis = new FileInputStream(fileNguon);
            // Tao doi tuong ghi file
            FileOutputStream fos = new FileOutputStream(fileDich);

            // Tao buffer de doc file
            byte[] buffer = new byte[1024];
            int bytesDoc;

            System.out.println("Bat dau sao chep file...");

            // Doc va ghi tu file nguon sang file dich
            while ((bytesDoc = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesDoc);
                // Tam dung de hien thi tien trinh
                Thread.sleep(50);
                System.out.println("Da sao chep " + bytesDoc + " bytes");
            }

            // Dong file
            fis.close();
            fos.close();

            System.out.println("Da sao chep xong file tu " + fileNguon + " sang " + fileDich);
        } catch (IOException | InterruptedException e) {
            System.out.println("Loi khi sao chep file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Thay doi ten file tai day
        SaoChepFile thread = new SaoChepFile("nguon.txt", "dich.txt");
        thread.start();
    }
}
