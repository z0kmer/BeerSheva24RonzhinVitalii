export function myBind(thisArg) {
    const res =  (...arg ) => {
        return this.apply(thisArg, arg)
    }
    return res;
}