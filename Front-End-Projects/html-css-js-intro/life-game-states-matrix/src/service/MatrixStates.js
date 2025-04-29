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
    next(){
      this.#_matrix = this.createRandomMatrix(this.#_matrix.length, this.#_matrix[0].length);
      return this.#_matrix;
    }
  }