package telran.producer.consumer;

public class Main {
    private static final int N_MESSAGES = 20;
    static final int N_RECEIVERS = 10;

    public static void main(String[] args) throws InterruptedException {
        MessageBox messageBox = new SimpleMessageBox();
        Sender sender = new Sender(N_MESSAGES, messageBox);
        Receiver[] receivers = new Receiver[N_RECEIVERS];
        
        for (int i = 0; i < N_RECEIVERS; i++) {
            receivers[i] = new Receiver(messageBox);
            receivers[i].start();
        }

        sender.start();
        sender.join();

        for (Receiver receiver : receivers) {
            receiver.terminate();
        }
    }
}
