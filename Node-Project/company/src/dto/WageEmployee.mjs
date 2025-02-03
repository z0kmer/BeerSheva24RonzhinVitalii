import Employee from './Employee.mjs';

export default class WageEmployee extends Employee {
    constructor(id, basicSalary, department, wage, hours) {
        super(id, basicSalary, department);
        this.wage = wage;
        this.hours = hours;
    }

    computeSalary() {
        return super.computeSalary() + this.wage * this.hours;
    }

    static fromJSON(json) {
        return new WageEmployee(json.id, json.basicSalary, json.department, json.wage, json.hours);
    }

    toJSON() {
        const json = super.toJSON();
        return {
            ...json,
            wage: this.wage,
            hours: this.hours
        };
    }
}
