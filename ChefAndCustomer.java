package baitapngay332025;
public class ChefAndCustomer {
    public static void main(String[] args) {
        Table table = new Table();

        Thread chef = new Thread(table::cook, "Chef");
        Thread customer = new Thread(table::eat, "Customer");

        chef.start();
        customer.start();
    }
}
