export function myParseInt(strNum) {
    let res = NaN;
    let sign = 1;
    if (strNum != null && strNum != undefined) {
        let index = 0;

        strNum = strNum.toString();
        strNum = strNum.trim();
        if (strNum[0] == '-') {
            index++;
            sign = -1;
        } else if (strNum[0] == '+') {
            index++;
        }
        if (index < strNum.length && !isNaN(getDigit(strNum[index]))) {
            res = 0;
            let running = true;
            while (index < strNum.length && running) {
                let digit = getDigit(strNum[index]);
                if (isNaN(digit)) {
                    running = false;
                } else {
                    res = res * 10 + digit;
                    index++;
                }
            }
        }
    }
    return res * sign;
}

function getDigit(digitStr) {
    let res = digitStr >= '0' && digitStr <= '9' ? +digitStr : NaN;
    return res;
}

export function myToStringFromNumber(number) {
    // TODO returns string presentation of the given number
    // if number has type of string, the string should contain a number matching the parseInt syntax
    // examples:
    // myToStringFromIntNumber(12.35) -> returns "12"
    // myToStringFromIntNumber("12.35") -> returns "12"
    // myToStringFromIntNumber() -> returns (empty string)
    // myToStringFromIntNumber(-12) -> returns "-12"
    // myToStringFromIntNumber(+12) -> returns "12"
    // myToStringFromIntNumber("a1") -> returns ""
    // myToStringFromIntNumber("1a") -> returns "1"
    //
    //Disallowed the following operations^
    //toString()
    //constructor String
    //operator + with emty string like "" +

    let result = [];
    if (number == null || number == undefined) {
        result = '';
    } else {
        if (typeof number === 'string') {
            number = myParseInt(number);
        } if (isNaN(number)) {
            result = '';
        } else {
            let isNegative = number < 0; number = Math.abs(Math.floor(number));
            if (number === 0) {
                result = ['0'];
            } else {
                while (number > 0) {
                    let digit = number % 10;
                    result.unshift(getDigitString(digit));
                    number = Math.floor(number / 10);
                } if (isNegative) {
                    result.unshift('-');
                }
            }
        }
    }
    return Array.isArray(result) ? result.join('') : result;
}
function getDigitString(digit) {
    return String.fromCharCode(48 + digit);
}

function parseSign(strNum) {
    let sign = 1;
    let index = 0;

    if (strNum[0] === '-') {
        sign = -1;
        index++;
    } else if (strNum[0] === '+') {
        index++;
    }

    return { sign, index };
}

export function myParseIntRedix(strNum, radix) {
    //converting from string to number taking in consideration different number systems
    //redix is number of digits in the number system
    //redix is any number from 2 to 36 (0123456789...<all letters>)
    //if radix is under the digital number system is implied
    //examples: myParseIntRadix("10", 8) -> 8;
    //myParseIntRadix("z", 36) -> 35
    //myParseIntRadix("f", 36) -> 15;
    //myParseIntRadix("3", 2) -> NaN;
    //myParseIntRadix("1010", 2) -> 10;
    let result = 0;
    let valid = true;
    let sign = 1;

    if (typeof strNum === 'string' && typeof radix === 'number' && radix >= 2 && radix <= 36) {
        const digits = '0123456789abcdefghijklmnopqrstuvwxyz'.slice(0, radix);
        strNum = strNum.trim().toLowerCase();

        const { sign, index } = parseSign(strNum);

        for (let i = index;i < strNum.length && valid; i++) {
            const digit = strNum[i];
            const value = digits.indexOf(digit);

            if (value === -1) {
                valid = false;
            } else {
                result = result * radix + value;
            }
        }

        result = valid ? result * sign : NaN;
    } else {
        result = NaN;
    }

    return result;
}


export function stringShift(str, shift) {
    return (typeof shift !== 'number' || shift < 0 || isNaN(shift)) 
        ? str 
        : Array.from(str).map(char => shiftChar(char, shift)).join('');
}
export function stringUnshift(str, unshift) {
    return (typeof unshift !== 'number' || unshift < 0 || isNaN(unshift)) 
        ? str 
        : Array.from(str).map(char => shiftChar(char, -unshift)).join('');
}
function shiftChar(char, shift) {
    const minCharCode = 32;
    const maxCharCode = 126;
    const range = maxCharCode - minCharCode + 1;
    
    let newCharCode = char.charCodeAt(0) + (shift % range);
    if (newCharCode > maxCharCode) {
        newCharCode = minCharCode + (newCharCode - maxCharCode - 1);
    } else if (newCharCode < minCharCode) {
        newCharCode = maxCharCode - (minCharCode - newCharCode - 1);
    }
    return String.fromCharCode(newCharCode);
}



