import { describe, expect, it } from 'vitest';
import { myBind } from '../functions.mjs';

describe("myBind test according to the TODO comments", () => {
    const point = {x:3, y:4};
    function sumArguments(num3=0, num4=0) {
        return this.x + this.y + num3 + num4;
    }
    sumArguments.bind = myBind;
   // console.log('sumArguments string: ${sumArguments')
    const fun = sumArguments.bind(point);
    it("no additional parameters passed", () => {
        
        expect(fun()).toBe(7);
    })
    it(" all parameters are passed ", () => {
        expect(fun(10, 20)).toBe(37)
    })
})