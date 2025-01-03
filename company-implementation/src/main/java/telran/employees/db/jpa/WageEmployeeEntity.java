package telran.employees.db.jpa;

import org.json.JSONObject;

import jakarta.persistence.Entity;
import telran.employees.Employee;
import telran.employees.WageEmployee;

@Entity
public class WageEmployeeEntity extends EmployeeEntity {
    private int wage;
    private int hours;

    @Override
    protected void fromEmployeeDto(Employee empl) {
        super.fromEmployeeDto(empl);
        WageEmployee wageEmployee = (WageEmployee) empl;
        this.wage = wageEmployee.getWage();
        this.hours = wageEmployee.getHours();
    }

    @Override
    protected void toJsonObject(JSONObject jsonObj) {
        super.toJsonObject(jsonObj);
        jsonObj.put("wage", this.wage);
        jsonObj.put("hours", this.hours);
    }

    @Override
    public int computeSalary() {
        return super.computeSalary() + wage * hours;
    }
}
