package telran.employees;

import java.time.LocalDate;

import org.json.JSONObject;

public class Employee {
    private long id;
    private String name;
    private String department;
    private int salary;
    private LocalDate birthDate;

    public Employee(long id, String name, String department, int salary, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.birthDate = birthDate;
    }

    public static Employee getEmployeeFromJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            long id = jsonObject.getLong("id");
            String name = jsonObject.getString("name");
            String department = jsonObject.getString("department");
            int salary = jsonObject.getInt("salary");
            LocalDate birthDate = LocalDate.parse(jsonObject.getString("birthDate"));
            return new Employee(id, name, department, salary, birthDate);
        } catch (Exception e) {
            System.out.println("Invalid JSON: " + json);
            throw e;
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", birthDate=" + birthDate +
                '}';
    }
}
