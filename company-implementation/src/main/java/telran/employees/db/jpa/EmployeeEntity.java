package telran.employees.db.jpa;

import org.json.JSONObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import telran.employees.Employee;

@Entity
@Table(name="employees")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EmployeeEntity {
    @Id
    private long id;
    @Column(name="basic_salary")
    private int basicSalary;
    private String department;

    protected void fromEmployeeDto(Employee empl) {
        this.id = empl.getId();
        this.basicSalary = empl.getBasicSalary();
        this.department = empl.getDepartment();
    }

    protected void toJsonObject(JSONObject jsonObj) {
        jsonObj.put("id", this.id);
        jsonObj.put("basicSalary", this.basicSalary);
        jsonObj.put("department", this.department);
    }

    public int computeSalary() {
        return basicSalary;
    }
}
