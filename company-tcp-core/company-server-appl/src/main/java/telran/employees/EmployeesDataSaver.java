import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import telran.io.Persistable;

public class EmployeesDataSaver implements Runnable {
    private Persistable persistable;
    private ScheduledExecutorService scheduler;

    public EmployeesDataSaver(Persistable persistable, long interval, TimeUnit unit) {
        this.persistable = persistable;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this, interval, interval, unit);
    }

    public void stop() {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        persistable.saveToFile("employees.data");
    }
}
