package baitapfile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Bai4 {
    public static void main(String[] args) {
        String tenFile = "so.dat";
        List<Integer> danhSachSo = Arrays.asList(10, 20, 30, 40, 50);
        System.out.println("Chương trình ghi và đọc số nguyên từ file nhị phân");
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(tenFile))) {
            dos.writeInt(danhSachSo.size());

            for (int so : danhSachSo) {
                dos.writeInt(so);
            }
            System.out.println("Đã ghi " + danhSachSo.size() + " số vào file " + tenFile);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
            return;
        }

        try (DataInputStream dis = new DataInputStream(new FileInputStream(tenFile))) {
            int soLuong = dis.readInt();
            System.out.println("Đọc " + soLuong + " số từ file " + tenFile + ":");

            for (int i = 0; i < soLuong; i++) {
                System.out.println("Số " + (i + 1) + ": " + dis.readInt());
            }

        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}
