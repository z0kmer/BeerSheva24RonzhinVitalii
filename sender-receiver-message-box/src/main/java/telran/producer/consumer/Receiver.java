package telran.producer.consumer;

public class Receiver extends Thread {
    private MessageBox messageBox;
    private volatile boolean running = true;

    public Receiver(MessageBox messageBox) {
        this.messageBox = messageBox;
        setDaemon(false);
    }

    public void terminate() {
        running = false;
        this.interrupt();
    }

    @Override
    public void run() {
        while (running) {
            try {
                String message = messageBox.take();
                System.out.printf("Thread: %s, message: %s\n", getName(), message);
            } catch (InterruptedException e) {
                if (!running) {
                    break;
                }
            }
        }
    }
}
