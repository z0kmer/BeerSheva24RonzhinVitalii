package telran.employees;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import telran.io.Persistable;

public class EmployeesDataSaver implements Runnable {
    private Persistable persistable;
    private long interval;
    private ScheduledExecutorService scheduler;

    public EmployeesDataSaver(Persistable persistable, long interval, TimeUnit unit) {
        this.persistable = persistable;
        this.interval = unit.toMillis(interval);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this, interval, interval, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        scheduler.shutdown();
        try {
            scheduler.awaitTermination(interval, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        persistable.saveToFile("employees.data");
    }
}
