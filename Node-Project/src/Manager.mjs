import Employee from "./Employee.mjs";
export default class Manager extends Employee {
    #factor;
    constructor(id, department, basicSalary, factor=0) {
        super(id, department, basicSalary);
        this.#factor = factor;
    }
    getFactor() {
        return this.#factor;
    }
    computeSalary() {
        return super.computeSalary() * this.#factor;
    }

}