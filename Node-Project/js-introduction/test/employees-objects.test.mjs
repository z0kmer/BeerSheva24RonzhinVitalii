import { describe, expect, it } from "vitest";
import Employee from "../src/Employee.mjs";
import Manager from "../src/Manager.mjs";
import SalesPerson from "../src/SalesPerson.mjs";
import WageEmployee from "../src/WageEmployee.mjs";
describe("creating, getters, computeSalary tests", () => {
  const basicSalary = 10000;
  const department = "dep1";
  const id = 1;
  const wage = 100;
  const hours = 1;
  const percent = 0.1;
  const sales = 100000;
  const factor = 2;
  const employee = new Employee(id, department, basicSalary);
  const wageEmpl = new WageEmployee(id, department, basicSalary, wage, hours);
  const salesPerson = new SalesPerson(id, department, basicSalary, wage, hours, percent,sales);
  const manager = new Manager(id, department, basicSalary, factor);
  it("Employee test", () => {
    
    expect(employee.getBasicSalary()).toBe(basicSalary);
    expect(employee.computeSalary()).toBe(basicSalary);
    expect(employee.getDepartment()).toBe(department);
    expect(employee.getId()).toBe(id);
  });
  it("WageEmployee test", () => {
   
    expect(wageEmpl.getHours()).toBe(hours);
    expect(wageEmpl.computeSalary()).toBe(10100);
    expect(wageEmpl.getWage()).toBe(wage);
  });
  it("SalesPerson test", () => {
   
    expect(salesPerson.getPercent()).toBe(percent);
    expect(salesPerson.getSales()).toBe(sales);
    expect(salesPerson.computeSalary()).toBe(basicSalary + wage * hours + percent * sales / 100);
  })
  it("Manager test", () => {
  
    expect(manager.getFactor()).toBe(factor);
    expect(manager.computeSalary()).toBe(basicSalary * 2);
  })
  it ("Polymorphhism test", () => {
    const employees = [
        employee, wageEmpl, salesPerson, manager
    ]
    const budget = employees.reduce((b, e) => b + e.computeSalary(), 0);
    expect(budget).toBe(10000 + 10100 + 10200 + 20000)
  } )
  it("setPrototype", () => {
    const obj = {};
    Object.setPrototypeOf(obj, new Employee());
    const prototype = Object.getPrototypeOf(obj);
    expect(obj.getId()).toBe(0)
  })
});