console.log("script.js успешно загружен");

/**
 * Функция для загрузки страниц в контейнер #content-placeholder.
 * Если URL содержит "life-game-states-matrix", используется <iframe>,
 * поскольку загруженная страница содержит полную HTML-разметку и бандл Vite.
 */
async function loadPage(url) {
  const placeholder = document.getElementById("content-placeholder");

  // Если URL содержит life-game-states-matrix, используем iframe для изоляции приложения
  if (url.includes("life-game-states-matrix")) {
    placeholder.innerHTML = `<iframe src="${url}" style="width: 100%; height: 100%; border: none;"></iframe>`;
    return;
  }

  placeholder.innerHTML = "<p>Загрузка...</p>"; // Индикация загрузки

  try {
    const response = await fetch(url);
    if (response.ok) {
      const content = await response.text();
      placeholder.innerHTML = content;
      
      // Выполняем скрипты, если они присутствуют в загруженной разметке
      const scripts = placeholder.querySelectorAll("script");
      scripts.forEach(oldScript => {
        const newScript = document.createElement("script");
        if (oldScript.src) {
          newScript.src = oldScript.src;
          newScript.defer = oldScript.defer;
        } else {
          newScript.textContent = oldScript.textContent;
        }
        document.body.appendChild(newScript);
        oldScript.parentNode.removeChild(oldScript);
      });
    } else {
      console.error(`Ошибка загрузки ${url}: ${response.status}`);
      placeholder.innerHTML = "<p>Ошибка загрузки. Попробуйте позже.</p>";
    }
  } catch (error) {
    console.error(`Ошибка запроса: ${error}`);
    placeholder.innerHTML = "<p>Ошибка запроса. Проверьте соединение с интернетом.</p>";
  }
}

// Отображение стартового контента материнской страницы
window.addEventListener("DOMContentLoaded", () => {
  const placeholder = document.getElementById("content-placeholder");
  placeholder.innerHTML = `
    <p class="welcome">Добро пожаловать.<br>Выберите, что вам интересно.</p>
    <img src="welcome.jpg" alt="Welcome Image" class="welcome-img">
  `;
});
