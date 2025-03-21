package btkethop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class DemKyTuTrongFile extends Thread {
    private String fileDoc;
    private String fileGhi;

    public DemKyTuTrongFile(String fileDoc, String fileGhi) {
        this.fileDoc = fileDoc;
        this.fileGhi = fileGhi;
    }

    @Override
    public void run() {
        try {
            // Tao reader de doc file
            BufferedReader reader = new BufferedReader(new FileReader(fileDoc));

            // Map de luu so lan xuat hien cua moi ky tu
            Map<Character, Integer> demKyTu = new HashMap<>();
            int tongSoKyTu = 0;

            System.out.println("Bat dau dem ky tu trong file " + fileDoc);

            // Doc tung ky tu trong file
            int charCode;
            while ((charCode = reader.read()) != -1) {
                char kyTu = (char) charCode;

                // Tang so dem cho ky tu
                demKyTu.put(kyTu, demKyTu.getOrDefault(kyTu, 0) + 1);
                tongSoKyTu++;

                // Mo phong xu ly nang
                if (tongSoKyTu % 1000 == 0) {
                    System.out.println("Da dem " + tongSoKyTu + " ky tu...");
                }
            }

            // Dong file doc
            reader.close();

            // Ghi ket qua vao file
            FileWriter writer = new FileWriter(fileGhi);

            // Ghi tong so ky tu
            writer.write("Tong so ky tu: " + tongSoKyTu + "\n\n");
            writer.write("So lan xuat hien cua tung ky tu:\n");

            // Ghi so lan xuat hien cua tung ky tu
            for (Map.Entry<Character, Integer> entry : demKyTu.entrySet()) {
                char kyTu = entry.getKey();
                int soLan = entry.getValue();
                String tenKyTu;

                // Xu ly ky tu dac biet
                if (kyTu == '\n') {
                    tenKyTu = "\\n (xuong dong)";
                } else if (kyTu == '\r') {
                    tenKyTu = "\\r (carriage return)";
                } else if (kyTu == '\t') {
                    tenKyTu = "\\t (tab)";
                } else if (kyTu == ' ') {
                    tenKyTu = "Space (dau cach)";
                } else {
                    tenKyTu = String.valueOf(kyTu);
                }

                writer.write("'" + tenKyTu + "': " + soLan + " lan\n");
            }

            // Dong file ghi
            writer.close();

            System.out.println("Da dem xong va ghi ket qua vao file " + fileGhi);
            System.out.println("Tong so ky tu: " + tongSoKyTu);

        } catch (Exception e) {
            System.out.println("Loi khi dem ky tu: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Ten file doc va file ghi ket qua
        String fileDoc = "van_ban.txt";
        String fileGhi = "ket_qua_dem.txt";

        // Tao va khoi dong thread
        DemKyTuTrongFile thread = new DemKyTuTrongFile(fileDoc, fileGhi);
        thread.start();

        // Cho thread hoan thanh
        try {
            thread.join();
            System.out.println("Chuong trinh da hoan thanh!");
        } catch (InterruptedException e) {
            System.out.println("Thread bi gian doan: " + e.getMessage());
        }
    }
}