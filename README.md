# MyBookShopApp
Описание проекта
MyBookShopApp - это учебный проект на Spring Boot, с использованием Thymeleaf и других технологий. Представляет собой книжный интернет-магазин.

Добавленные фичи:

4.1. Добавлены классы контроллеров для обработки перехода между всеми основными разделами магазина.

4.2. Выполнена локализация пользовательского интерфейса - реализована возможность смены языка с помощью инструментов Thymeleaf и класса конфигурации LocaleChangeConfig, который является бином интерфейса LocaleResolver. Обработка пользовательского действия выполняется на JavaScript и помещена в шаблон Thymeleaf.

5.1. Замена JDBC на JPA в AuthorService и BookService.

5.3. Подключена Liquibase для управления миграциями схем базы данных.

6.1. Создана документация REST API для сущности Book. 
Реализован поиск по сайту и постраничный вывод результатов поиска.

6.5.1. Подключен механизм постраничного вывода книг в разделах "Новинки" и "Популярное" на главной странице.

6.5.2.1. Реализован функционал раздела "Новинки": 
- вывод списка книг, отсортированных по дате публикации
- возможность фильтрации списка по диапазону дат
- постраничный вывод контента

6.5.2.2. Реализован функционал раздела "Популярное", расчет популярности книги по формуле и постраничный вывод контента.

6.5.2.3. Реализован функционал сортировки книг по тегам, с учетом количества книг по каждому тегу, постраничный вывод контента.

6.5.2.4. Реализована необходимая логика и структура для работы раздела «Жанры», включая вывод списка книг, сформированный по выбранному пользователем жанру. 

6.5.3. Реализована страница автора, постраничный вывод книг в слайдере, и постраничный вывод полного списка книг по автору в отдельном окне.

7.1 Реализован механизм загрузки файла на сервер, обложка книги.

7.2 Реализован механизм скачивания книг с сервера, в разных форматах.

7.3 Реализован механизм обработки ошибок, создан GlobalExceptionHandlerController

7.4 Реализована возможность добавления книг в корзину и удаления, посредством добавления в cookies.

7.6.1 Реализована возможность добавления книг в "Отложенные", и удаления оттуда, с помощью cookies.

7.6.2 Реализован механизм добавления оценки книги и расчета рейтинга по книге, добавлена новая сущность в базу данных.

7.6.3 Реализован механизм добавления отзыва на книгу, возможность ставить отзыву лайки и дизлайки.

8.3 Подключена аутентификация через Spring Security Configuration.

8.5 Аутентификация переделана на JWT токены.

8.6 Добавлена возможность авторизации через OAuth.

8.7.1 Реализована обработка security-исключения истечения срока действия токена ExpiredJwtException.

8.7.2. Реализован механизм blacklist для невалидных JWT-токенов.

8.7.3. Доработана OAuth-авторизация через Facebook, реализована подгрузка данных пользователя из Facebook.

9.2 Написаны Unit-тесты для класса BookStoreUserRegister.

9.3 Написаны Integration-тесты для MainPageController с использованием MockMvc.

9.4 Написаны тесты для MainPage с помощью Selenium.

9.5 Написаны тесты уровня данных для классов BookRepository и BookstoreUserRepository.

11.1 Подключена вирификация номера телефона при регистрации нового пользователя через СМС-сообщение с помощью сервиса twilio.com

11.2 Подключена верификация почтового адреса при регистрации нового пользователя через почтовый сервис gmail.com

11.3 Подключен поиск через Google Books Api.

11.4 Подключена платежная система Robokassa, реализована возможность оплаты книг, добавленных в корзину.

11.6 Spring Actuator подключен, CLient и Admin

9.7.2 Написаны Unit-тесты для регистрации пользователя, авторизации и расчета популярности книг.

9.7.3 Написаны интеграционные тесты для регистрации нового пользователя, login по е-мэйлу/телефону, logout, добавления книги в корзину, удаления из корзины.


Скрипт для запуска приложения:

для run.cmd (win)

`@echo build maven project`

`call mvnw clean -Dmaven.test.skip package`

`@echo run project`

`call java -jar target/MyBookShopApp-0.0.1-SNAPSHOT.jar`

