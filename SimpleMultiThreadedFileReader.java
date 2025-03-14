package baitapfile;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class SimpleMultiThreadedFileReader {
    // Kích thước mỗi đoạn 1MB
    private static final int CHUNK_SIZE = 1024 * 1024;
    public static void main(String[] args) throws Exception {
        String tanvydeptrai = "tanvydeptrai.txt";
        String outputFile = "output.txt";
        readFileWithMultipleThreads(tanvydeptrai, outputFile);
        System.out.println("Hoàn thành!");
    }
    public static void readFileWithMultipleThreads(String inputFile, String outputFile) throws Exception {
        File file = new File(inputFile);
        long fileSize = file.length();

        // tính số đoạn ần chia
        int numChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);

        System.out.println("Kích thước file: " + fileSize + " bytes");
        System.out.println("Số đoạn: " + numChunks);

        // thread pool
        ExecutorService executor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        List<Future<byte[]>> chunkResults = new ArrayList<>();
        // doc het doan từng đoan
        for (int i = 0; i < numChunks; i++) {
            final int chunkIndex = i;
            final long startPosition = (long) i * CHUNK_SIZE;
            final long endPosition = Math.min(startPosition + CHUNK_SIZE, fileSize);
            // đọc tđoạn và thêm vào thread pool
            Future<byte[]> future = executor.submit(() -> {
                byte[] buffer = new byte[(int)(endPosition - startPosition)];

                try (RandomAccessFile raf = new RandomAccessFile(inputFile, "r")) {
                    raf.seek(startPosition);
                    raf.readFully(buffer);
                    System.out.println("Đã đọc đoạn " + chunkIndex);
                    return buffer;
                }
            });
            chunkResults.add(future);
        }
        // ghi ket qua vao file theo thu tu
        try (FileOutputStream output = new FileOutputStream(outputFile)) {
            for (Future<byte[]> result : chunkResults) {
                output.write(result.get());
            }
        }
        // đoóng thread pool
        executor.shutdown();
    }
}