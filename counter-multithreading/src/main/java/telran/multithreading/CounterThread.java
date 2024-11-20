package telran.multithreading;

import java.util.concurrent.atomic.AtomicLong;

public class CounterThread extends Thread{
    private static  int nIterations;
    private static AtomicLong counter = new AtomicLong();
    public static int getnIterations() {
        return nIterations;
    }
    public static void setnIterations(int nIterations) {
        CounterThread.nIterations = nIterations;
    }
    public static long getCounter() {
        return counter.get();
    }
   
    private void counterIncrement() {
        counter.incrementAndGet();
    }
    @Override
    public void run() {
        for(int i = 0; i < nIterations; i++) {
            counterIncrement();
        }
    }
}