package telran.employees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class CompanyImpl implements Company {
    private TreeMap<Long, Employee> employees = new TreeMap<>();
    private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
    private TreeMap<Float, List<Manager>> managersFactor = new TreeMap<>();

    @Override
    public Iterator<Employee> iterator() {
        return new CompanyIterator(employees, employeesDepartment, managersFactor);
    }

    @Override
    public void addEmployee(Employee empl) {
        if (employees.containsKey(empl.getId())) {
            throw new IllegalStateException("Employee with ID " + empl.getId() + " already exists.");
        }
        employees.put(empl.getId(), empl);
        employeesDepartment.computeIfAbsent(empl.getDepartment(), k -> new ArrayList<>()).add(empl);
        if (empl instanceof Manager) {
            Manager manager = (Manager) empl;
            managersFactor.computeIfAbsent(manager.getFactor(), k -> new ArrayList<>()).add(manager);
        }
    }

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    @Override
    public Employee removeEmployee(long id) {
        Employee employee = employees.remove(id);
        if (employee == null) {
            throw new NoSuchElementException("Employee with ID " + id + " not found.");
        }
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
        return employee;
    }

    @Override
    public int getDepartmentBudget(String department) {
        return employeesDepartment.getOrDefault(department, Collections.emptyList())
                .stream()
                .mapToInt(Employee::computeSalary)
                .sum();
    }

    @Override
    public String[] getDepartments() {
        return employeesDepartment.keySet().stream().sorted().toArray(String[]::new);
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        if (managersFactor.isEmpty()) {
            return new Manager[0];
        }
        List<Manager> managers = managersFactor.lastEntry().getValue();
        return managers.toArray(new Manager[0]);
    }
}