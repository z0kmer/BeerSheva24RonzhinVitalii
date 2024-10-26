package telran.employees;

import java.time.LocalDate;

public class WageEmployee extends Employee {
    private int wage;
    private int hours;

    public WageEmployee(long id, String name, String department, int wage, int hours) {
        super(id, name, department, wage * hours, LocalDate.now());
        this.wage = wage;
        this.hours = hours;
    }

    @Override
    public String toString() {
        return super.toString() + ", WageEmployee{" +
                "wage=" + wage +
                ", hours=" + hours +
                '}';
    }
}
