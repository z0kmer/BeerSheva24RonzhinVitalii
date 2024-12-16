package telran.producer.consumer;

public class Receiver extends Thread {
    private MessageBox messageBox;
    private boolean isEvenReceiver;

    public Receiver(MessageBox messageBox, boolean isEvenReceiver) {
        this.messageBox = messageBox;
        this.isEvenReceiver = isEvenReceiver;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = messageBox.take();
                int messageNumber = Integer.parseInt(message.replaceAll("\\D+", ""));
                if ((messageNumber % 2 == 0 && isEvenReceiver) || (messageNumber % 2 != 0 && !isEvenReceiver)) {
                    System.out.printf("Thread: %s, message: %s\n", getName(), message);
                } else {
                    messageBox.put(message); // Return message back to the box if it doesn't match
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
