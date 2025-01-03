package telran.employees;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Main {
    private static final String FILE_NAME = "../../Databases/employees-data.csv";

    public static void main(String[] args) throws IOException {
        // Creates file CSV for importing data to the Employees table of DB
        // Example for each Employee type
        // 1,Employee, 15000,QA,,,,,
        // 2,Manager,12000,QA,2.0,,,,
        // 3,WageEmployee,12000,QA,,100,100,,
        // 4,SalesPerson,12000,QA,,100,100,0.1,100000

        List<String> lines = CompanyGenerator.getGenDataLines(ConfigurationProperties.getGenerationMap());
        try (PrintWriter writer = new PrintWriter(FILE_NAME);) {
            lines.forEach(writer::println);
        }

    }
}