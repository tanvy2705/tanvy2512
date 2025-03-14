package baitapfile;

import java.io.File;

public class Bai5 {
    public static void main(String[] args) {
        String thuMuc = "./";  // Thư mục hiện tại

        System.out.println("Chương trình liệt kê các file và thư mục");

        File dir = new File(thuMuc);

        if (dir.isDirectory()) {
            File[] danhSachFile = dir.listFiles();

            if (danhSachFile != null && danhSachFile.length > 0) {
                System.out.println("Các file và thư mục trong " + thuMuc + ":");
                System.out.println("----------------------------------");

                for (File file : danhSachFile) {
                    String loai = file.isDirectory() ? "Thư mục" : "File   ";
                    System.out.println(loai + ": " + file.getName());
                }
                System.out.println("----------------------------------");
                System.out.println("Tổng cộng: " + danhSachFile.length + " file/thư mục");
            } else {
                System.out.println("Thư mục " + thuMuc + " trống hoặc không thể đọc được nội dung.");
            }
        } else {
            System.out.println(thuMuc + " không phải là thư mục hợp lệ.");
        }
    }
}


