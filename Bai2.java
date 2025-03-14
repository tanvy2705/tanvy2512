package baitapfile;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bai2 {
    public static void main(String[] args) {
        String tenFile = "nhap.txt";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileWriter writer = new FileWriter(tenFile)) {

            System.out.println("Chương trình ghi dữ liệu từ bàn phím vào file");
            System.out.println("Nhập nội dung (Nhập 'exit' để kết thúc):");
            String dong;

            while (!(dong = reader.readLine()).equalsIgnoreCase("exit")) {
                writer.write(dong + "\n");
            }

            System.out.println("Đã lưu dữ liệu vào file " + tenFile);

        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
}
