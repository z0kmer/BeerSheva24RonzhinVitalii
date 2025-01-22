import { describe, expect, it, test } from "vitest";
import { myParseInt, myParseIntRadix } from "../conversion-functions-teacher.mjs";
test ("standard parseInt method with some wrong value", () => {
    expect(parseInt(10, 1)).toBeNaN();
})
test ("standard parseInt method with out radix", () => {
    expect(parseInt(10)).toBe(10);
})
test ("standard parseInt method with radix equaled null", () => {
    expect(parseInt(10, null)).toBeNaN();
})
test("swap primitives", () => {
  let a = 10;
  let b = 20;
  [a, b] = [b, a];
  expect(a).toBe(20);
  expect(b).toBe(10);
});
describe("equal operators", () => {
  it("simple equility operator ==", () => {
    expect(12 == "12").toBeTruthy();
  });
  it("strong equility operator ===", () => {
    expect(12 === "12").toBeFalsy();
  });
});
//Unit test is AAA - Arranging / Act / Assertion
describe("myParseInt test suit", () => {
  it("parameter is number 0", () => {
    expect(myParseInt(0)).toBe(parseInt(0));
  });
  it("parameter is object of class Number", () => {
    expect(myParseInt(new Number(3))).toBe(parseInt(new Number(3)));
  });
  it("reqular string with positive integer number", () => {
    const strNum = "12"; //Arranging
    const res = myParseInt(strNum) + 2; //Act
    expect(res).toBe(14); //Assertion
  });
  it("reqular string with negative integer number", () => {
    expect(myParseInt("-12")).toBe(-12);
  });
  it("string with  number following +", () => {
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
  it("string beginning with the space", () => {
    expect(myParseInt(" 12")).toBe(12);
  }),
    it("space in middle", () => {
      expect(myParseInt("12 35")).toBe(12);
    });
  it("first symbol is not a number", () => {
    expect(myParseInt("a1")).toBeNaN();
  });
  it("string begins from ++", () => {
    expect(myParseInt("++12")).toBeNaN();
  });
  it("space following -", () => {
    expect(myParseInt("- 12")).toBeNaN();
  });
});
describe("myParseIntRadix test", () => {
    it("36-th base number system", () => {
        let resExp = 15*36 + 35; //"fz"
        let resActual = myParseIntRadix("fz", 36);
        expect(resActual).toBe(resExp);
    });
    it("36-th base number system with capital letters", () => {
        let resExp = 15*36 + 35; //"fz" 
        let resActual = myParseIntRadix("FZ", 36);
        expect(resActual).toBe(resExp);
    });
    it("wrong radix value (less than 2)", () => {
        expect(myParseIntRadix("100", 1)).toBeNaN()
    })
    it("wrong radix value (greaater than 36)", () => {
        expect(myParseIntRadix("100", 37)).toBeNaN()
    })
    it("binary number system with ending up disallowed symbol", () => {
        expect(myParseIntRadix("1002", 2)).toBe(4);
    })
})