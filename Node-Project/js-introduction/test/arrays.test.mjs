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
// describe ("removing elements from array", () => {
//     const insertedNumbers = [-10, -20];
//     it("removing from begining of array", () => {
//         const ar = [1, 2, 3, 4];
//         const expected = [2, 3, 4];
//         ar.shift(ar);
//         expect(ar).toEqual(expected);
//     })
//     it("removing from end of array", () => {
//         const ar = [1, 2, 3, 4];
//         const expected = [1, 2, 3];
//         ar.pop(ar);
//         expect(ar).toEqual(expected);
//     })
//     it("removing from midle of array", () => {
//         const ar = [1, 2, 3, 4];
//         const expected = [1, 4];
//         ar.splice(1, 2);
//         expect(ar).toEqual(expected);
//     })
// })
// //===============
// import { myMap, myReduce } from '../arrays.mjs';
// const array = [10, 1000, -10, 30, 60];
// describe("map / reduce", () => {
//     array.map = myMap;
//     array.reduce = myReduce;
//     it("myMap testing", () => {
//        const expected = [10, 1001, -8, 33, 64]//array of elements where each element will be converted to element + index
//        expect(array.map((e,index) => e + index)).toEqual(expected);
//     })
//     it("myReduce testing", () => {
//         //TODO
//         //write two reduce methods for taking sum of all array numbers
//         //and minimal and maximal values for only one reduce call
//           })
// })
//=========================================
const array = [10, 1000, -10, 30, 60];
describe('destructing array', () => {
    it('assigning two first numbers of of array to diffferent variables', () => {
        const [a,b] = array;
        expect(a).toBe(array[0]);
        expect(b).toBe(array[1]);
    })
    it('swap of values by using destructing', () => {
        let a = 10, b = 20;
        // const ar = [a, b];
        // [b, a] = ar;
        [b, a] = [a, b];
        expect(a).toBe(20);
        expect(b).toBe(10);
    })
})
