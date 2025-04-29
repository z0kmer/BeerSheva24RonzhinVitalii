export default class MatrixStates {
  #_matrix;

  constructor(matrix) {
    this.#_matrix = matrix ?? [];
  }

  createRandomMatrix(rows, columns) {
    this.#_matrix = Array.from({ length: rows }, () =>
      Array.from({ length: columns }, () => Math.floor(Math.random() * 2))
    );
    return this.#_matrix;
  }

  /**
   * Вычисляет следующее поколение матрицы по правилам Conway’s Game of Life.
   * Правила:
   * - Живая клетка с <2 или >3 живыми соседями умирает.
   * - Живая клетка с 2 или 3 живыми соседями остаётся живой.
   * - Мёртвая клетка с ровно 3 живыми соседями становится живой.
   *
   * @returns {number[][]} новое поколение матрицы
   */
  next() {
    const numRows = this.#_matrix.length;
    const numCols = this.#_matrix[0].length;
    const newMatrix = Array.from({ length: numRows }, () =>
      Array(numCols).fill(0)
    );

    for (let i = 0; i < numRows; i++) {
      for (let j = 0; j < numCols; j++) {
        let liveNeighbors = 0;

        // Перебираем 8 соседей
        for (let x = i - 1; x <= i + 1; x++) {
          for (let y = j - 1; y <= j + 1; y++) {
            // Пропускаем саму клетку
            if (x === i && y === j) continue;
            // Проверяем, что индекс не выходит за границы
            if (x >= 0 && x < numRows && y >= 0 && y < numCols) {
              liveNeighbors += this.#_matrix[x][y];
            }
          }
        }

        // Применяем правила игры «Жизнь»
        if (this.#_matrix[i][j] === 1) {
          if (liveNeighbors < 2 || liveNeighbors > 3) {
            newMatrix[i][j] = 0;
          } else {
            newMatrix[i][j] = 1;
          }
        } else {
          if (liveNeighbors === 3) {
            newMatrix[i][j] = 1;
          }
        }
      }
    }

    this.#_matrix = newMatrix;
    return newMatrix;
  }
}
