console.log("script.js успешно загружен");

async function loadPage(url) {
  const placeholder = document.getElementById("content-placeholder");
  placeholder.innerHTML = "<p>Загрузка...</p>"; // Индикация загрузки

  try {
    const response = await fetch(url);
    if (response.ok) {
      const content = await response.text();
      placeholder.innerHTML = content;
      
      // Находим и выполняем все скрипты из загруженного контента
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

// По умолчанию показываем приветствие
window.addEventListener("DOMContentLoaded", () => {
  const placeholder = document.getElementById("content-placeholder");
  placeholder.innerHTML = `
    <p class="welcome">Добро пожаловать.<br> Выбери в меню, что тебе интересно.</p>
    <img src="welcome.jpg" alt="Welcome Image" class="welcome-img">
  `;
});
