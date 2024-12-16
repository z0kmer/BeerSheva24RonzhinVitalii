package telran.producer.consumer;

public class Sender extends Thread {
    private int nMessages;
    private MessageBox evenMessageBox;
    private MessageBox oddMessageBox;

    public Sender(int nMessages, MessageBox evenMessageBox, MessageBox oddMessageBox) {
        this.nMessages = nMessages;
        this.evenMessageBox = evenMessageBox;
        this.oddMessageBox = oddMessageBox;
    }

    @Override
    public void run() {
        for (int i = 0; i < nMessages; i++) {
            try {
                if (i % 2 == 0) {
                    evenMessageBox.put("Message" + (i + 1));
                } else {
                    oddMessageBox.put("Message" + (i + 1));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
