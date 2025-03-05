package baitapngay332025;
class PrintNumbers {
    private int number = 1;
    private final int MAX = 10;

    public synchronized void printOdd() {
        while (number <= MAX) {
            if (number % 2 == 1) {
                System.out.println(Thread.currentThread().getName() + ": " + number);
                number++;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void printEven() {
        while (number <= MAX) {
            if (number % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ": " + number);
                number++;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
