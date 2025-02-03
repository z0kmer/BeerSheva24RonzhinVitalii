import { beforeEach, describe, expect, it } from "vitest";
import Employee from "../src/dto/Employee.mjs";
import Manager from "../src/dto/Manager.mjs";
import SalesPerson from "../src/dto/SalesPerson.mjs";
import WageEmployee from "../src/dto/WageEmployee.mjs";
import Company from "../src/service/Company.mjs";

describe('Company', () => {
  let company;
  let empl1;
  let empl2;
  let empl3;

  beforeEach(() => {
    company = new Company();
    empl1 = new WageEmployee(123, 1000, 'QA', 100, 10);
    empl2 = new Manager(120, 2000, 'QA', 2);
    empl3 = new SalesPerson(125, 3000, 'Development', 100, 10, 0.01, 10000);
    company.addEmployee(empl1);
    company.addEmployee(empl2);
    company.addEmployee(empl3);
  });

  it('add employees', async () => {
    const empl4 = new Employee(200, 1000, 'QA');
    await company.addEmployee(empl4);
    expect(await company.getEmployee(200)).toEqual(empl4);
    await expect(company.addEmployee(empl4)).rejects.toThrow('Already exists employee 200');
    await expect(company.addEmployee(empl1)).rejects.toThrow('Already exists employee 123');
  });

  it('get employees', async () => {
    expect(await company.getEmployee(123)).toEqual(empl1);
    expect(await company.getEmployee(200)).toBeNull();
  });

  it('remove employees', async () => {
    expect(await company.removeEmployee(123)).toEqual(empl1);
    await expect(company.removeEmployee(123)).rejects.toThrow('Not found employee 123');
  });

  it('calculate department budget', async () => {
    expect(await company.getDepartmentBudget('QA')).toEqual(6000);
    expect(await company.getDepartmentBudget('Development')).toEqual(4001);
    expect(await company.getDepartmentBudget('Audit')).toEqual(0);
  });

  it('return all departments', async () => {
    expect(await company.getDepartments()).toEqual(['Development', 'QA']);
    await company.removeEmployee(125);
    expect(await company.getDepartments()).toEqual(['QA']);
  });

  it('get managers with the most factor', async () => {
    const manager = new Manager(300, 1000, 'QA', 3);
    await company.addEmployee(manager);
    expect(await company.getManagersWithMostFactor()).toEqual([manager]);
  });

  it('save and restore from file', async () => {
    await company.saveToFile('company.data');
    const newCompany = new Company();
    await newCompany.restoreFromFile('company.data');
    expect(await newCompany.getEmployee(123)).toEqual(empl1);
  });
});
