package baitapngay332025;
import java.util.LinkedList;

public class Table {
    private final int CAPACITY = 5;
    private final LinkedList<String> dishes = new LinkedList<>();

    public synchronized void cook() {
        while (true) {
            try {
                while (dishes.size() >= CAPACITY) {
                    System.out.println("full ban, dau bep doi...");
                    wait();
                }
                Thread.sleep(2000);
                String dish = "mon" + (dishes.size() + 1);
                dishes.add(dish);
                System.out.println("dau bep len mon: " + dish);
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void eat() {
        while (true) {
            try {
                while (dishes.isEmpty()) {
                    System.out.println("quan e khạch doi");
                    wait();
                }
                Thread.sleep(5000);
                String dish = dishes.removeFirst();
                System.out.println("khach da mukbang: " + dish);
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
