package telran.view;

public class SimpleCalculator implements Calculator{

    @Override
    public double add(double op1, double op2) {
        return op1 + op2;
    }

    @Override
    public double subtract(double op1, double op2) {
        return op1 - op2;
    }

    @Override
    public double multiply(double op1, double op2) {
        return op1 * op2;
    }

    @Override
    public double divide(double op1, double op2) {
        return op1 / op2;
    }

}