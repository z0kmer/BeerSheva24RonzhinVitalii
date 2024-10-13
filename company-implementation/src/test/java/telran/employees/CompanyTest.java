package telran.employees;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.io.Persistable;




 class CompanyTest {
private static final long ID1 = 123;
private static final int SALARY1 = 1000;
private static final String DEPARTMENT1 = "QA";
private static final long ID2 = 120;
private static final int SALARY2 = 2000;
private static final long ID3 = 125;
private static final int SALARY3 = 3000;
private static final String DEPARTMENT2 = "Development";
private static final long ID4 = 200;
private static final String DEPARTMENT4 = "Audit";
private static final int WAGE1 = 100;
private static final int HOURS1 = 10;
private static final float FACTOR1 = 2;
private static final float PERCENT1 = 0.01f;
private static final long SALES1 = 10000;
private static final float FACTOR2 = 2.5f;
private static final long ID5 = 300;
private static final float FACTOR3 = 3;
private static final long ID6 = 400;
private static final long ID7 = 500;
Employee empl1 = new WageEmployee(ID1, SALARY1, DEPARTMENT1, WAGE1, HOURS1);
Employee empl2 = new Manager(ID2, SALARY2, DEPARTMENT1, FACTOR1);
Employee empl3 = new SalesPerson(ID3, SALARY3, DEPARTMENT2, WAGE1, HOURS1, PERCENT1, SALES1);
 Company company = new CompanyImpl();
@BeforeEach
void setCompany() {
	
	 for(Employee empl: new Employee[] {empl1, empl2, empl3}) {
		 company.addEmployee(empl);
	 };
}
	@Test
	void testAddEmployee()
	{
		Employee empl = new Employee(ID4, SALARY1, DEPARTMENT1);
		company.addEmployee(empl);
		assertThrowsExactly(IllegalStateException.class,
				() -> company.addEmployee(empl));
		assertThrowsExactly(IllegalStateException.class,
				() -> company.addEmployee(empl1));
	}

	@Test
	void testGetEmployee() {
		assertEquals(empl1, company.getEmployee(ID1));
		assertNull(company.getEmployee(ID4));
	}

	@Test
	void testRemoveEmployee() {
		assertEquals(empl1, company.removeEmployee(ID1));
		assertThrowsExactly(NoSuchElementException.class,
				() -> company.removeEmployee(ID1));
	}

	@Test
	void testGetDepartmentBudget() {
		assertEquals(SALARY1 + WAGE1 * HOURS1 + SALARY2 * FACTOR1, company.getDepartmentBudget(DEPARTMENT1));
		assertEquals(SALARY3 + WAGE1 * HOURS1 + PERCENT1 * SALES1 / 100, company.getDepartmentBudget(DEPARTMENT2));
		assertEquals(0, company.getDepartmentBudget(DEPARTMENT4));
	}

	@Test
	void testIterator() {
		runTestIterator(company);
	}
	private void runTestIterator(Company companyPar) {
		Employee[] expected = {empl2, empl1, empl3};
		Iterator<Employee> it = companyPar.iterator();
		int index = 0;
		while(it.hasNext()) {
			assertEquals(expected[index++], it.next());
		}
		assertEquals(expected.length, index);
		assertThrowsExactly(NoSuchElementException.class, it::next);
	}
	@Test
	void testGetDepartments() {
		String [] expected = {DEPARTMENT1, DEPARTMENT2};
		Arrays.sort(expected);
		assertArrayEquals(expected, company.getDepartments());
		expected = new String[] {DEPARTMENT1};
		company.removeEmployee(ID3);
		assertArrayEquals(expected, company.getDepartments());
	}
	@Test
	void testGetManagersWithMostFactor() {
		company.addEmployee(new Manager(ID4, SALARY1, DEPARTMENT1, FACTOR2));
		Manager[] managersExpected = {new Manager(ID5, SALARY1, DEPARTMENT1, FACTOR3),
		new Manager(ID6, SALARY1, DEPARTMENT1, FACTOR3),
		new Manager(ID7, SALARY1, DEPARTMENT2, FACTOR3)
		};
		for(Manager mng: managersExpected) {
			company.addEmployee(mng);
		}
		assertArrayEquals(managersExpected, company.getManagersWithMostFactor());
		company.removeEmployee(ID4);
		company.removeEmployee(ID5);
		company.removeEmployee(ID6);
		company.removeEmployee(ID7);
		assertArrayEquals(new Manager[] {(Manager) empl2}, company.getManagersWithMostFactor());
		company.removeEmployee(ID2);
		assertArrayEquals(new Manager[0],company.getManagersWithMostFactor());
		
	}
	@Test
		void iteratorRemoveTest() {
			Iterator<Employee> it = company.iterator();
			while(it.hasNext()) {
				Employee empl = it.next();
				if(empl.computeSalary() > 2000) {
					it.remove();
				}
			}
			assertThrowsExactly(IllegalStateException.class, it::remove);
			assertThrowsExactly(NoSuchElementException.class,
					() -> company.removeEmployee(ID2));
			assertThrowsExactly(NoSuchElementException.class,
					() -> company.removeEmployee(ID3));
			assertEquals(0, company.getDepartmentBudget(DEPARTMENT2));
			assertArrayEquals(new Manager[0], company.getManagersWithMostFactor());
			assertArrayEquals(new String[] {DEPARTMENT1}, company.getDepartments());
		}
		@Test
		void jsonTest() {
			Employee empl = Employee.getEmployeeFromJSON("{\"basicSalary\":1000,\"className\":\"telran.employees.Manager\",\"id\":123,\"department\":\"QA\",\"factor\":2}");
			assertEquals(empl, new Manager(ID1,SALARY1,DEPARTMENT1,FACTOR1));
		}
		@Test
		void persistenceTest() {
			if (company instanceof Persistable persCompany) {
				persCompany.saveToFile("company.data");
				CompanyImpl comp = new CompanyImpl();
				comp.restoreFromFile("company.data");
				runTestIterator(comp);
			}
		}
	
	

}