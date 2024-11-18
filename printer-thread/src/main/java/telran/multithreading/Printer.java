package telran.multithreading;

public class Printer extends Thread{
    private static final int DEFAULT_NUMBER_OF_ITERATIONS = 100;
        private final char symbol;
        private static int nIterations = DEFAULT_NUMBER_OF_ITERATIONS;
    public Printer(char symbol) {
        this.symbol = symbol;
    }
    static public void setNiterations(int nIterations) {
        Printer.nIterations = nIterations;
    }
@Override
public void run(){
    for(int i = 0; i < nIterations; i++) {
        System.out.println(symbol);
        try {
            sleep(10);
        } catch (InterruptedException e) {
           
        }
    }
}
}