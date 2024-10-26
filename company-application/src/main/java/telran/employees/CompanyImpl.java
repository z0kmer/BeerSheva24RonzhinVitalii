package telran.employees;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import telran.io.Persistable;

public class CompanyImpl implements Company, Persistable {

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
        // Логика добавления сотрудника
    }

    @Override
    public void removeEmployee(long id) {
        // Логика удаления сотрудника
    }

    @Override
    public Employee getEmployee(long id) {
        // Логика получения сотрудника по ID
        return null;
    }

    @Override
    public int getDepartmentSalaryBudget(String department) {
        // Логика получения бюджета зарплат отдела
        return 0;
    }

    @Override
    public List<String> getDepartments() {
        // Логика получения списка отделов
        return null;
    }

    @Override
    public List<Manager> getManagersWithMostFactor() {
        // Логика получения списка менеджеров с наибольшим фактором
        return null;
    }

    @Override
    public void saveToFile(String filePath) {
        // Логика сохранения в файл
    }
}
