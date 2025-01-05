package telran.employees.db.jpa;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import telran.employees.Employee;
import telran.employees.Manager;
import telran.employees.SalesPerson;
import telran.employees.WageEmployee;

public class EmployeesMapperTest {
    Employee [] employees = {
        new Employee(123l, 10000, "DEP"),
        new Manager(123l, 10000, "DEP", 1.5f),
        new WageEmployee(123l, 10000, "DEP", 100, 100),
        new SalesPerson(123l, 10000, "DEP", 100, 100, 0.1f,100000),

    };

    private void runTest(Employee empl) {
        EmployeeEntity emplEntity = EmployeesMapper.toEmployeeEntityFromDto(empl);
        Employee emplFromEntity = EmployeesMapper.toEmployeeDtoFromEntity(emplEntity);
        assertEquals(empl.toString(), emplFromEntity.toString());
    }
    @Test
    void mapperTest () {
        Arrays.stream(employees).forEach(this::runTest);
    }
}