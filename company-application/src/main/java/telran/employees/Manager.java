package telran.employees;

import java.time.LocalDate;

public class Manager extends Employee {
    private double factor;

    public Manager(long id, String name, String department, int salary, double factor) {
        super(id, name, department, salary, LocalDate.now());
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }

    @Override
    public String toString() {
        return super.toString() + ", Manager{" +
                "factor=" + factor +
                '}';
    }
}
