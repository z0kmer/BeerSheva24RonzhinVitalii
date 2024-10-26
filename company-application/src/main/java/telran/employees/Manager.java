package telran.employees;

import java.time.LocalDate;

public class Manager extends Employee {
    private double factor;

    public Manager(long id, String name, String department, int salary, LocalDate birthDate, double factor) {
        super(id, name, department, salary, birthDate);
        this.factor = factor;
    }

    @Override
    public String toString() {
        return super.toString() + ", Manager{" +
                "factor=" + factor +
                '}';
    }
}