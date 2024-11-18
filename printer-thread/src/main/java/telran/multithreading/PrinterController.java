package telran.multithreading;

public class PrinterController {
    public static void main(String[] args) {
        Printer printer1 = new Printer('#');
        Printer printer2 = new Printer('*');
        printer1.start();
        printer2.start();
    }
}