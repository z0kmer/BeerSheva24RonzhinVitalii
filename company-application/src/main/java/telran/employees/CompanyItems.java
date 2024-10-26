package telran.employees;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class CompanyItems {
    // Константы для значений
    private static final long MIN_ID = 1;
    private static final long MAX_ID = 9999;
    private static final int MIN_BASIC_SALARY = 30000;
    private static final int MAX_BASIC_SALARY = 200000;
    private static final int MIN_WAGE = 100;
    private static final int MAX_WAGE = 500;
    private static final int MIN_HOURS = 10;
    private static final int MAX_HOURS = 200;
    private static final int MIN_PERCENT = 1;
    private static final int MAX_PERCENT = 100;
    private static final int MIN_SALES = 1000;
    private static final int MAX_SALES = 100000;
    private static final double MIN_FACTOR = 1.0;
    private static final double MAX_FACTOR = 10.0;
    private static final HashSet<String> DEPARTMENTS = new HashSet<>(List.of("QA", "Audit", "Development", "Management"));

    public static Item[] getItems(Company company) {
        return new Item[]{
            Item.of("Add Employee", io -> addEmployee(io, company)),
            Item.of("Display Employee Data", io -> displayEmployeeData(io, company)),
            Item.of("Fire Employee", io -> fireEmployee(io, company)),
            Item.of("Department Salary Budget", io -> departmentSalaryBudget(io, company)),
            Item.of("List of Departments", io -> listOfDepartments(io, company)),
            Item.of("Display Managers with Most Factor", io -> displayManagersWithMostFactor(io, company)),
            Item.ofExit()
        };
    }

    private static void addEmployee(InputOutput io, Company company) {
        Item[] items = new Item[]{
            Item.of("Hire Employee", i -> hireEmployee(i, company)),
            Item.of("Hire Wage Employee", i -> hireWageEmployee(i, company)),
            Item.of("Hire Sales Person", i -> hireSalesPerson(i, company)),
            Item.of("Hire Manager", i -> hireManager(i, company)),
            Item.ofExit()
        };
        Menu menu = new Menu("Add Employee", items);
        menu.perform(io);
    }

    private static void hireEmployee(InputOutput io, Company company) {
        long id = io.readLong("Enter employee ID", "Invalid ID");
        String name = io.readString("Enter employee name");
        String department = io.readStringOptions("Enter department", "Invalid department", DEPARTMENTS);
        int salary = io.readInt("Enter salary", "Invalid salary");
        Employee employee = new Employee(id, name, department, salary, LocalDate.now());
        company.addEmployee(employee);
        io.writeLine("Employee hired");
    }

    private static void hireWageEmployee(InputOutput io, Company company) {
        long id = io.readLong("Enter employee ID", "Invalid ID");
        String name = io.readString("Enter employee name");
        String department = io.readStringOptions("Enter department", "Invalid department", DEPARTMENTS);
        int wage = io.readInt("Enter wage", "Invalid wage");
        int hours = io.readInt("Enter hours", "Invalid hours");
        WageEmployee wageEmployee = new WageEmployee(id, name, department, wage, hours);
        company.addEmployee(wageEmployee);
        io.writeLine("Wage Employee hired");
    }

    private static void hireSalesPerson(InputOutput io, Company company) {
        long id = io.readLong("Enter employee ID", "Invalid ID");
        String name = io.readString("Enter employee name");
        String department = io.readStringOptions("Enter department", "Invalid department", DEPARTMENTS);
        int sales = io.readInt("Enter sales", "Invalid sales");
        double percent = io.readDouble("Enter percent", "Invalid percent");
        SalesPerson salesPerson = new SalesPerson(id, name, department, sales, percent);
        company.addEmployee(salesPerson);
        io.writeLine("Sales Person hired");
    }

    private static void hireManager(InputOutput io, Company company) {
        long id = io.readLong("Enter employee ID", "Invalid ID");
        String name = io.readString("Enter employee name");
        String department = io.readStringOptions("Enter department", "Invalid department", DEPARTMENTS);
        int salary = io.readInt("Enter salary", "Invalid salary");
        double factor = io.readDouble("Enter factor", "Invalid factor");
        Manager manager = new Manager(id, name, department, salary, factor);
        company.addEmployee(manager);
        io.writeLine("Manager hired");
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
