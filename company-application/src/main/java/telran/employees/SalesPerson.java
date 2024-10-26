package telran.employees;

import java.time.LocalDate;

public class SalesPerson extends Employee {
    private int sales;
    private double percent;

    public SalesPerson(long id, String name, String department, int sales, double percent) {
        super(id, name, department, (int) (sales * percent), LocalDate.now());
        this.sales = sales;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return super.toString() + ", SalesPerson{" +
                "sales=" + sales +
                ", percent=" + percent +
                '}';
    }
}
