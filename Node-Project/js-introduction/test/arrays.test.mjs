import { describe, expect, it } from 'vitest';

// describe ("inserting new elments in array", () => {
//     const insertedNumbers = [-10, -20];
//     it("inserting at begining of array", () => {
//         const ar = [1, 2];
//         const expected = [-10, -20, 1, 2];
//         ar.unshift(...insertedNumbers);
//         expect(ar).toEqual(expected);
//     })
//     it("inserting at end of array", () => {
//         const ar = [1, 2];
//         const expected = [1, 2, -10, -20];
//         ar.push(...insertedNumbers);
//         expect(ar).toEqual(expected);
//     })
//     it("inserting at midle of array", () => {
//         const ar = [1, 2];
//         const expected = [1, -10, -20, 2];
//         ar.splice(1, 0, ...insertedNumbers);
//         expect(ar).toEqual(expected);
//     })
// })
describe ("removing elements from array", () => {
    const insertedNumbers = [-10, -20];
    it("removing from begining of array", () => {
        const ar = [1, 2, 3, 4];
        const expected = [2, 3, 4];
        ar.shift(ar);
        expect(ar).toEqual(expected);
    })
    it("removing from end of array", () => {
        const ar = [1, 2, 3, 4];
        const expected = [1, 2, 3];
        ar.pop(ar);
        expect(ar).toEqual(expected);
    })
    it("removing from midle of array", () => {
        const ar = [1, 2, 3, 4];
        const expected = [1, 4];
        ar.splice(1, 2);
        expect(ar).toEqual(expected);
    })
})