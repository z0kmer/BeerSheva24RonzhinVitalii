import { beforeEach, describe, expect, it } from "vitest";
import Employee from "../src/dto/Employee.mjs";
import Manager from "../src/dto/Manager.mjs";
import SalesPerson from "../src/dto/SalesPerson.mjs";
import WageEmployee from "../src/dto/WageEmployee.mjs";
import { EMPLOYEE_ALREADY_EXISTS, EMPLOYEE_NOT_FOUND, INVALID_EMPLOYEE_TYPE } from "../src/exceptions/exceptions.mjs";
import Company from "../src/service/Company.mjs";
const ID1 = 123;
const SALARY1 = 1000;
const DEPARTMENT1 = "QA";
const ID2 = 120;
const SALARY2 = 2000;
const ID3 = 125;
const SALARY3 = 3000;
const DEPARTMENT2 = "Development";
const ID4 = 200;
const DEPARTMENT4 = "Audit";
const WAGE1 = 100;
const HOURS1 = 10;
const FACTOR1 = 2;
const PERCENT1 = 0.01;
const SALES1 = 10000;
const FACTOR2 = 2.5;
const ID5 = 300;
const FACTOR3 = 3;
const ID6 = 400;
const ID7 = 500;
const empl1 = new WageEmployee(ID1, DEPARTMENT1, SALARY1,  WAGE1, HOURS1);
const empl2 = new Manager(ID2, DEPARTMENT1, SALARY2,  FACTOR1);
const empl3 = new SalesPerson(ID3, DEPARTMENT2, SALARY3,  WAGE1, HOURS1, PERCENT1, SALES1);
let company = new Company();
const employees = [empl1, empl2, empl3];
(async () => await fillCompany())();
async function fillCompany() {
    company = new Company();
    for await (const empl of employees ){
        company.addEmployee(empl);
    }
}
describe("tests of company updating", () => {
    beforeEach(async () => await fillCompany());
    it(" adding new Employee", async () => {
        const empl = new Employee(ID4, SALARY1, DEPARTMENT1);
		await company.addEmployee(empl);
       await expect( company.addEmployee(empl)).rejects.toThrowError(EMPLOYEE_ALREADY_EXISTS(ID4));
       await expect( company.addEmployee(2)).rejects.toThrow(INVALID_EMPLOYEE_TYPE(2))
    });
    it("removing existing Employee", async () => {
        await expect( company.removeEmployee(ID4)).rejects.toThrowError(EMPLOYEE_NOT_FOUND(ID4));
       await company.removeEmployee(ID1);
       await company.addEmployee(empl1);

    })

})
describe("tests of company non-updating", async () => {
    beforeEach(async() => await fillCompany());
    it("getting Employee object test", async () => {
        const empl = await company.getEmployee(ID1);
        expect(empl.getId()).toBe(ID1);
        expect(empl instanceof WageEmployee).toBeTruthy();
         expect(await company.getEmployee(ID4)).toBeNull();
    });
    it("getting Managers with maximal factor", async () => {
        await company.addEmployee(new Manager(ID4, DEPARTMENT1, SALARY1,  FACTOR2));
		const managersExpected = [new Manager(ID5, DEPARTMENT1, SALARY1,  FACTOR3),
		new Manager(ID6, SALARY1, DEPARTMENT1, FACTOR3),
		new Manager(ID7, SALARY1, DEPARTMENT2, FACTOR3)
        ];
		for await (const mng of managersExpected) {
			company.addEmployee(mng);
		}
        expect(await company.getManagersWithMostFactor()).toEqual(managersExpected);
		await company.removeEmployee(ID4);
		await company.removeEmployee(ID5);
		await company.removeEmployee(ID6);
		await company.removeEmployee(ID7);
        expect(await company.getManagersWithMostFactor()).toEqual([empl2]);
		await company.removeEmployee(ID2);
        expect(await company.getManagersWithMostFactor()).toEqual([]);
    });
    it("get all departments", async() => {
        expect(await company.getDepartments()).toEqual([DEPARTMENT2, DEPARTMENT1])
    });
    it("get department budget", async () => {
        expect(await company.getDepartmentBudget(DEPARTMENT1)).toBe(SALARY1 + WAGE1 * HOURS1 + SALARY2 * FACTOR1);
		
        expect(await company.getDepartmentBudget(DEPARTMENT2)).toBe(SALARY3 + WAGE1 * HOURS1 + PERCENT1 * SALES1 / 100);
        expect(await company.getDepartmentBudget(DEPARTMENT4)).toBe(0);
        
    });
});
describe("persistence tests",async() => {
    beforeEach(async() => await fillCompany());
    it("save / restore persistence test", async () => {
        if(company.saveToFile) {
            const fileName = "test.data";
            await company.saveToFile(fileName);
            const companyNew = new Company();
            await companyNew.restoreFromFile(fileName);
            expect(await companyNew.getEmployee(ID1)).toEqual(empl1);
            expect(await companyNew.getEmployee(ID2)).toEqual(empl2);
            expect(await companyNew.getEmployee(ID3)).toEqual(empl3);
            expect(await companyNew.getEmployee(ID4)).toBeNull();
            expect(await companyNew.getDepartmentBudget(DEPARTMENT1)).toBe(SALARY1 + WAGE1 * HOURS1 + SALARY2 * FACTOR1);
		
            expect(await companyNew.getDepartmentBudget(DEPARTMENT2)).toBe(SALARY3 + WAGE1 * HOURS1 + PERCENT1 * SALES1 / 100);
            expect(await companyNew.getDepartmentBudget(DEPARTMENT4)).toBe(0);


        }
    })
    
})
describe("async iterator test", () => {
    beforeEach(async() => await fillCompany());
    
    it("iterating all objects", async () => {
        await runTest([empl1, empl2, empl3], undefined, company)
    })
    it("iterating objects with basic salary greater than 1000", async () => {
        await runTest([empl2, empl3],e => e.getBasicSalary() > 1000, company)
    })
    it("iterating objects with basic salary less than 1000", async () => {
        await runTest([],e => e.getBasicSalary() < 1000, company)
     })

})
async function runTest(expected, predicate, company) {
    const comp = (e1, e2)=>e1.getId() - e2.getId();
    company.setPredicate(predicate);
        expected = expected.toSorted(comp);
        const actual = [];
        for await (const empl of company){
            actual.push(empl);
        }
        actual.sort(comp);
        expect(actual).toEqual(expected);

}
 