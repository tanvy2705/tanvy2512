package baitapngay332025;

public class OddEvenThread {
    public static void main(String[] args) {
        PrintNumbers printNumbers = new PrintNumbers();

        Thread oddThread = new Thread(printNumbers::printOdd, "OddThread");
        Thread evenThread = new Thread(printNumbers::printEven, "EvenThread");

        oddThread.start();
        evenThread.start();
    }
}


