package telran.multithreading;

import java.util.Random;

public class Racer extends Thread implements Comparable<Racer> {
    private Race race;
    private int number;
    private long raceTime;

    public Racer(Race race, int number) {
        this.race = race;
        this.number = number;
    }

    @Override
    public void run() {
        int minSleep = race.getMinSleep();
        int maxSleep = race.getMaxSleep();
        int distance = race.getDistance();
        Random random = new Random();
        long start = System.currentTimeMillis();

        for (int i = 0; i < distance; i++) {
            try {
                sleep(random.nextInt(minSleep, maxSleep + 1));
                System.out.printf("%d - step %d\n", number, i);
            } catch (InterruptedException e) {}
        }

        raceTime = System.currentTimeMillis() - start;
        race.winner.compareAndSet(-1, number);
    }

    public int getNumber() {
        return number;
    }

    public long getRaceTime() {
        return raceTime;
    }

    @Override
    public int compareTo(Racer other) {
        return Long.compare(this.raceTime, other.raceTime);
    }
}
