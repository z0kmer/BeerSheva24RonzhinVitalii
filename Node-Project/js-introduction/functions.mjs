export function myBind(thisArg) {
    const sourceFun = this;
    
    return function() {
        return sourceFun.apply(thisArg, arguments)
    }
}