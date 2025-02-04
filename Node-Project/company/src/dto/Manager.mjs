import Employee from './Employee.mjs';

export default class Manager extends Employee {
    constructor(id, basicSalary, department, factor) {
        super(id, basicSalary, department);
        this.factor = factor;
    }

    computeSalary() {
        return super.computeSalary() * this.factor;
    }

    static fromJSON(json) {//not static...this is string
        return new Manager(json.id, json.basicSalary, json.department, json.factor);
    }

    toJSON() {
        const json = super.toJSON();
        return {
            ...json,
            factor: this.factor
        };
    }
}
