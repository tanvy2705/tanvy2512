package baitapngay332025;

public class vandebonho {
        private static volatile boolean flag = false;

        public static void main(String[] args) {
            Thread luong1 = new Thread(() -> {
                while (!flag) { }
                System.out.println("Luồng 1 nhận thấy flag thay đổi!");
            });

            luong1.start();

            try { Thread.sleep(2000); } catch (InterruptedException e) {}

            flag = true;
            System.out.println("Luồng 2 đã thay đổi flag thành true.");
        }
    }


