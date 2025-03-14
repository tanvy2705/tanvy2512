package baitapfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TimKiemTuKhoa {
    // Sử dụng ConcurrentHashMap
    private static final ConcurrentHashMap<String, Integer> ketQuaTimKiem = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Nhập đường dẫn thư mục
        System.out.print("Nhập đường dẫn thư mục: ");
        String duongDanThuMuc = scanner.nextLine();
        // Nhập từ khóa cần tìm
        System.out.print("Nhập từ khóa cần tìm: ");
        String tuKhoa = scanner.nextLine();
        // Nhập số luồng
        System.out.print("Nhập số luồng (mặc định: 4): ");
        int soLuong = 4;
        try {
            soLuong = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Số luồng không hợp lệ. Sử dụng giá trị mặc định (4).");
        }
        scanner.close();
        List<File> danhSachFile = new ArrayList<>();
        try {
            danhSachFile = Files.walk(Paths.get(duongDanThuMuc))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt")) // Chỉ xử lý file .txt
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc thư mục: " + e.getMessage());
            System.exit(1);
        }
        if (danhSachFile.isEmpty()) {
            System.out.println("Không tìm thấy file nào trong thư mục.");
            System.exit(0);
        }
        System.out.println("Tìm thấy " + danhSachFile.size() + " file. Đang tìm kiếm...");
        // tìm kiếm với Thread
        timKiemVoiThread(danhSachFile, tuKhoa, soLuong);
        inKetQua();
    }
    private static void timKiemVoiThread(List<File> danhSachFile, String tuKhoa, int soLuong) {
        int soFileChoMoiLuong = (int) Math.ceil((double) danhSachFile.size() / soLuong);
        List<Thread> danhSachThread = new ArrayList<>();
        for (int i = 0; i < soLuong; i++) {
            int batDau = i * soFileChoMoiLuong;
            int ketThuc = Math.min(batDau + soFileChoMoiLuong, danhSachFile.size());

            if (batDau >= danhSachFile.size()) break;

            List<File> phanDoan = danhSachFile.subList(batDau, ketThuc);
            Thread thread = new Thread(new TimKiemTask(phanDoan, tuKhoa));
            danhSachThread.add(thread);
            thread.start();
        }
        for (Thread thread : danhSachThread) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread bị gián đoạn: " + e.getMessage());
            }
        }
    }

    //  executorService
    private static void timKiemVoiExecutorService(List<File> danhSachFile, String tuKhoa, int soLuong) {
        ExecutorService executorService = Executors.newFixedThreadPool(soLuong);

        for (File file : danhSachFile) {
            executorService.submit(() -> timKiemTrongFile(file, tuKhoa));
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.err.println("ExecutorService bị gián đoạn: " + e.getMessage());
        }
    }
    private static class TimKiemTask implements Runnable {
        private final List<File> danhSachFile;
        private final String tuKhoa;

        public TimKiemTask(List<File> danhSachFile, String tuKhoa) {
            this.danhSachFile = danhSachFile;
            this.tuKhoa = tuKhoa;
        }
        @Override
        public void run() {
            for (File file : danhSachFile) {
                timKiemTrongFile(file, tuKhoa);
            }
        }
    }
    private static void timKiemTrongFile(File file, String tuKhoa) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int soLanXuatHien = 0;
            while ((line = reader.readLine()) != null) {
                int index = 0;
                while (index != -1) {
                    index = line.indexOf(tuKhoa, index);
                    if (index != -1) {
                        soLanXuatHien++;
                        index += tuKhoa.length();
                    }
                }
            }
            if (soLanXuatHien > 0) {
                ketQuaTimKiem.put(file.getName(), soLanXuatHien);
                System.out.println("Tìm thấy " + soLanXuatHien + " lần xuất hiện của \"" + tuKhoa + "\" trong file " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file " + file.getName() + ": " + e.getMessage());
        }
    }
    private static void inKetQua() {
        System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");

        if (ketQuaTimKiem.isEmpty()) {
            System.out.println("Không tìm thấy từ khóa trong bất kỳ file nào.");
            return;
        }
        int tongSoLanXuatHien = 0;
        List<Map.Entry<String, Integer>> danhSachKetQua = new ArrayList<>(ketQuaTimKiem.entrySet());
        danhSachKetQua.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())); // Sắp xếp giảm dần
        System.out.println("Số file chứa từ khóa: " + ketQuaTimKiem.size());
        System.out.println("\nDanh sách các file (theo số lần xuất hiện):");
        for (Map.Entry<String, Integer> entry : danhSachKetQua) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " lần");
            tongSoLanXuatHien += entry.getValue();
        }
        System.out.println("\nTổng số lần xuất hiện: " + tongSoLanXuatHien);
    }
}
