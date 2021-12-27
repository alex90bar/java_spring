# MyBookShopApp
Описание проекта
MyBookShopApp - это учебный проект на Spring Boot, с использованием Thymeleaf и других технологий. Представляет собой книжный интернет-магазин.

Добавленные фичи:

Урок 4 задание 1:
Добавлены классы контроллеров для обработки перехода между всеми основными разделами магазина.

Урок 4 задание 2:
Выполнена локализация пользовательского интерфейса - реализована возможность смены языка с помощью инструментов Thymeleaf и класса конфигурации LocaleChangeConfig, который является бином интерфейса LocaleResolver. Обработка пользовательского действия выполняется на JavaScript и помещена в шаблон Thymeleaf.

Урок 5 задание 1:
Замена JDBC на JPA в AuthorService и BookService.

Урок 5 задание 3:
Подключена Liquibase для управления миграциями схем базы данных.

Скрипт для запуска приложения:

для run.cmd (win)

`@echo build maven project`

`call mvnw clean -Dmaven.test.skip package`

`@echo run project`

`call java -jar target/MyBookShopApp-0.0.1-SNAPSHOT.jar`

