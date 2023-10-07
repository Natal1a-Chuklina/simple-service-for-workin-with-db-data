# Simple service for workin with db data
### Простой сервис для работы с данными из БД
Считывает файл с критериями и тип операции, исходя из которых извлекает необходимые данные из БД и формирует результат 
обработки в выходной файл, указанный пользователем.

## Содержание
- [Зависимости](#зависимости)
- [Использование](#использование)
- [Установка](#установка)

## Параметры
- Java 8
- Кодировка входного / выходного файла - UTF-8
- Формат файлов - JSON

## Зависимости
- [Picocli](https://picocli.info/)
- [Project Lombok](https://projectlombok.org/)
- [Logback](https://logback.qos.ch/)
- [Jackson](https://github.com/FasterXML/jackson)
- [PostgreSQL](https://www.postgresql.org/)
- [Apache Commons](https://commons.apache.org/)

## Установка
Для генерации исполняемого jar файла необходимо выполнить сборку проекта:

```sh
mvn package
```
В папке target появится .jar файл, содержащий необходимые зависимости.

## Использование
Запуск осуществляется командой:

```sh
java -jar .\simple-service-for-working-with-db-data-1.0.0.jar [args]
```
### Список обязательных аргументов:
> ### -i, --input 
> Файл, откуда будет производиться чтение критериев поиска и статистики.

> ### -o, --output 
> Файл, куда будет записан результат работы программы.

> ### -t, --type 
> Тип операции: search - поиск по критериям, stat - получение статистики за период.

### Список необязательных аргументов:
> ### -v,--verbose
> Флаг, отвечающий за вывод логов в консоль.

### Содержимое входного файла:
1. При получении статистики входной файл должен содержать две даты в формате "yyyy-MM-dd":
- startDate - дата начала сбора статистики
- endDate - дата конца сбора статистики
2. При выполнении поиска входной файл должен содержать список критериев поиска (criteria). Доступные критерии:
  - Поиск покупателей по фамилии (lastName)
  - Поиск покупателей, купивших товар с указанным названием (productName) не менее, чем введенное число раз (minTimes)
  - Поиск покупателей, у которых общая стоимость всех покупок попадает в указанный интервал (minExpenses и maxExpenses)
  - Поиск покупателей, купивших меньше всего товаров. Возвращает не более, чем указанное число покупателей (badCustomers)

### Файл конфигурации:
Рядом с исполняемым файлом должен находиться файл конфигурации с названием config.json, который должен содержать
данные для подключения к БД: url базы данных (dbUrl), имя пользователя (dbUsername), пароль (dbPassword).








