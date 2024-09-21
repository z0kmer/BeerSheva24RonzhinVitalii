package telran.employees;

public class Employee {
    private long id;
    private int basicSalary;
    private String department;
    public Employee(long id, int basicSalary, String department) {
        this.id = id;
        this.basicSalary = basicSalary;
        this.department = department;
    }
    public int computeSalary() {
        return basicSalary;
    }
    public long getId() {
        return id;
    }
    public String getDepartment() {
        return department;
    }
    @Override
    public boolean equals(Object obj) {
        //according to only id
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}