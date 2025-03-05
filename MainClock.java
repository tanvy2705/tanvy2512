package Baitapdaluong;

public class MainClock {
    public static void main(String[] args) {
        Thread thread = new Thread(new Clock());
        thread.start();
    }
}

