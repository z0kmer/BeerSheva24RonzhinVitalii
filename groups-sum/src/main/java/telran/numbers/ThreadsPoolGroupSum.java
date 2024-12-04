package telran.numbers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ThreadsPoolGroupSum extends ThreadsGroupSum{

    private static final int N_THREADS = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService;

    public ThreadsPoolGroupSum(int[][]groups) {
        super(groups);
        executorService = Executors.newFixedThreadPool(N_THREADS);
    }
    @Override
   protected void startTasks(FutureTask<Long>[] tasks) {
        //write this method by using threads pool
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new FutureTask<>(new OneGroupSum(groups[i]));
            executorService.submit(tasks[i]);
        }
    }

    @Override
    public long computeSum() {
        long sum = super.computeSum();
        executorService.shutdown();
        return sum;
    }
}