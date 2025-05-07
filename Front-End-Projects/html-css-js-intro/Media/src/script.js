function changeImage(imageSrc, textFile) {
  // Обновляем основное изображение
  document.getElementById('main-image').src = imageSrc;

  // Загружаем текст из указанного файла
  fetch(textFile)
    .then(response => response.text())
    .then(text => {
      document.getElementById('image-text').innerText = text;
    })
    .catch(error => console.error("Ошибка загрузки файла:", error));

  // Если окно просмотра скрыто, показываем его
  showViewer();
}

function showViewer() {
  // Убираем класс, который скрывает область просмотра
  document.getElementById('gallery-container').classList.remove("viewer-hidden");
}

function hideViewer() {
  // Добавляем класс, чтобы скрыть область просмотра (viewer-wrapper)
  document.getElementById('gallery-container').classList.add("viewer-hidden");
}
