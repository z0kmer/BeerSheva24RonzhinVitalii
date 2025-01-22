let name = 'Vasya';
let age = 25;
const person = { name: 'Petya', age: 40 };
person.gender = 'male';
delete person.age;
let key = 'gender';
let field = person[key];
key = 'age';
person[key] = 20;
({ name, age } = person);
let { gender } = person;
let a;


export function getOccurencesObject(stringIn) {
    /*
        return object with data about occurence for each character in the given string
        'abcadab'
        data should include 'a' encounted 3 times
        'b' - two times
        'c' and 'd' - one time
        and write vitest-tests
    */

    const occurrences = {};
    
    if (typeof stringIn === 'string') {
        for (let char of stringIn) {
            occurrences[char] = (occurrences[char] || 0) + 1;
        }
    }
    return occurrences;
}