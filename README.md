# Лабораторна робота №2 - Вебзастосунок для управління бібліотекою

## Опис проєкту

Вебзастосунок для управління бібліотекою з реалізацією CRUD операцій для книг.

## Технології

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Thymeleaf** (шаблонізатор)
- **Maven**

## Структура проєкту

```
lab2-angel/
├── src/
│   ├── main/
│   │   ├── java/ua/edu/kpi/library/
│   │   │   ├── LibraryWebApplication.java
│   │   │   ├── model/
│   │   │   │   ├── Book.java          # Основна сутність (CRUD)
│   │   │   │   └── Reader.java        # Допоміжна сутність (один-до-багатьох)
│   │   │   ├── repository/
│   │   │   │   ├── BookRepository.java
│   │   │   │   └── ReaderRepository.java
│   │   │   ├── service/
│   │   │   │   └── BookService.java
│   │   │   ├── controller/
│   │   │   │   └── BookController.java
│   │   │   └── config/
│   │   │       └── DatabaseInitializer.java
│   │   └── resources/
│   │       ├── templates/books/
│   │       │   ├── list.html          # Список книг
│   │       │   └── form.html          # Форма створення/редагування/перегляду
│   │       ├── static/css/
│   │       │   └── style.css
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Вимоги

- Java 17 або вище
- Maven 3.6+

## Запуск проєкту

### 1. Компіляція та запуск

```bash
cd lab2-angel
mvn clean spring-boot:run
```

### 2. Доступ до застосунку

Відкрийте браузер і перейдіть за адресою:
```
http://localhost:8080/books
```

### 3. H2 Console (для перегляду БД)

```
http://localhost:8080/h2-console
```

**Налаштування підключення:**
- JDBC URL: `jdbc:h2:mem:librarydb`
- Username: `sa`
- Password: (порожнє)

## Функціональність

### CRUD операції для книг:

1. **Create** - Створення нової книги
2. **Read** - Перегляд списку книг та детальної інформації
3. **Update** - Редагування існуючої книги
4. **Delete** - Видалення книги

### Сторінки:

1. **Список книг** (`/books`) - відображає всі книги з можливістю створення, редагування та видалення
2. **Форма книги** (`/books/new`, `/books/{id}`, `/books/{id}/edit`) - для створення, перегляду та редагування

## База даних

### Сутності:

- **Book** (основна сутність)
  - id (Long)
  - title (String)
  - author (String)
  - isbn (String)
  - reader (Reader) - зв'язок Many-to-One
  - isAvailable (Boolean)

- **Reader** (допоміжна сутність)
  - id (Long)
  - name (String)
  - email (String)
  - books (List<Book>) - зв'язок One-to-Many

### Зв'язки:

- Book → Reader: Many-to-One (багато книг на одного читача)
- Reader → Book: One-to-Many (один читач має багато книг)

## Початкові дані

При запуску застосунку автоматично створюються:
- 3 читачі (допоміжна сутність)
- 3 книги (для демонстрації)

## Тестування

```bash
mvn test
```

## Збірка JAR файлу

```bash
mvn clean package
java -jar target/library-web-1.0-SNAPSHOT.jar
```

## Особливості реалізації

- Валідація даних на рівні моделі
- Обробка помилок з коректними повідомленнями
- Повна інформація про читача на сторінці форми
- Автоматична ініціалізація БД з тестовими даними
- Сучасний та зручний інтерфейс


# lab2-angel
