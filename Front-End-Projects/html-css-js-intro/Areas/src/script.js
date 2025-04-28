document.addEventListener("DOMContentLoaded", () => {
  console.log("DOMContentLoaded: Скрипт загружен");

  const table = document.getElementById("dynamic-table");
  if (!table) {
    console.error("Таблица с id='dynamic-table' не найдена.");
    return;
  }

  function generateTable() {
    const rows = 50;
    const cols = 50;
    table.innerHTML = "";
  
    for (let i = 0; i < rows; i++) {
      const tr = document.createElement("tr");
      for (let j = 0; j < cols; j++) {
        const td = document.createElement("td");
        const value = Math.random() > 0.5 ? 1 : 0;
        td.textContent = value;
        td.className = value === 1 ? "one" : "zero";
        tr.appendChild(td);
      }
      table.appendChild(tr);
    }
    console.log("Таблица обновлена: ", table);
  }  

  generateTable(); // Генерация таблицы при загрузке
  setInterval(generateTable, 1000); // Обновление таблицы каждые 2 секунды
});
