/*
// numbers
//var - устарело, но то же, что и let
//let
//const
let a = 10; //объявляет переменную
a = 10 / 3;
let trunk;
a = Math.trunc(a); // округляет (типа round, но по своим правилам)
a **=2; //возвести в степень 2
const str = "12.35a"; //не изменная переменная
a = +str +2; //+ перед строкой пытается превратить её в число, если внутри нет букв
a = parseFloat(str, 8); //переводит число
a = a.toString(); //в строку переводит содержимое переменной

function square(num) {
    return num ** 2;
}
a = square(10);
str[1]='*';
for(let i = 0; i < str.length; i++) {
    console.log(str[i]);
}


// HW just for us
// написать функцию myIntParce a=parseInt("1abc");
// должен вернуть число 1.
// Если нет цифр, возвращать фразу "Not a nummber".

function myIntParce(a) {
    let result = '';
    let i = 0;

    while((/^[0-9]$/.test(a[i]) && i < a.length) || (a[i] && i == 0)) {
        result += a[i];
        i++
    }
    if (result == '' || result === '-') {
        result = "We don't have a digit"
    }
    console.log(result);
}

let dataForQuest = "123abc";
myIntParce(dataForQuest);

*/

// myParseInt(strNum) by teacher

function myParseInt(strNum) {
    let res = NaN;
    let sign = 1;
    if(strNum != null && strNum != undefined) {
        let index = 0;
        strNum = strNum.toString();
        strNum = strNum.trim(); // удаляем пробелы вначале и вконце строки
        if (strNum[0] == '-') {
            index++;
            sigh = -1;
        } else if (strNum[0] == '+') {
            index++;
        }
        if(index < strNum.length && !NaN(getDigit(strNum[index]))) {
            res = 0;
            let running = true;
            while(index < strNum.length && running) {
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
    return res *= sign;
}
function getDigit(digitStr) {
    let res = digitStr >= '0' && digitStr <= '9' ? +digitStr : NaN;
    return res;
}




