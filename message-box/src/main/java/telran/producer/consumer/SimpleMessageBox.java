package telran.producer.consumer;

public class SimpleMessageBox implements MessageBox {
    private String message;

    @Override
    synchronized public void put(String message) throws InterruptedException {
        while (this.message != null) {
            wait();
        }
        this.message = message;
        notifyAll();
    }

    @Override
    synchronized public String take() throws InterruptedException {
        while (message == null) {
            wait();
        }
        String msg = message;
        message = null;
        notifyAll();
        return msg; // may not be null
    }

    @Override
    synchronized public String poll() {
        String msg = message;
        if (msg != null) {
            message = null;
            notifyAll();
        }
        return msg; // it may be null
    }
}
