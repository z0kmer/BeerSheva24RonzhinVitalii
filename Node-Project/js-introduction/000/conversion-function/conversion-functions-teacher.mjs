const zeroCode = "0".charCodeAt(0);
const aCode = "a".charCodeAt(0);
const nineCode = "9".charCodeAt(0);
export function myParseIntRadix(strNum, radix) {
  //converting from string to number taking in consideration different number systems
  //radix is number of digits in the number system
  //radix is any number from 2 to 36 (0123456789... <all letters>)
  //if radix is undefined the digital number system is implied
  //examples: myParseIntRadix("10",8) -> 8;
  //myParseIntRadix("f",36) -> 15;
  //myParseIntRadix("z",36) -> 35;
  //myParseIntRadix("3",2) -> NaN;
  //myParseIntRadix("10103",2) -> 10;

  let res = NaN;
  let sign = 1;
  let actualRadix = getActualRadix(radix);
  if (strNum != null && strNum != undefined && !isNaN(actualRadix)) {
    let index = 0;
    strNum = strNum.toString();
    strNum = strNum.trim();
    strNum = strNum.toLowerCase();
    ({ sign, index } = signProcessing(strNum, index, sign));
    res = convertProcessing(index, strNum, res, radix);
  }
  return (res *= sign);
}
export function myParseInt(strNum) {
  return myParseIntRadix(strNum, 10);
}
function convertProcessing(index, strNum, res, radix) {
  if (index < strNum.length && !isNaN(getDigit(strNum[index],radix))) {
    res = 0;
    let running = true;
    while (index < strNum.length && running) {
      let digit = getDigit(strNum[index], radix);
      if (isNaN(digit)) {
        running = false;
      } else {
        res = res * radix + digit;
        index++;
      }
    }
  }
  return res;
}

function signProcessing(strNum, index, sign) {
  if (strNum[0] == "-") {
    index++;
    sign = -1;
  } else if (strNum[0] == "+") {
    index++;
  }
  return { index, sign };
}

function getDigit(digitStr, radix) {
  const code = digitStr.charCodeAt(0);
  const base = code > nineCode ? aCode - 10 : zeroCode;
 const res = code - base;
  return res > -1 && res < radix ? res : NaN;
}

export function myToStringFromIntNumber(number) {
  //TODO returns string presentation of the given number
  // if number has type of string, the string should contain a number matching the parseInt syntax
  // examples:
  //myToStringFromIntNumber(12.35) -> returns "12"
  //myToStringFromIntNumber("12.35") -> returns "12"
  //myToStringFromIntNumber() -> returns "" (empty string)
  //myToStringFromIntNumber(-12) -> returns "-12"
  //myToStringFromIntNumber(+12) -> return "12"
  //myToStringFromIntNum("a1") -> returns ""
  //myToStringFromInt("1a") -> returns "1"
  //Disallowed the following operations:
  //toString() using
  //constructor String
  //operator + with emty string like "" + for conversion purposes, but actual ccncatination od strings is a permitted operation
  //"" + number is disallowed but "" + string is allowed
  //"" + "1" permitted
  //"" + 1 is not so good
  // let res = "";
  // res + number % 10
}
function getActualRadix(radix) {
  let actualRadix = 10;
  if (radix !== undefined) {
    actualRadix = radix > 1 && radix < 37 ? radix : NaN;
  }
  return actualRadix;
}
function stringShift(str, shift) {
    //TODO
    //each character code insade string is increased on the shift value 'a' shifted on 3 will result character 'd'
    //'9' shifted on 2 will result ';'
    //if shifting causes exiting out of printable ASCII character there will by cycling from the begining
    //'9~9' shifted on 2 will result ';0;'
    //if the 'shift' is either a negative number or not a number, the given string should be return whith no updating 
    //'99' shifted on '-2' or 'asd' will result '99'
}
function stringUnshift(str, unshift) {
    //TODO
    //each character code insade string is dencreased on the unshift value 'd' unshifted on 3 will result character 'a'
    //';' unshifted on 2 will result '9'
    //if unshifting causes exiting out of printable ASCII character there will by cycling from the end
    //';0;' unshifted on 2 will result '9~9'
    //if the 'unshift' is either a negative number or not a number, the given string should be return whith no updating
    //'99' unshifted on '-2' or 'asd' will result '99'
}