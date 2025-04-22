const lights = {
    red: document.getElementById("red"),
    yellow: document.getElementById("yellow"),
    green: document.getElementById("green"),
};

// Массив состояний и их интервалы
const states = [
    { active: ["red"], interval: 2000 },
    { active: ["red", "yellow"], interval: 2000 },
    { active: ["green"], interval: 2000 },
];

let currentIndex = 0;

// Функция для обновления состояния светофора
function updateState() {
    Object.keys(lights).forEach(light => lights[light].className = "trafficLight");
    states[currentIndex].active.forEach(light => lights[light].classList.add(`trafficLight-${light}`));
    const nextIndex = (currentIndex + 1) % states.length;
    setTimeout(() => {
        currentIndex = nextIndex;
        updateState();
    }, states[currentIndex].interval);
}

// Запуск
updateState();
