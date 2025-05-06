function changeImage(imageSrc, textFile) {
  document.getElementById('main-image').src = imageSrc;

  fetch(textFile)
    .then(response => response.text())
    .then(text => {
      document.getElementById('image-text').innerText = text;
    })
    .catch(error => console.error("Ошибка загрузки файла:", error));
}
