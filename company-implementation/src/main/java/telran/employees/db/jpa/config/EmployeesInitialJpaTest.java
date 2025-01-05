package telran.employees.db.jpa;

import java.util.HashMap;

import telran.employees.Company;
import telran.employees.db.CompanyDbImpl;
import telran.employees.db.CompanyRepository;

public class EmployeesInitialJpaTest {
    @Test
    void getEmployeesTest() {
        HashMap<String, Object> hibernateProperties = new HashMap<String, Object>(
            
        ) {
            {
                put("hibernate.hbm2ddl.auto", "update");
            }
        };
        CompanyRepository repository =
         new CompanyRepositoryJpaImpl(new EmployeesPersistenceUnitInfo(), hibernateProperties);
         Company company = new CompanyDbImpl(repository);
         company.forEach(System.out::println);

    }
}