# Дипломный проект "Explore with me"

[Pull Request](https://github.com/KostayKapustin/java-explore-with-me/pull/2)

### Описание
Приложение в котором можно делиться информацией об интересных событиях и помогать найти компанию для участия в них.</br>

### Используемые технологии
Java 11, Spring Boot, Lombok, JPA, Hibernate, PostgreSQL, H2, Maven, Docker

### Составные части проекта

#### 1. Основной микросервис EWM-MAIN
Состоит из 2 частей:
1. **Приватная часть:** для зарегистрированных пользователей и администраторов сервиса. Позволяет создавать,
   обновлять, удалять, модерировать события. Добавлять, удалять пользователей. Управлять запросами на участие в событиях.
   Создавать, менять, удалять категории и подборки событий.
2. **Публичная часть:** для всех остальных пользователей, позволяет искать опубликованные события. Просматривать детальную
   информацию о событии по его id.

Запускается на порту 8080.</br>
Имеет отдельную Базу Данных.</br>
[Swagger спецификация основного сервиса](ewm-main-service-spec.json)</br>
[Postman тесты для основного сервиса](postman/ewm-main-service.json)</br></br>

#### Диаграмма БД основного сервиса
![](ewm-main/src/main/resources/ewm_main_db@localhost.png)

#### 2. Микросервис статистики EWM-STAT
Запускается на порту 9090.</br>
Имеет отдельную Базу Данных.</br>
[Swagger спецификация сервиса статистики](ewm-stats-service-spec.json)</br>
[Postman тесты для сервиса статистики](postman/ewm-stat-service.json)</br></br>
Позволяет сохранять и получать статистические данные о запросах на публичную часть основного сервиса.</br></br>
Сохраняет и предоставляет информацию о:
- адресах на которые поступали запросы
- ip адресах пользователей делающих запросы
- общем колличестве просмотров определенных событий
- колличестве просмотров с уникальных ip
- колличестве просмотров за определенный промежуток времени

#### Диаграмма БД сервиса статистики
![](ewm-stat/src/main/resources/ewm_status_db@localhost.png)

### Порядок запуска проекта
- **В ручном режиме:**
   - Запустить БД Основного сервиса
   - Запустить основной сервис
   - Запустить БД сервиса статистики
   - Запустить сервис статистики
- **В автоматическом режиме:**
   - Сборка всего проекта **maven -clean -install**
   - Общий запуск из корневой папки командой **docker compose up**

# Feature
В дополнение к техническому заданию в приложении реализована функция добавления комментариев к событиям и их модерации. 
Пулл-реквест можно посмотреть :[feature](https://github.com/KostayKapustin/java-explore-with-me/pull/4/commits/f2db7975f1301cf797874c1a4f54ab91589c269c).

### Административное API позволяет:

- удалять комментарии и получать.

### Приватное API позволяет:

- добавлять, получать, изменять и удалять комментарии.

### Схема обновлённой базы данных основного сервиса:
![](ewm-main/src/main/resources/ewm_main_db@localhost(feature).png)
