package telran.employees;

import java.util.List;

public interface Company {
    void addEmployee(Employee employee);
    void removeEmployee(long id);
    Employee getEmployee(long id);
    int getDepartmentSalaryBudget(String department);
    List<String> getDepartments();
    List<Manager> getManagersWithMostFactor();
}
