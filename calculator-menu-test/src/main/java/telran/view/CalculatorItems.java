package telran.view;

public class CalculatorItems {
    private static Calculator calculator;
    public static Item[] getItems(Calculator calculator) {
        CalculatorItems.calculator = calculator;
        Item[] res = {
                Item.of("Add numbers", CalculatorItems::add),
                Item.of("Subtract numbers", CalculatorItems::subtract),
                Item.of("Multiply numbers", CalculatorItems::multiply),
                Item.of("Divide numbers", CalculatorItems::divide),
                Item.ofExit()
        };
        return res;
    }
    static double[] enterNumbers(InputOutput io) {
        double op1 = io.readDouble("Enter first number", "");
        double op2 = io.readDouble("Enter second number", "");
        return new double[]{op1, op2};
    }
    static void add(InputOutput io){
        double[] numbers = enterNumbers(io);
        io.writeLine(calculator.add(numbers[0], numbers[1]));
    }
    static void subtract(InputOutput io){
        double[] numbers = enterNumbers(io);
        io.writeLine(calculator.subtract(numbers[0], numbers[1]));
    }
    static void multiply(InputOutput io){
        double[] numbers = enterNumbers(io);
        io.writeLine(calculator.multiply(numbers[0], numbers[1]));
    }
    static void divide(InputOutput io){
        double[] numbers = enterNumbers(io);
        io.writeLine(calculator.divide(numbers[0], numbers[1]));
    }
}