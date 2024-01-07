# Delivery service

Многопользовательский сервис доставки на дом. 

## Использованные технологии

- Java 21
- Maven
- Spring Boot, Spring Data JPA, Spring Security, Flyway
- БД PostgreSQL
- Docker - контейнеры, образы, написание Dockerfile, Docker Compose
- GitHub Actions

## Функционал Api

Spring Boot (Java) приложение, реализующее REST API для работы с пользователями, сессиями, создаваемыми при авторизации, и заказами. Авторизация построена на JWT. У пользователей есть свои роли и в соответствии со своей ролью они могут взаимодействовать с приложением 

Работа с пользователями:

- Регистрация
- Авторизация
- Logout

Работа с заказами:

- Создание
- Редактирование (статус, адрес)
- Пометка заказа как выполненного

## Docker
В рамках проекта с помощью GitHub Actions была автоматизирована сборка docker образов.

## Инструкция по запуску проекта на локальном компьютере

- Установить Docker на локальную машину — https://docs.docker.com/get-docker/
- Склонировать репозиторий delivery_service

```bash
git clone https://github.com/Burgoy/delivery_service.git
```

- Запустить приложение с помощью start.sh

```bash
bash start.sh
```

- Страница с документацией будет доступна по ссылке: http://localhost:9001/swagger-ui/index.html
