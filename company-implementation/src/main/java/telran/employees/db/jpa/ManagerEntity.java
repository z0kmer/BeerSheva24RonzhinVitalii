package telran.employees.db.jpa;

import jakarta.persistence.Entity;
import telran.employees.Employee;
import org.json.JSONObject;

@Entity
public class ManagerEntity extends EmployeeEntity {
    private float factor;

    @Override
    protected void fromEmployeeDto(Employee empl) {
        super.fromEmployeeDto(empl);
        Manager manager = (Manager) empl;
        this.factor = manager.getFactor();
    }

    @Override
    protected void toJsonObject(JSONObject jsonObj) {
        super.toJsonObject(jsonObj);
        jsonObj.put("factor", this.factor);
    }

    @Override
    public int computeSalary() {
        return (int) (super.computeSalary() * factor);
    }
}
