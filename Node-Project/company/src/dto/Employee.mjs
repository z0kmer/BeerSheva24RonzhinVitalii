export default class Employee {
    constructor(id, basicSalary, department) {
        this.id = id;
        this.basicSalary = basicSalary;
        this.department = department;
    }

    computeSalary() {
        return this.basicSalary;
    }

    static fromJSON(json) {
        return new Employee(json.id, json.basicSalary, json.department);
    }

    toJSON() {
        return {
            id: this.id,
            basicSalary: this.basicSalary,
            department: this.department,
            className: this.constructor.name
        };
    }
}
