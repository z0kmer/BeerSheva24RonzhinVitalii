import { describe, expect, it } from 'vitest';

describe ("Object copying", () => {
    const person1 = {name: "Vasya", age: 25};
    it("showing example of referances assigment but not copy", () => {
        const person2 = person1;
        person2.gender = "mail";
        expect(person1.gender).toBe("mail");
    })
    it("copying using method assign of class Object", () => {
        const person2 = {...person1};
        person2.city = "Lod";
        expect(person1.city).toBeUndefined;
        person1.city = "Lod";
        expect(person1 != person2).toBeTruthy();
        expect(person2).toEqual(person1);
        expect(person2).not.toBe(person1);
    })

})