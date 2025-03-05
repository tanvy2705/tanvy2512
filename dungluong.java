package baitapngay332025;

public class dungluong {
        public static void main(String[] args) throws InterruptedException {
            Thread luong = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Luồng đang chạy");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Luồng bị gián đoạn!");
                        break;
                    }
                }
                System.out.println("Luồng đã dừng.");
            });

            luong.start();
            Thread.sleep(2000);
            luong.interrupt();
        }
    }

