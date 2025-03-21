package btkethop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DocVaXuLyDuLieu {
    // Hang doi de luu du lieu giua cac thread
    static BlockingQueue<String> hangDoi = new LinkedBlockingQueue<>();

    // Thread doc file
    static class ThreadDoc extends Thread {
        private String tenFile;

        public ThreadDoc(String tenFile) {
            this.tenFile = tenFile;
        }

        @Override
        public void run() {
            try {
                BufferedReader br = new BufferedReader(new FileReader(tenFile));
                String dong;
                System.out.println("Thread Doc: Bat dau doc file");

                // Doc tung dong trong file
                while ((dong = br.readLine()) != null) {
                    // Dua dong vao hang doi
                    hangDoi.put(dong);
                    System.out.println("Thread Doc: Da doc dong: " + dong);
                    Thread.sleep(100);
                }

                // Danh dau het file
                hangDoi.put("KET_THUC");
                br.close();
                System.out.println("Thread Doc: Da doc xong file");
            } catch (Exception e) {
                System.out.println("Loi khi doc file: " + e.getMessage());
            }
        }
    }

    // Thread xu ly du lieu
    static class ThreadXuLy extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("Thread Xu Ly: Bat dau xu ly du lieu");
                int tongSoTu = 0;

                while (true) {
                    // Lay dong tu hang doi
                    String dong = hangDoi.take();

                    // Kiem tra neu la dau ket thuc
                    if (dong.equals("KET_THUC")) {
                        break;
                    }

                    // Dem so tu trong dong
                    int soTu = demSoTu(dong);
                    tongSoTu += soTu;

                    System.out.println("Thread Xu Ly: Dong co " + soTu + " tu");
                    Thread.sleep(200);
                }

                System.out.println("Thread Xu Ly: Hoan thanh. Tong so tu: " + tongSoTu);
            } catch (Exception e) {
                System.out.println("Loi khi xu ly du lieu: " + e.getMessage());
            }
        }

        // Ham dem so tu trong chuoi
        private int demSoTu(String text) {
            if (text == null || text.isEmpty()) {
                return 0;
            }
            return text.split("\\s+").length;
        }
    }

    public static void main(String[] args) {
        // Thay doi ten file tai day
        ThreadDoc threadDoc = new ThreadDoc("input.txt");
        ThreadXuLy threadXuLy = new ThreadXuLy();

        // Bat dau cac thread
        threadDoc.start();
        threadXuLy.start();
    }
}