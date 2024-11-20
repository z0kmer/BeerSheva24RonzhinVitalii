package telran.multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {
//Fields and methods for Race parameters
// min_sleep_timeout, max_sleep_time for getting some random sleep value in each iteration as random factor for racer-winner definition
//distance - number of iterations
//any others possible fields

    private int nRacers; private int distance;
    private Racer[] racers;
    private AtomicInteger winner = new AtomicInteger(-1); // -1 означает, победителя нет сейчас
    private CountDownLatch latch;

    public Race(int nRacers, int distance) {
        this.nRacers = nRacers;
        this.distance = distance;
        this.racers = new Racer[nRacers];
        this.latch = new CountDownLatch(nRacers);
    }

    public int getDistance() {
        return distance;
    }

    public boolean setWinner(int number) {
        return winner.compareAndSet(-1, number);
    }

    public void startRace() {
        for (int i = 0; i < nRacers; i++) {
            racers[i] = new Racer(this, i + 1);
            racers[i].start();
        }
    }
        
    public void awaitRaceEnd() {
        try {
            latch.await();
            System.out.printf("Поздравляем гонщика №%d с победой!\n", winner.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void countDown() {
        latch.countDown();
    }
}