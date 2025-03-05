package baitapngay332025;

public class Starvation {
        public static void main(String[] args) {
            Thread highPriority = new Thread(() -> {
                while (true) {
                    System.out.println("luong cao dang chay");
                    Thread.yield(); // Nhường CPU cho luồng khác
                }
            });

            Thread lowPriority = new Thread(() -> {
                while (true) System.out.println("luong thap .....");
            });

            highPriority.setPriority(Thread.MAX_PRIORITY);
            lowPriority.setPriority(Thread.MIN_PRIORITY);

            highPriority.start();
            lowPriority.start();
        }
    }
