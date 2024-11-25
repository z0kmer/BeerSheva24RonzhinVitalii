package telran.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class Race {
    private int distance;
    private int minSleep;
    private int maxSleep;
    private long start;
    AtomicInteger winner = new AtomicInteger(-1);

    public Race(int distance, int minSleep, int maxSleep) {
        this.distance = distance;
        this.minSleep = minSleep;
        this.maxSleep = maxSleep;
        this.start = System.currentTimeMillis();
    }

    public int getWinner() {
        return winner.get();
    }

    public int getDistance() {
        return distance;
    }

    public int getMinSleep() {
        return minSleep;
    }

    public int getMaxSleep() {
        return maxSleep;
    }

    public long getStartTime() {
        return start;
    }
}
