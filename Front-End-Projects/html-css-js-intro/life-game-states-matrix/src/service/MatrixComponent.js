export default class MatrixComponent {
    #matrixChildElements;
    #matrixStates;
    #matrixElem;
  
    constructor(rows, columns, matrixElem, matrixStates, interval = 1000) {
      this.#matrixElem = matrixElem;
      this.#matrixStates = matrixStates;
  
      // Задаём параметры CSS-сетки
      matrixElem.style.setProperty("grid-template-columns", `repeat(${columns}, 1fr)`);
      matrixElem.style.setProperty("grid-template-rows", `repeat(${rows}, 1fr)`);
  
      // Создаём стартовую матрицу и генерируем HTML для ячеек
      this.#initialMatrixCreate(rows, columns);
  
      // Запускаем обновление матрицы с заданным интервалом
      setInterval(this.#tick.bind(this), interval);
    }
  
    #initialMatrixCreate(rows, columns) {
      const matrixObj = this.#matrixStates.createRandomMatrix(rows, columns).flat();
      this.#matrixElem.innerHTML = getHtmlFromMatrixObj(matrixObj);
      this.#matrixChildElements = document.getElementsByClassName('cell');
    }
  
    #tick() {
      const matrixObj = this.#matrixStates.next().flat();
      matrixObj.forEach((n, index) => {
        this.#matrixChildElements[index].style.backgroundColor = n ? "black" : "white";
      });
    }
  }
  
  function getHtmlFromMatrixObj(matrixObj) {
    const res = matrixObj.map(
      n => `<div class="cell ${n ? "cell-alive" : "cell-dead"}"></div>`
    );
    return res.join("");
  }
  