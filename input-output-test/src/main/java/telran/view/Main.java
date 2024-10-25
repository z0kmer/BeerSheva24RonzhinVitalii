package telran.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

record Employee(long id, String name, String department, int salary, LocalDate birthDate) {
}

public class Main {
    static InputOutput io = new StandardInputOutput();
    /*********************** */
    // For HW #35 constants
    final static int MIN_SALARY = 5000;
    final static int MAX_SALARY = 30000;
    final static String[] DEPARTMENTS = { "QA", "Audit", "Development", "Management" };
    // name should be at least 3 English letters; first - capital, others - lower
    // case
    final static long MIN_ID = 100000;
    final static long MAX_ID = 999999;
    final static int MIN_AGE = 18;
    final static int MAX_AGE = 70;

    /*********************************** */
    public static void main(String[] args) {
        readEmployeeBySeparateFields();
    }

    static void readEmployeeAsObject() {
        io.writeLine("\n");
        Employee empl = io.readObject("Enter employee data in the format:" +
                " <id>#<name>#<department>#<salary>#<yyyy-MM-DD> ",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new Employee(Long.parseLong(tokens[0]), tokens[1], tokens[2],
                            Integer.parseInt(tokens[3]), LocalDate.parse(tokens[4]));

                });
        io.writeLine("You are entered the following Employee data");
        io.writeLine(empl);
    }
       static  void readEmployeeBySeparateFields() {
        io.writeLine("\n");

        long id = io.readNumberRange(String.format("Enter ID value in the range [%d-%d]", MIN_ID, MAX_ID),
        "Wrong ID value", MIN_ID, MAX_ID).longValue();
        String name = io.readStringPredicate("Enter employee's name",
         "must be at least 3 English letter, first - capital, others-lower case",
          s -> s.matches("[A-Z][a-z]{2,}"));
          HashSet<String> departmentsSet = new HashSet<>(List.of(DEPARTMENTS));
        String department = io.readStringOptions("Enter department from " + departmentsSet, "Must be one out from " + departmentsSet, 
        departmentsSet);
        int salary = io.readNumberRange(String.format("Enter salary value in the range [%d-%d]", MIN_SALARY, MAX_SALARY),
        "Wrong salary value", MIN_SALARY, MAX_SALARY).intValue();
        LocalDate minBirthDate = getBirthDateFromAge(MAX_AGE);
        LocalDate maxBirthDate = getBirthDateFromAge(MIN_AGE);
        LocalDate birthdate = io.readIsoDateRange(String.format("Enter birthdate in the range [%s - %s]",
        minBirthDate, maxBirthDate), "Wrong birthdate",minBirthDate, maxBirthDate);
        //Enter ID, Enter name, Enter department, Enter salary, Enter birthdate
        Employee empl = new Employee(id, name, department, salary, birthdate);
        io.writeLine("You have entered employee:");
        io.writeLine(empl);
     }

    private static LocalDate getBirthDateFromAge(int age) {
        return LocalDate.now().minusYears(age);
    }
}