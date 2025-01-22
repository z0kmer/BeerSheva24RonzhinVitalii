import { describe, expect, it } from 'vitest';
import { myParseInt, myParseIntRedix, myToStringFromNumber, stringShift, stringUnshift } from '../conversion-functions.mjs';

// Unit test is AAA - Arranging / Act / Assertion
describe("myParseInt test suit", () => {
    it("reqular string with integer number", () => {
        const strNum = "12"; // Arranging
        const res = myParseInt(strNum) + 2; // Act
        expect(res).toEqual(14); // Assertions
    });
    it("reqular string with negative integer number", () => {
        expect(myParseInt("-12")).toBe(-12);
    });
    it("reqular string with plus integer number", () => {
        expect(myParseInt("+12")).toBe(12);
    });
    it("undefined", () => {
        expect(myParseInt()).toBeNaN();
    });
    it("null", () => {
        expect(myParseInt(null)).toBeNaN();
    });
    it("float number inside a string", () => {
        expect(myParseInt("12.35")).toBe(12);
    });
    it("String beginning with the space", () => {
        expect(myParseInt(" 12")).toBe(12);
    });
    it("Space in middle", () => {
        expect(myParseInt("12 35")).toBe(12);
    });
    it("first symbol is not a number", () => {
        expect(myParseInt("a1")).toBeNaN();
    });
    it("String beginning with double plus", () => {
        expect(myParseInt("++12")).toBeNaN();
    });
    it("Space following", () => {
        expect(myParseInt("- 12")).toBeNaN();
    });
});

describe('myToStringFromNumber', () => {
    it('Floating', () => {
        expect(myToStringFromNumber(12.35)).toBe('12');
    });
    it('String with floating', () => {
        expect(myToStringFromNumber("12.35")).toBe('12');
    });
    it('Empty string', () => {
        expect(myToStringFromNumber()).toBe('');
    });
    it('Negative digit', () => {
        expect(myToStringFromNumber(-12)).toBe('-12');
    });
    it('With plus', () => {
        expect(myToStringFromNumber(+12)).toBe('12');
    });
    it('a1', () => {
        expect(myToStringFromNumber("a1")).toBe('');
    });
    it('1a"', () => {
        expect(myToStringFromNumber("1a")).toBe('1');
    });
    it('null input', () => {
        expect(myToStringFromNumber(null)).toBe('');
    });
    it('Zero', () => {
        expect(myToStringFromNumber(0)).toBe('0');
    });
    it('With space', () => {
        expect(myToStringFromNumber(" 12")).toBe('12');
    });
});

// test("standart parseInt method with some wrong value", () => {
//     expect(parseInt(10, 40).toBeNaN());
// });

describe('myParseIntRedix', () => {
    it('"10" in base 8 to 8', () => {
        expect(myParseIntRedix("10", 8)).toBe(8);
    });
    it('"z" in base 36 to 35', () => {
        expect(myParseIntRedix("z", 36)).toBe(35);
    });
    it('"f" in base 36 to 15', () => {
        expect(myParseIntRedix("f", 36)).toBe(15);
    });
    it('"3" in base 2', () => {
        expect(isNaN(myParseIntRedix("3", 2))).toBe(true);
    });
    it('"1010" in base 2 to 10', () => {
        expect(myParseIntRedix("1010", 2)).toBe(10);
    });
    it('"-ff1" in base 16 to -4081', () => {
        expect(myParseIntRedix("-ff1", 16)).toBe(-4081);
    });
    it('"+ff1" in base 16 to 4081', () => {
        expect(myParseIntRedix("+ff1", 16)).toBe(4081);
    });
    it('"xyz" in base 10', () => {
        expect(isNaN(myParseIntRedix("xyz", 10))).toBe(true);
    });
    it('Non-string input', () => {
        expect(isNaN(myParseIntRedix(123, 10))).toBe(true);
    });
    it('Invalid radix', () => {
        expect(isNaN(myParseIntRedix("123", 1))).toBe(true);
        expect(isNaN(myParseIntRedix("123", 37))).toBe(true);
    });
    it('Invalid simbol', () => {
        expect(isNaN(myParseIntRedix("123..", 4))).toBe(true);
        expect(isNaN(myParseIntRedix("..123", 4))).toBe(true);
        expect(isNaN(myParseIntRedix("12.3", 4))).toBe(true);
    });
});

describe('stringShift', () => {
    it('should shift "a" by 3 positions to "d"', () => {
        expect(stringShift('a', 3)).toBe('d');
    });
    it('should shift "9" by 2 positions to ";"', () => {
        expect(stringShift('9', 2)).toBe(';');
    });
    it('should shift "9~9" by 2 positions to ";0;"', () => {
        expect(stringShift('9~9', 2)).toBe(';!;');
    });
    it('should return the original string when shift is negative', () => {
        expect(stringShift('99', -2)).toBe('99');
    });
    it('should return the original string when shift is not a number', () => {
        expect(stringShift('99', 'asd')).toBe('99');
    });
    it('not a number', () => {
        expect(stringShift('asv', NaN)).toBe('asv');
    });
});
describe('stringUnshift', () => {
    it('should unshift "d" by 3 positions to "a"', () => {
        expect(stringUnshift('d', 3)).toBe('a');
    });
    it('should unshift ";" by 2 positions to "9"', () => {
        expect(stringUnshift(';', 2)).toBe('9');
    });
    it('should unshift ";0;" by 2 positions to "9.9"', () => {
        expect(stringUnshift(';0;', 2)).toBe('9.9');
    });
    it('should return the original string when unshift is negative', () => {
        expect(stringUnshift('99', -2)).toBe('99');
    });
    it('should return the original string when unshift is not a number', () => {
        expect(stringUnshift('99', 'asd')).toBe('99');
    });
});
describe('ShiftUnshift', () => {
    it('Big Shift', () => {
        const shiftedString = stringShift('abc', 1_000_000_000);
        const unshiftedString = stringUnshift(shiftedString, 1_000_000_000);

        expect(unshiftedString).toEqual('abc');
    });
});