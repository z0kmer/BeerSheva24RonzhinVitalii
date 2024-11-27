package telran.multithreading;

public class Worker extends Thread {
    private static final Object lockFirst = new Object();
    private static final Object lockSecond = new Object();

    @Override
    public void run() {
        while (true) {
            f1();
            f2();
        }
    }

    private void f1() {
        synchronized (lockFirst) {
            System.out.println(Thread.currentThread().getName() + " locked lockFirst in f1");
            try {
                // We give time for the second thread to lock lockSecond in f2
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockSecond) {
                System.out.println(Thread.currentThread().getName() + " locked lockSecond in f1");
            }
        }
    }

    private void f2() {
        synchronized (lockSecond) {
            System.out.println(Thread.currentThread().getName() + " locked lockSecond in f2");
            try {
                // We give time for the second thread to lock lockFirst in f1
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockFirst) {
                System.out.println(Thread.currentThread().getName() + " locked lockFirst in f2");
            }
        }
    }
}
