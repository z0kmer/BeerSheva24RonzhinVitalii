console.log("script.js успешно загружен");

async function loadPage(url) {
  const placeholder = document.getElementById("content-placeholder");
  placeholder.innerHTML = "<p>Загрузка...</p>"; // Индикация загрузки

  try {
    const response = await fetch(url);
    if (response.ok) {
      const content = await response.text();
      placeholder.innerHTML = content;
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
    <p class="welcome">Добро пожаловать.<br> выбери в меню, что тебе интересно.</p>
    <img src="welcome.jpg" alt="Welcome Image" class="welcome-img">
  `;
});
