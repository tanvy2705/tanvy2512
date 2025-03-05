package Baitapdaluong;

public class SoChanSoLe extends Thread {
    public boolean isEven = false;
    public synchronized void Even(){
        for(int i =2; i<= 10; i+=2){
            while (!isEven){
                try{
                    wait();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("So chan: " + i);
            isEven = false;
            notify();
        }
    }

    public synchronized void Odd(){
        for(int i =1; i <= 10; i+=2){
            while (isEven){
                try{
                    wait();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("so le : " + i);
            isEven = true;
            notify();
        }
    }



}

