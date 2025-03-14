package baitapfile;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class MultiThreadedFileProcessor {
    private static final String[] INPUT_FILES = {
            "C:\\Users\\ACER\\IdeaProjects\\untitled\\src\\input1.txt",
            "C:\\Users\\ACER\\IdeaProjects\\untitled\\src\\input2.txt",
            "C:\\Users\\ACER\\IdeaProjects\\untitled\\src\\input3.txt"
    };
    private static final String OUTPUT_FILE = "C:\\Users\\ACER\\IdeaProjects\\untitled\\src\\output.txt";

    public static void main(String[] args) {
        List<Future<String>> results = new ArrayList<>();

        // Tạo thread pool
        ExecutorService executor = Executors.newFixedThreadPool(INPUT_FILES.length);

        // Tạo các task đọc file và submit vào thread pool
        for (String fileName : INPUT_FILES) {
            Callable<String> task = () -> readFile(fileName);
            Future<String> result = executor.submit(task);
            results.add(result);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(OUTPUT_FILE))) {
            for (Future<String> result : results) {
                try {
                    // Đồng bộ hóa - đợi cho đến khi thread hoàn thành
                    String content = result.get();
                    synchronized (writer) {
                        writer.write(content);
                        writer.newLine();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("Lỗi khi đọc file: " + e.getMessage());
                }
            }
            System.out.println("Đã ghi thành công vào file " + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        } finally {
            // Đóng thread pool
            executor.shutdown();
        }
    }
    private static String readFile(String fileName) throws IOException {
        System.out.println("Đang đọc file: " + fileName);
        StringBuilder content = new StringBuilder();

        // Thêm header
        content.append("=== Nội dung từ file: ").append(fileName).append(" ===\n");

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        System.out.println("Đã đọc xong file: " + fileName);
        return content.toString();
    }
}