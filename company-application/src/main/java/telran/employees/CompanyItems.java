package telran.employees;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import telran.view.InputOutput;
import telran.view.Item;

public class CompanyItems {

    public static Item[] getItems(Company company) {
        return new Item[]{
            Item.of("Add Employee", io -> addEmployee(io, company)),
            Item.of("Hire Employee", io -> hireEmployee(io, company)),
            Item.of("Hire Wage Employee", io -> hireWageEmployee(io, company)),
            Item.of("Hire Sales Person", io -> hireSalesPerson(io, company)),
            Item.of("Hire Manager", io -> hireManager(io, company)),
            Item.of("Display Employee Data", io -> displayEmployeeData(io, company)),
            Item.of("Fire Employee", io -> fireEmployee(io, company)),
            Item.of("Department Salary Budget", io -> departmentSalaryBudget(io, company)),
            Item.of("List of Departments", io -> listOfDepartments(io, company)),
            Item.of("Display Managers with Most Factor", io -> displayManagersWithMostFactor(io, company)),
            Item.ofExit()
        };
    }

    private static void addEmployee(InputOutput io, Company company) {
        long id = io.readLong("Enter employee ID", "Invalid ID");
        String name = io.readString("Enter employee name");
        String department = io.readStringOptions("Enter department", "Invalid department", new HashSet<>(List.of("QA", "Audit", "Development", "Management")));
        int salary = io.readInt("Enter salary", "Invalid salary");
        Employee employee = new Employee(id, name, department, salary, LocalDate.now());
        company.addEmployee(employee);
        io.writeLine("Employee added");
    }

    private static void hireEmployee(InputOutput io, Company company) {
        // Логика найма сотрудника
    }

    private static void hireWageEmployee(InputOutput io, Company company) {
        // Логика найма сотрудника с повременной оплатой
    }

    private static void hireSalesPerson(InputOutput io, Company company) {
        // Логика найма продавца
    }

    private static void hireManager(InputOutput io, Company company) {
        // Логика найма менеджера
    }

    private static void displayEmployeeData(InputOutput io, Company company) {
        long id = io.readLong("Enter employee ID", "Invalid ID");
        Employee employee = company.getEmployee(id);
        if (employee != null) {
            io.writeLine(employee);
        } else {
            io.writeLine("Employee not found");
        }
    }

    private static void fireEmployee(InputOutput io, Company company) {
        long id = io.readLong("Enter employee ID", "Invalid ID");
        company.removeEmployee(id);
        io.writeLine("Employee fired");
    }

    private static void departmentSalaryBudget(InputOutput io, Company company) {
        String department = io.readString("Enter department name");
        int budget = company.getDepartmentSalaryBudget(department);
        io.writeLine("Department salary budget: " + budget);
    }

    private static void listOfDepartments(InputOutput io, Company company) {
        List<String> departments = company.getDepartments();
        io.writeLine("Departments: " + departments);
    }

    private static void displayManagersWithMostFactor(InputOutput io, Company company) {
        List<Manager> managers = company.getManagersWithMostFactor();
        io.writeLine("Managers with most factor: " + managers);
    }
}
