package baitapfile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

    public class Bai1 {
        public static void main(String[] args) {
            String fileNguon = "nguon.txt";
            String fileDich = "dich.txt";
            try {
                File fileNguonObj = new File(fileNguon);
                if (!fileNguonObj.exists()) {
                    fileNguonObj.createNewFile();
                    System.out.println("Đã tạo file " + fileNguon + ". Hãy thêm nội dung vào file này trước khi chạy lại chương trình.");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Lỗi khi tạo file nguồn: " + e.getMessage());
                return;
            }

            try (FileInputStream fis = new FileInputStream(fileNguon);
                 FileOutputStream fos = new FileOutputStream(fileDich)) {

                int byteData;
                while ((byteData = fis.read()) != -1) {
                    fos.write(byteData);
                }

                System.out.println("Đã sao chép thành công từ " + fileNguon + " sang " + fileDich);

            } catch (IOException e) {
                System.out.println("Lỗi khi sao chép file: " + e.getMessage());
            }
        }
    }

