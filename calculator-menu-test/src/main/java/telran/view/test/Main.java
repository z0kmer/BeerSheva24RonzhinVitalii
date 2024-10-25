package telran.view.test;
import telran.view.CalculatorItems;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.SimpleCalculator;
import telran.view.StandardInputOutput;
public class Main {
    public static void main(String[] args) {
       InputOutput io = new StandardInputOutput();
       Item[] items = CalculatorItems.getItems(new SimpleCalculator());
       Menu menu = new Menu("Calculator", items);
       menu.perform(io);
    }
}