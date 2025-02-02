// // function sleep (timeout, callback) {
// //     let running = true;
// //     const start = new Date().getTime();
// //     while(new Date().getTime() - start < timeout){
// //         //setTimeout(() => running = false, timeout);
// //     }
// // }
// function sleep (timeout) {
//     return new Promise ((resolve, reject) => {
//         if(timeout < 0) {
//             reject ('timeout cannot be a negative')
//         } else if (timeout > 365 * 24 * 3600000) {
//             reject ('a promise for time greater than one year may not be provided')
//         } else {
//             setTimeout(() => resolve(), timeout)
//         }
//         //setTimeout(() => resolve(), timeout);
//     })
// }
// // const timeout = 5000;
// // const promise = sleep (timeout);
// // promise.then(() => console.log(`${timeout} has passed)`));
// // console.log(`waiting for ${timeout}`);
// const timeout = 1000;
// //sleep(timeout).then(() => console.log(`${timeout} has passed`)).catch(er => console.log(er));

// //==============
// function initialNumber() {
//     return 1;
// }
// function sum(num) {
//     return num + 1;
// }
// function displayNum (num) {
//     if(num > 3) {
//         throw 'the number cannot be greater than 3';
//     }
//     console.log(num);
// }
// // sleep (timeout).then(() => initialNumber()).then(num => sum(num))
// //             .then(num => displayNum(num)).catch(er => console.log(er));
// // async function summing(numbers) {
// //     await sleep(-1);
// //     let result = initialNumber();
// //     for (let num of numbers) {
// //         result = result + sum(result, num);
// //     }
// //     return result;
// // }
// let res = summing([1, 2]).then(num => displayNum(num));
// console.log('kukureku');
// console.log(1 + res); //'1[object Promise]'

// //?12

class Deferred {
    #functions
    constructor() {
        this.#functions = [];
    }
    then(fun) {
        this.#functions.push(fun);
    }
    resolve(value) {
        this.#functions.forEach(fun => value = fun(value));
    }
}
const d = new Deferred()
d.then(function(res){ console.log("1 ", res); return "a"; });

d.then(function(res){ console.log("2 ", res); return "b"; });

d.then(function(res){ console.log("3 ", res); return "c"; });
d.resolve('hello');
