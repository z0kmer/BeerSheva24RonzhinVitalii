function createChessboard() {
    const chessboard = document.querySelector('.chessboard');
  
    // Размеры окна
    const width = window.innerWidth;
    const height = window.innerHeight;
    const boardSize = Math.min(width, height) * 0.9;
  
    // Размеры доски
    chessboard.style.width = `${boardSize}px`;
    chessboard.style.height = `${boardSize}px`;
  
    // Очищаем доску
    chessboard.innerHTML = '';
  
    const rows = 8, cols = 8;
  
    const cellSize = boardSize / rows;
  
    for (let row = 0; row < rows; row++) {
      for (let col = 0; col < cols; col++) {
        const cell = document.createElement('div');
        cell.classList.add('cell');
        cell.style.width = `${cellSize}px`;
        cell.style.height = `${cellSize}px`;
        if ((row + col) % 2 === 0) {
          cell.classList.add('white');
        } else {
          cell.classList.add('black');
        }
        chessboard.appendChild(cell);
      }
    }
  }
  
  window.onload = createChessboard;
  
  // Изменении размеров окна
  window.onresize = createChessboard;
  