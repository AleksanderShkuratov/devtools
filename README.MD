**Quick Start**

[![Java CI with Checkstyle and JaCoCo](https://github.com/AleksanderShkuratov/devtools/actions/workflows/ci.yml/badge.svg)](https://github.com/AleksanderShkuratov/devtools/actions/workflows/ci.yml)

Как запускать Gradle-задачи из IDE
1. Открой View → Tool Windows → Gradle.
2. В дереве задач выбери 
   Tasks → application → run, 
   Tasks → build → build или Tasks → verification → test, запускай двойным кликом.
3. Альтернативно используй Ctrl + Ctrl (Run Anything)
   и введи gradle run или gradle test — IDEA выполнит команду без терминала.

**Packages**
Package (пакет) — это логическая группировка классов, которая предотвращает конфликты имён и упрощает навигацию.
Объявление package ru.mentee.power; в начале файла говорит компилятору: «класс принадлежит пространству имён ru.mentee.power»,
и IDE автоматически размещает файл в папке src/main/java/ru/mentee/power/. 
В Java имя класса формируется как «полное имя пакета + имя класса» (Fully Qualified Name, FQN): ru.mentee.power.devtools.ProgressDemo.

Зачем это нужно: Без пакетов два класса с одинаковым именем (например, Progress из модуля A и Progress из модуля B) 
столкнутся при импорте, и компилятор не сможет различить их. 
Пакет создаёт уникальную область видимости; правило обратного доменного имени (ru.mentee.power) 
гарантирует глобальную уникальность, так как доменное имя зарегистрировано владельцем.

Ограничения: Структура папок должна точно повторять имя пакета: если в коде написано package ru.mentee.power,
файл обязан лежать в src/main/java/ru/mentee/power/, иначе компилятор выдаст ошибку «package does not match directory». 
IntelliJ IDEA автоматически синхронизирует структуру при создании пакета через New → Package.

## Структура `MenteeProgress`

### Поля (record components)

| Поле | Тип | Пример значения | Описание |
|------|------|-----------------|-------------|
| `menteeName` | `String` | `"Александр"` | Имя участника программы |
| `sprintNumber` | `int` | `1`, `2`, `3` | Номер текущего спринта |
| `plannedHoursPerWeek` | `int` | `6`, `10`, `20` | Количество часов, которое участник планирует уделять в неделю |

### Метод-правило

| Метод | Возвращаемый тип | Логика | Результат |
|-------|------------------|--------|-----------|
| `readyForSprint()` | `boolean` | `plannedHoursPerWeek >= 3` | `true` — если часов ≥ 3, иначе `false` |

> 📌 **Пороговое значение:** 3 часа в неделю — минимальная нагрузка для допуска к спринту.
 
Ссылка на урок - https://mentee-power.xl.ru/learn/MCIneBj4KkyH-GIRCspFvA/theory

### Правило веток: feature/DVT-X

В Dev Tools оставляем master чистым, работу ведём в ветке feature/DVT-<номер>

### Git локальный цикл

1. Все файлы проекта и изменения хранятся в Рабочей директории (Working Directory).
Когда вы редактируете файл и сохраняете его, изменения сначала попадают сюда.

2. Те изменения из рабочей директории, которые хотите включить в следующий коммит необходимо поместить в Индекс (Staging Area).
Это промежуточная область. Для перемещения изменений из рабочей директории в индекс используется команда git add.
Изменения в рабочей директории, которые не были добавлены в индекс, не попадут в коммит.

3. Место на вашем компьютере, где хранится вся история ваших коммитов(снимков состояния проекта)
называется Локальный репозиторий (Local Repository). 
Команда git commit создает новый коммит, который фиксирует все изменения, находящиеся в индексе,
и добавляет его в историю локального репозитория.

## Сценарий ручной проверки DVT-6

### Запуск приложения
1. Откройте Gradle Tool Window (View → Tool Windows → Gradle)
2. Выполните: devtools → Tasks → application → run
3. Ожидаемый вывод в Run Tool Window:
   Суммарно: пройдено 25 из 36 уроков, осталось 11 уроков

### Запуск тестов
1. Откройте Gradle Tool Window
2. Выполните: devtools → Tasks → verification → test
3. Ожидаемый вывод: BUILD SUCCESSFUL, все тесты зелёные

### Отладка через Debug
1. Установите breakpoint на строке цикла while в ProgressTracker.calculateProgress
2. Запустите Debug: кликните правой кнопкой на main → Debug 'ProgressTracker.main()'
3. Используйте Step Over (F8) для прохождения итераций
4. Проверьте Variables: counter, remainingHours должны изменяться корректно
5. Используйте Evaluate Expression (Alt+F8): вычислите remainingLessons * 2
6. Ожидаемый результат Evaluate: 14 (для completedLessons=5, totalLessons=12)

### Что делать при ошибках
- Если вывод некорректен: проверьте логику цикла через Debug
- Если тесты красные: откройте вывод теста, найдите AssertionError, скорректируйте метод
- Если Debug не останавливается: убедитесь, что breakpoint установлен (красный кружок)

## Кодстайл-гайд проекта devtools

Проект следует правилам Google Java Style Guide с адаптацией.
Автоматическая проверка: ./gradlew checkstyleMain

### 1. Именование методов: camelCase

До:    public void add_student(Student s) { }
После: public void addStudent(Student student) { }

Почему: Java Convention требует camelCase для методов.
Источник: https://google.github.io/styleguide/javaguide.html#s5.3-camel-case

### 2. Пробелы после if/for/while

До:    if(condition) {
После: if (condition) {

Почему: улучшает читаемость, отделяет ключевое слово от выражения.
Источник: Oracle Code Conventions — Whitespace

### 3. Длина строки: максимум 120 символов

До:    public List getStudentsFromSpecificCityWithVeryLongName...
После: public List getStudentsByCity(String city) {

Почему: длинные строки затрудняют чтение в редакторе и при code review.
Источник: https://google.github.io/styleguide/javaguide.html#s4.4-column-limit

### 4. Порядок импортов

До:    import java.util.List; import java.util.ArrayList; import java.io.File;
После: import java.io.File; import java.util.ArrayList; import java.util.List;

Почему: алфавитный порядок упрощает поиск импортов.
Источник: IntelliJ IDEA → Code → Optimize Imports

### 5. Фигурные скобки для if

До:    if (condition) doSomething();
После: if (condition) { doSomething(); }

Почему: скобки обязательны даже для однострочных блоков.
Источник: https://google.github.io/styleguide/javaguide.html#s4.1.1-braces-always-used