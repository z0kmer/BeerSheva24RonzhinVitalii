# Используйте базовый образ OpenJDK
FROM openjdk:17-jdk-alpine

# Установите рабочую директорию
WORKDIR /app

# Установите Maven
RUN apk add --no-cache maven

# Скопируйте файлы проекта в контейнер
COPY . .

# Соберите проект с использованием Maven
RUN mvn clean install

# Запустите приложение
CMD ["java", "-jar", "target/company-server-appl-1.0-SNAPSHOT.jar"]
