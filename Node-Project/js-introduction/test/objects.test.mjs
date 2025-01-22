import { describe, expect, it } from "vitest";
import { getOccurencesObject } from "../objects.mjs";

describe('getOccurencesObject', () => {
    it('"abcadab"', () => {
        const result = getOccurencesObject('abcadab');
        expect(result).toEqual({
            a: 3,
            b: 2,
            c: 1,
            d: 1
        });
    });
    it('empty string', () => {
        const result = getOccurencesObject('');
        expect(result).toEqual({});
    });
    it('one character', () => {
        const result = getOccurencesObject('a');
        expect(result).toEqual({ a: 1 });
    });
    it('all unique characters', () => {
        const result = getOccurencesObject('abcd');
        expect(result).toEqual({
            a: 1,
            b: 1,
            c: 1,
            d: 1
        });
    });
    it('repeating characters', () => {
        const result = getOccurencesObject('aaabbbccc');
        expect(result).toEqual({
            a: 3,
            b: 3,
            c: 3
        });
        });
    });
    it('repeating characters with spaces', () => {
        const result = getOccurencesObject('aaa bbb ccc');
        expect(result).toEqual({
            a: 3,
            ' ': 2,
            b: 3,
            c: 3
        });
    });
    it('empty object for NaN input', () => {
        const result = getOccurencesObject(NaN);
        expect(result).toEqual({});
    });
    it('empty object for number input', () => {
        const result = getOccurencesObject(12345);
        expect(result).toEqual({});
    });
});
