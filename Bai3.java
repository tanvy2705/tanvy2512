package baitapfile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

public class Bai3 {
    public static void main(String[] args) {
        String tenFile = "input.txt";
        File file = new File(tenFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("Dòng 1\nDòng 2\nDòng 3\nDòng 4\nDòng 5\n");
                writer.close();
                System.out.println("Đã tạo file " + tenFile + " với 5 dòng mẫu.");
            } catch (IOException e) {
                System.out.println("Lỗi khi tạo file: " + e.getMessage());
                return;
            }
        }

        int soDong = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(tenFile))) {
            while (reader.readLine() != null) {
                soDong++;
            }

            System.out.println("Số dòng trong file " + tenFile + ": " + soDong);

        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}
