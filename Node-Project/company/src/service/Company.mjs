import fs from 'fs/promises';
import Employee from '../dto/Employee.mjs';
import Manager from '../dto/Manager.mjs';
import SalesPerson from '../dto/SalesPerson.mjs';
import WageEmployee from '../dto/WageEmployee.mjs';

export default class Company {
    #employees;
    #departments;
    
    constructor() {
        this.#employees = {};
        this.#departments = {};
    }

    async addEmployee(empl) {
        if (this.#employees[empl.id]) {
            throw new Error(`Already exists employee ${empl.id}`);
        }
        this.#employees[empl.id] = empl;
        if (!this.#departments[empl.department]) {
            this.#departments[empl.department] = [];
        }
        this.#departments[empl.department].push(empl);
    }

    async getEmployee(id) {
        return this.#employees[id] || null;
    }

    async removeEmployee(id) {
        const empl = this.#employees[id];
        if (!empl) {
            throw new Error(`Not found employee ${id}`);
        }
        delete this.#employees[id];
        const deptEmployees = this.#departments[empl.department];
        const index = deptEmployees.indexOf(empl);
        if (index !== -1) {
            deptEmployees.splice(index, 1);
            if (deptEmployees.length === 0) {
                delete this.#departments[empl.department];
            }
        }
        return empl;
    }

    async getDepartmentBudget(department) {
        return (this.#departments[department] || []).reduce((sum, empl) => sum + empl.computeSalary(), 0);
    }

    async getDepartments() {
        return Object.keys(this.#departments).sort();
    }

    async getManagersWithMostFactor() {
        const managers = [];
        for (const empl of Object.values(this.#employees)) {
            if (empl instanceof Manager) {
                managers.push(empl);
            }
        }
        const maxFactor = Math.max(...managers.map(m => m.factor), 0);
        return managers.filter(m => m.factor === maxFactor);
    }

    async saveToFile(fileName) {
        await fs.writeFile(fileName, JSON.stringify(this.#employees, null, 2));
    }

    async restoreFromFile(fileName) {
        const data = await fs.readFile(fileName, 'utf-8');
        const employees = JSON.parse(data);
        for (const id in employees) {
            const employeeData = employees[id];
            let empl;
            switch (employeeData.className) {
                case 'WageEmployee':
                    empl = WageEmployee.fromJSON(employeeData);
                    break;
                case 'Manager':
                    empl = Manager.fromJSON(employeeData);
                    break;
                case 'SalesPerson':
                    empl = SalesPerson.fromJSON(employeeData);
                    break;
                default:
                    empl = Employee.fromJSON(employeeData);
            }
            await this.addEmployee(empl);
        }
    }
}
