import { describe, expect, it } from 'vitest';

describe ("inserting new elments in array", () => {
    const insertedNumbers = [-10, -20];
    it("inserting at begining of array", () => {
        const ar = [1, 2];
        const expected = [-10, -20, 1, 2];
        ar.unshift(...insertedNumbers);
        expect(ar).toEqual(expected);
    })
    it("inserting at end of array", () => {
        const ar = [1, 2];
        const expected = [1, 2, -10, -20];
        ar.push(...insertedNumbers);
        expect(ar).toEqual(expected);
    })
    it("inserting at midle of array", () => {
        const ar = [1, 2];
        const expected = [1, -10, -20, 2];
        ar.splice(1, 0, ...insertedNumbers);
        expect(ar).toEqual(expected);
    })
})