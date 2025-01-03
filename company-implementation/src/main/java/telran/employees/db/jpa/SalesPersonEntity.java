package telran.employees.db.jpa;

import jakarta.persistence.Entity;

@Entity
public class SalesPersonEntity extends WageEmployeeEntity {
    private float percent;
    private long sales;

    @Override
    protected void fromEmployeeDto(Employee empl) {
        super.fromEmployeeDto(empl);
        SalesPerson salesPerson = (SalesPerson) empl;
        this.percent = salesPerson.getPercent();
        this.sales = salesPerson.getSales();
    }

    @Override
    protected void toJsonObject(JSONObject jsonObj) {
        super.toJsonObject(jsonObj);
        jsonObj.put("percent", this.percent);
        jsonObj.put("sales", this.sales);
    }

    @Override
    public int computeSalary() {
        return (int) (super.computeSalary() + sales * percent / 100);
    }
}
