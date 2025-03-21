package btkethop;

    import java.io.BufferedReader;
import java.io.FileReader;

    public class DocFileBangThread extends Thread {
        private String tenFile;

        public DocFileBangThread(String tenFile) {
            this.tenFile = tenFile;
        }

        @Override
        public void run() {
            try {
                // Tao doi tuong doc file
                FileReader fr = new FileReader(tenFile);
                BufferedReader br = new BufferedReader(fr);

                String dong;
                // Doc tung dong cho den het file
                while ((dong = br.readLine()) != null) {
                    System.out.println(dong);
                    // Tam dung de thay ro
                    Thread.sleep(100);
                }

                // Dong file
                br.close();
                fr.close();

                System.out.println("Da doc xong file: " + tenFile);
            } catch (Exception e) {
                System.out.println("Loi khi doc file: " + e.getMessage());
            }
        }

        public static void main(String[] args) {
            // Thay doi ten file tai day
            DocFileBangThread thread = new DocFileBangThread("test.txt");
            thread.start();
        }
    }

