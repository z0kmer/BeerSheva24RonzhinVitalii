export default class MatrixComponent {
    #matrixChildElements //node list of all grid's children (all elements with class cell)
    #matrixStates
    #matrixElem
    constructor(rows, columns, matrixElem, matrixStates, interval=1000) {
       matrixElem.style.setProperty("grid-template-columns", `repeat(${columns}, 1fr)`);
       matrixElem.style.setProperty("grid-template-rows", `repeat(${rows}, 1fr)`);
       this.#matrixStates = matrixStates;
       this.#matrixElem = matrixElem;
       this.#initialMatrixCreate(rows, columns);
       setInterval(this.#tick.bind(this), interval);

    }
    #initialMatrixCreate(rows, columns){
        const matrixObj = this.#matrixStates.createRandomMatrix(rows, columns).flatMap(a => a);
        this.#matrixElem.innerHTML = getHtmlFromMatrixObj(matrixObj);
        this.#matrixChildElements = document.getElementsByClassName('cell');

    }
    #tick () {
        const matrixObj = this.#matrixStates.next().flatMap(a => a);
        matrixObj.forEach((n, index) => this.#matrixChildElements[index].style.backgroundColor = n ?
         "black" : "white")
    }
   
}
function getHtmlFromMatrixObj(matrixObj) {
    const res = matrixObj.map(n => `<div class="${`cell ${n ? "cell-alive" : "cell-dead"}`}" ></div>`);
    return res.join("");
}