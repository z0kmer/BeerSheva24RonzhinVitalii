let autoImages = [
    { src: "images/img01.jpeg", text: "txt/img01.txt", caption: "Ork" },
    { src: "images/img02.jpeg", text: "txt/img02.txt", caption: "Troll" },
    { src: "images/img03.jpeg", text: "txt/img03.txt", caption: "Elf" },
    { src: "images/img04.jpeg", text: "txt/img04.txt", caption: "Dwarf" },
    { src: "images/img04.jpeg", text: "txt/img05.txt", caption: "Human" }
  ];
  
  let autoIndex = 0;
  let autoChangeInterval;
  let inactivityTimeout;
  
  function changeImage(imageSrc, textFile, caption) {
    const mainImage = document.getElementById('main-image');
    // Плавное исчезновение
    mainImage.style.opacity = 0;
    setTimeout(() => {
      mainImage.src = imageSrc;
      mainImage.style.opacity = 1;
    }, 1000); // задержка 1 секунда для эффекта растворения
  
    // Загрузка текста из txt-файла
    fetch(textFile)
      .then(response => response.text())
      .then(text => {
        document.getElementById('image-text').innerText = text;
      })
      .catch(error => console.error("Ошибка загрузки файла:", error));
  
    // Обновляем подпись (caption)
    if (caption !== undefined) {
      document.querySelector('.image-title-box').innerText = caption;
    }
  
    resetAutoChange(); // Сброс автосмены при ручном выборе
  }
  
  function startAutoChange() {
    autoChangeInterval = setInterval(() => {
      let imgObj = autoImages[autoIndex];
      changeImage(imgObj.src, imgObj.text, imgObj.caption);
      autoIndex = (autoIndex + 1) % autoImages.length;
    }, 4000); // Автосмена каждые 4 секунды (учитывая 1-с fade)
  }
  
  function resetAutoChange() {
    clearTimeout(inactivityTimeout);
    clearInterval(autoChangeInterval);
    inactivityTimeout = setTimeout(startAutoChange, 3000); // Если мышь не двигается 3 секунды
  }
  
  // Отслеживание движения мыши для сброса таймера автосмены
  document.addEventListener("mousemove", resetAutoChange);
  resetAutoChange(); // Инициализируем при загрузке страницы
  