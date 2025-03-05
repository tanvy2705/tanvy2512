package Baitapdaluong;

public class Mainbanve {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new QuayVe("a"));
        Thread thread2 = new Thread(new QuayVe("b"));
        Thread thread3 = new Thread(new QuayVe("c"));
        thread1.start();
        thread2.start();
        thread3.start();

    }
}