import { expect, test } from 'vitest';
import getOccurencesObject from '../objects.mjs';

test("getOccurencesObject with destructuring", () => {
    const str = "aaabgbgc";
    let {a, b, g, c} = getOccurencesObject(str);
    expect(a).toBe(3);
    expect(b).toBe(2);
    expect(g).toBe(2);
    expect(c).toBe(1);


})
test("string with digits, spaces and hyphens", () => {
    const str = "1,d-     ";
    const res = getOccurencesObject(str);
    expect(res.d).toBe(1);
    expect(res[1]).toBe(1);
    expect(res[' ']).toBe(5)
})
test ("test for oject as key inside another object", () => {
    const x = {x:5};
    x.toString = function() {
        return `x:${this.x}` //"x:" + this.x
    };
    const y = {y:10};
    const obj1 = {}
    obj1[x] = 200;
    const obj2 = obj1;
    obj2[y] = 300;
    expect(obj2[x]).toBe(200);
    expect(obj1["[object Object]"]).toBe(300);
    expect(obj1[{z:100}]).toBe(300);
    console.log("printing object using log",x);
    console.log("printing object using method toSTring",  x.toString());
    
  
  
    
})
