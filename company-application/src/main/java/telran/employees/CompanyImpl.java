package telran.employees;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telran.io.Persistable;

public class CompanyImpl implements Company, Persistable {
    private Map<Long, Employee> employees = new HashMap<>();

    @Override
    public void restoreFromFile(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Employee employee = Employee.getEmployeeFromJSON(line);
                addEmployee(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    @Override
    public void removeEmployee(long id) {
        employees.remove(id);
    }

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    @Override
    public int getDepartmentSalaryBudget(String department) {
        return employees.values().stream()
            .filter(e -> e.getDepartment().equals(department))
            .mapToInt(Employee::getSalary)
            .sum();
    }

    @Override
    public List<String> getDepartments() {
        // Получаем уникальные названия отделов
        return employees.values().stream()
            .map(Employee::getDepartment)
            .distinct()
            .toList();
    }

    @Override
    public List<Manager> getManagersWithMostFactor() {
        return employees.values().stream()
            .filter(e -> e instanceof Manager)
            .map(e -> (Manager) e)
            .sorted((m1, m2) -> Double.compare(m2.getFactor(), m1.getFactor()))
            .toList();
    }

    @Override
    public void saveToFile(String filePath) {
        // Логика сохранения в файл
    }
}
