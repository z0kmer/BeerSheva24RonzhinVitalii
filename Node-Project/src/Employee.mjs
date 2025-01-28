export default class Employee {
    #id;
    #department;
    #basicSalary;
    constructor(id=0,department=null, basicSalary=0) {
        this.#basicSalary = basicSalary;
        this.#department = department;
        this.#id = id;
    }
    computeSalary() {
        return this.#basicSalary;
    }
    getId() {
        return this.#id;
    }
    getBasicSalary() {
        return this.#basicSalary
    }
    getDepartment(){
        return this.#department
    }
}