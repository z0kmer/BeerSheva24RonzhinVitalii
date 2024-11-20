package telran.multithreading;

import java.util.concurrent.ThreadLocalRandom;

public class Racer extends Thread{
    private Race race;
    private int number;
    
    public Racer(Race race, int number) {
        this.race = race;
        this.number = number;
    }
    
    @Override
    public void run(){
        //Running cycle containing number of iterations from the Race reference as the distance
        //Each iteration is printing out the number of the thread for game tracing to see game dynamics
        
        for (int i = 0; i < race.getDistance(); i++) {
            System.out.printf("Гонщик №%d проходит дистанцию (выполняет итерацию) %d\n", number, i + 1);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10)); // случайное время от 1 до 10 мс
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        if (race.setWinner(number)) {
            System.out.printf("Гонщик №%d закончил первым!\n", number);
        }
            
        race.countDown();
    }
}