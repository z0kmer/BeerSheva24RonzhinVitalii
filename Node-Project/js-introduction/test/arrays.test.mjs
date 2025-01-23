import { describe, expect, it } from 'vitest';

describe ("meaning of spread operator for array and arguments", () => {
    it("finding maximal number from array", () => {
        const numbers = [1, 2, 3, 4];
        expect(Math.max(...numbers)).toBe(4);
    })
    it("pushing one array to another", () => {
        const array1 = [1, 2];
        const array2 = [3, 4];
        const expected = [1, 2, 3, 4];
        array1.push(...array2);
        expect(array1).toEqual(expected);
    })
    it("copying arrays using spread operator", () => {
        const array1 = [1, 2];
        const array2 = [...array1];
        const expected = [1, 2, 3, 4];
        expect(array2).not.toBe(array1);
        expect(array2).toEqual(array1);
    })
})