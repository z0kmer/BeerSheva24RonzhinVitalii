package telran.multithreading;

import java.util.Arrays;

public class Main {
    private static final int N_THREADS = 5;
    private static final int N_ITERATIONS = 3;

    public static void main(String[] args) {
        CounterThread[] threads = new CounterThread[N_THREADS];
        CounterThread.setnIterations(N_ITERATIONS);
        startThreads(threads);
        waitThreadsFinishing(threads);
        System.out.printf("Total value of counter is %d",CounterThread.getCounter());
    }

    private static void waitThreadsFinishing(CounterThread[] threads) {
        Arrays.stream(threads).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                
            }
        });
    }

    private static void startThreads(CounterThread[] threads) {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }
    }
}