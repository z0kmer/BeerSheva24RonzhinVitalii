import WageEmployee from './WageEmployee.mjs';

export default class SalesPerson extends WageEmployee {
    constructor(id, basicSalary, department, wage, hours, percent, sales) {
        super(id, basicSalary, department, wage, hours);
        this.percent = percent;
        this.sales = sales;
    }

    computeSalary() {
        return super.computeSalary() + this.sales * this.percent / 100;
    }

    static fromJSON(json) {//not static...this is string
        return new SalesPerson(
            json.id, json.basicSalary, json.department,
            json.wage, json.hours, json.percent, json.sales
        );
    }

    toJSON() {
        const json = super.toJSON();
        return {
            ...json,
            percent: this.percent,
            sales: this.sales
        };
    }
}
