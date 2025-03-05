package Baitapdaluong;

public class Clock extends Thread {
    private void printTime(int hours, int minute, int second){
        System.out.println(String.format("%02d:%02d:%02d", hours,minute,second));
    }

    public void run(){
        try {
            int hours = 0;
            int minute = 0;
            int second = 0;

            while(true){
                printTime(hours,minute,second);
                Thread.sleep(1000);
                second++;
                if(second == 60){
                    second = 0;
                    minute++;
                    if(minute == 60){
                        minute=0;
                        hours++;
                    }if(hours == 24){
                        hours =0;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
