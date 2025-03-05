package Baitapdaluong;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SoChanSoLe printer = new SoChanSoLe();
        Thread even = new Thread(() -> printer.Even());
        Thread odd = new Thread(new Runnable() {
            @Override
            public void run() {
                printer.Odd();
            }
        });
        odd.start();
//        odd.join();
        even.start();
//       even.join();

//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=0; i<10; i++) {
//                    System.out.println(i);
//                }
//            }
//        });
//
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=10; i<20; i++) {
//                    System.out.println(i);
//                }
//            }
//        });
//
//        thread1.start();
//        thread1.join();
//        thread2.start();
//        thread2.join();

    }
}


