// Получение элементов по их идентификаторам
const redElement = document.getElementById("red");
const yellowElement = document.getElementById("yellow");
const greenElement = document.getElementById("green");

// Переменная для отслеживания текущего состояния
let currentState = 0; // Начальное состояние: красный свет

// Функция для изменения состояния светофора
function changeState() {
    // Сбрасываем классы (серый цвет по умолчанию)
    redElement.className = "trafficLight";
    yellowElement.className = "trafficLight";
    greenElement.className = "trafficLight";

    // В зависимости от текущего состояния устанавливаем нужные цвета
    if (currentState === 0) {
        // Первое состояние: только красный свет
        redElement.classList.add("trafficLight-red");
    } else if (currentState === 1) {
        // Второе состояние: красный + желтый
        redElement.classList.add("trafficLight-red");
        yellowElement.classList.add("trafficLight-yellow");
    } else if (currentState === 2) {
        // Третье состояние: только зеленый свет
        greenElement.classList.add("trafficLight-green");
    }

    // Переход к следующему состоянию
    currentState = (currentState + 1) % 3; // Перемещение по кругу (0 -> 1 -> 2 -> 0)
}

// Запуск функции changeState каждые 2 секунды
setInterval(changeState, 2000);
