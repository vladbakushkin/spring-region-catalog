## Spring region catalog
### Описание
Приложение, реализующее справочник регионов, предоставляющее REST-API на чтение и изменение справочника.
Справочник храниться в in-memory БД H2, в качестве ORM применен MyBatis.
Для сокращения операций чтения из БД применен Spring Cache.

### Документация
Для доступа к документации необходимо запустить приложение и перейти по адресу:

http://localhost:8080/swagger-ui/index.html#/

### Системные требования и запуск
#### Системные требования
- Java 17
- Spring boot 3.3.3

#### Запуск
Для запуска необходимо склонировать репозиторий и запустить приложение из IDE или через командную строку.
Для тестирования приложения в корневой директории приложена коллекция postman-тестов.
