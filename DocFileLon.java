package btkethop;

import java.io.RandomAccessFile;
import java.io.File;

public class DocFileLon {
    static class ThreadDoc extends Thread {
        private String tenFile;
        private long viTriBatDau;
        private long doDai;
        private int soThread;

        public ThreadDoc(String tenFile, long viTriBatDau, long doDai, int soThread) {
            this.tenFile = tenFile;
            this.viTriBatDau = viTriBatDau;
            this.doDai = doDai;
            this.soThread = soThread;
        }

        @Override
        public void run() {
            try {
                // Mo file o che do doc
                RandomAccessFile fileNgauNhien = new RandomAccessFile(tenFile, "r");

                // Di chuyen con tro den vi tri bat dau
                fileNgauNhien.seek(viTriBatDau);

                // Tao buffer de doc du lieu
                byte[] buffer = new byte[(int) doDai];
                int bytesDoc = fileNgauNhien.read(buffer);

                // Chuyen du lieu thanh chuoi
                String noiDung = new String(buffer, 0, bytesDoc);

                System.out.println("Thread " + soThread + " doc duoc: " + bytesDoc + " bytes");
                System.out.println("Noi dung dau tien: " + noiDung.substring(0, Math.min(50, noiDung.length())) + "...");

                // Dong file
                fileNgauNhien.close();
            } catch (Exception e) {
                System.out.println("Loi tai Thread " + soThread + ": " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Ten file can doc
        String tenFile = "file_lon.txt";

        // Kiem tra file ton tai
        File file = new File(tenFile);
        if (!file.exists()) {
            System.out.println("File khong ton tai!");
            return;
        }

        // Lay kich thuoc file
        long kichThuocFile = file.length();

        // So luong thread su dung
        int soThread = 4;

        // Tinh kich thuoc moi phan
        long kichThuocPhan = kichThuocFile / soThread;

        // Tao va khoi dong cac thread
        ThreadDoc[] cacThread = new ThreadDoc[soThread];

        System.out.println("Bat dau doc file kich thuoc " + kichThuocFile + " bytes bang " + soThread + " thread");

        for (int i = 0; i < soThread; i++) {
            long viTriBatDau = i * kichThuocPhan;
            long doDai;

            // Neu la thread cuoi cung, doc den het file
            if (i == soThread - 1) {
                doDai = kichThuocFile - viTriBatDau;
            } else {
                doDai = kichThuocPhan;
            }

            cacThread[i] = new ThreadDoc(tenFile, viTriBatDau, doDai, i + 1);
            cacThread[i].start();
        }

        // Cho tat ca thread hoan thanh
        for (int i = 0; i < soThread; i++) {
            cacThread[i].join();
        }

        System.out.println("Da doc xong file!");
    }
}