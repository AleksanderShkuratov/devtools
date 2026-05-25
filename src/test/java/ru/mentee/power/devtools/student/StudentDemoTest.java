package ru.mentee.power.devtools.student;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тестирование StudentDemo")
class StudentDemoTest {

  private ByteArrayOutputStream outputStream;
  private PrintStream originalOut;

  @BeforeEach
  void setUp() {
    // Перехватываем System.out перед каждым тестом
    outputStream = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(outputStream));
  }

  @AfterEach
  void tearDown() {
    // Восстанавливаем System.out после каждого теста
    System.setOut(originalOut);
  }

  @Test
  @DisplayName("main должен вывести общий список из 5 студентов")
  void mainShouldPrintAllFiveStudents() {
    // Действие
    StudentDemo.main();

    // Получаем вывод
    String output = outputStream.toString();

    // Проверяем, что вывод содержит всех студентов
    assertThat(output)
        .contains("Петр", "Москва")
        .contains("Софья", "Новгород")
        .contains("Иван", "Ярославль")
        .contains("Владимир", "Ярославль")
        .contains("Ольга", "Тверь");
  }

  @Test
  @DisplayName("main должен вывести заголовок 'Общий список студентов'")
  void mainShouldPrintGeneralListHeader() {
    // Действие
    StudentDemo.main();

    String output = outputStream.toString();

    assertThat(output).contains("Общий список студентов:");
  }

  @Test
  @DisplayName("main должен вывести студентов из Ярославля")
  void mainShouldPrintStudentsFromYaroslavl() {
    // Действие
    StudentDemo.main();

    String output = outputStream.toString();

    // Должны быть оба студента из Ярославля
    assertThat(output)
        .contains("Иван", "Ярославль")
        .contains("Владимир", "Ярославль");
  }

  @Test
  @DisplayName("main должен вывести ровно 2 студентов из Ярославля")
  void mainShouldPrintExactlyTwoStudentsFromYaroslavl() {
    // Действие
    StudentDemo.main();

    String output = outputStream.toString();

    // Находим секцию со студентами из Ярославля
    String yaroslavlSection = output.substring(
        output.indexOf("Студенты из Ярославля:"),
        output.indexOf("------------------------------------------",
            output.indexOf("Студенты из Ярославля:"))
    );

    // Считаем количество строк со студентами (упрощённо)
    long studentsCount = yaroslavlSection.lines()
        .filter(line -> line.contains("Student"))
        .count();

    assertThat(studentsCount).isEqualTo(2);
  }

  @Test
  @DisplayName("main должен вывести разделители после каждого списка")
  void mainShouldPrintSeparators() {
    // Действие
    StudentDemo.main();

    String output = outputStream.toString();

    // Проверяем, что разделитель встречается дважды
    int separatorCount = output.split("------------------------------------------").length - 1;
    assertThat(separatorCount).isEqualTo(2);
  }

  @Test
  @DisplayName("main должен вывести заголовок 'Студенты из Ярославля'")
  void mainShouldPrintYaroslavlHeader() {
    // Действие
    StudentDemo.main();

    String output = outputStream.toString();

    assertThat(output).contains("Студенты из Ярославля:");
  }

  @Test
  @DisplayName("main не должен выводить студентов не из Ярославля в отфильтрованном списке")
  void mainShouldNotPrintNonYaroslavlStudentsInFilteredList() {
    // Действие
    StudentDemo.main();

    String output = outputStream.toString();

    // Находим часть вывода после фильтрации
    String yaroslavlPart = output.substring(output.indexOf("Студенты из Ярославля:"));

    // Проверяем, что нет студентов из других городов
    assertThat(yaroslavlPart)
        .doesNotContain("Москва")
        .doesNotContain("Новгород")
        .doesNotContain("Тверь");
  }

  @Test
  @DisplayName("main должен сохранить порядок добавления студентов")
  void mainShouldPreserveStudentOrder() {
    // Действие
    StudentDemo.main();

    String output = outputStream.toString();

    // Проверяем последовательность в общем списке
    int petrIndex = output.indexOf("Петр");
    int sofiaIndex = output.indexOf("Софья");
    int ivanIndex = output.indexOf("Иван");
    int vladimirIndex = output.indexOf("Владимир");
    int olgaIndex = output.indexOf("Ольга");

    // Порядок должен быть: Петр, Софья, Иван, Владимир, Ольга
    assertThat(petrIndex).isLessThan(sofiaIndex);
    assertThat(sofiaIndex).isLessThan(ivanIndex);
    assertThat(ivanIndex).isLessThan(vladimirIndex);
    assertThat(vladimirIndex).isLessThan(olgaIndex);
  }

  @Test
  @DisplayName("main должен вывести каждого студента на отдельной строке")
  void mainShouldPrintEachStudentOnNewLine() {
    // Действие
    StudentDemo.main();

    String output = outputStream.toString();

    // Считаем количество строк, содержащих "Student"
    long studentLines = output.lines()
        .filter(line -> line.contains("Student"))
        .count();

    // Всего 5 студентов в общем списке + 2 в отфильтрованном = 7 упоминаний
    assertThat(studentLines).isEqualTo(7);
  }
}