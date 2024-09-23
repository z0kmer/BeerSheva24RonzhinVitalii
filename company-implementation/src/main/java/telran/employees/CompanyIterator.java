package telran.employees;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class CompanyIterator implements Iterator<Employee> {
    private Iterator<Employee> iterator;
    private Employee current;
    private Map<Long, Employee> employees;
    private Map<String, List<Employee>> employeesDepartment;
    private Map<Float, List<Manager>> managersFactor;

    public CompanyIterator(Map<Long, Employee> employees, Map<String, List<Employee>> employeesDepartment, Map<Float, List<Manager>> managersFactor) {
        this.employees = employees;
        this.employeesDepartment = employeesDepartment;
        this.managersFactor = managersFactor;
        this.iterator = employees.values().iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Employee next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        current = iterator.next();
        return current;
    }

    @Override
    public void remove() {
        if (current == null) {
            throw new IllegalStateException();
        }
        Employee employee = current;
        current = null;
        iterator.remove();
        employees.remove(employee.getId());
        List<Employee> departmentEmployees = employeesDepartment.get(employee.getDepartment());
        if (departmentEmployees != null) {
            departmentEmployees.remove(employee);
            if (departmentEmployees.isEmpty()) {
                employeesDepartment.remove(employee.getDepartment());
            }
        }
        if (employee instanceof Manager) {
            Manager manager = (Manager) employee;
            List<Manager> managers = managersFactor.get(manager.getFactor());
            if (managers != null) {
                managers.remove(manager);
                if (managers.isEmpty()) {
                    managersFactor.remove(manager.getFactor());
                }
            }
        }
    }
}