package telran.producer.consumer;
public interface MessageBox {
    void put(String message)throws InterruptedException;
    String take() throws InterruptedException;
    String poll();
}