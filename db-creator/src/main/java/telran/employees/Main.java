package telran.employees;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("employees-sql-data.csv")) {
            writer.write("id,role,basic_salary,department,factor,wage,hours,percent,sales\n");

            // Generate employees for each department and role
            generateEmployees(writer, "QA", "Manager", 1);
            generateEmployees(writer, "QA", "Employee", 2);
            generateEmployees(writer, "QA", "WageEmployee", 10);

            generateEmployees(writer, "Development", "Manager", 1);
            generateEmployees(writer, "Development", "WageEmployee", 30);

            generateEmployees(writer, "Devops", "Manager", 1);
            generateEmployees(writer, "Devops", "Employee", 5);

            generateEmployees(writer, "Sales", "Manager", 1);

            generateEmployees(writer, "Sales", "SalesPerson", 3);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateEmployees(FileWriter writer, String department, String role, int count) throws IOException {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int id = random.nextInt(9000) + 1000;
            int basicSalary = random.nextInt(45000) + 5000;
            double factor = role.equals("Manager") ? random.nextDouble() * (4 - 1.5) + 1.5 : 0;
            int wage = role.equals("WageEmployee") || role.equals("SalesPerson") ? random.nextInt(271) + 30 : 0;
            int hours = role.equals("WageEmployee") || role.equals("SalesPerson") ? random.nextInt(200) + 1 : 0;
            double percent = role.equals("SalesPerson") ? random.nextDouble() * (1.5 - 0.1) + 0.1 : 0;
            int sales = role.equals("SalesPerson") ? random.nextInt(100000) + 1 : 0;

            writer.write(String.format("%d,%s,%d,%s,%.2f,%d,%d,%.2f,%d\n", id, role, basicSalary, department, factor, wage, hours, percent, sales));
        }
    }
}
