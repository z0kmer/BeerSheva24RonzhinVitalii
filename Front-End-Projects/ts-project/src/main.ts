
type Person = {
    id: number;
    name: string;
    age: number;
    gender?: string;
    address?: {
        city: string;
        street: string;
    }
    education: string
}
function update<T> (obj: T, updater: Partial<T>): void {
    for(let key in updater) {
             obj[key] = updater[key]!
    }
}
function getOccurrencesObj(array: (string|number)[]): Record<string|number, number>{
     return array.reduce((acc: Record<string|number, number>, cur) => ({...acc, [cur]:
         acc[cur] ? ++acc[cur] : 1}), {})
}
function isAnagram(str1: string, anagram: string): boolean {
    let res: boolean = false;
    str1 = str1.toLowerCase();
    anagram = anagram.toLowerCase();
    if (str1.length === anagram.length && str1 !== anagram) {
        const letterOccurences: Record<string, number> = getOccurrencesObj(Array.from(str1));
        res = Array.from(anagram).every(letter => --letterOccurences[letter] > -1)
    }
    return res;
}
console.log(isAnagram("hello", "olleh"))
console.log(isAnagram("hello", "olllh"))
console.log(getOccurrencesObj([1,2,3,1,1,1,2]));
console.log(getOccurrencesObj(["lmn", "lmn"]))


