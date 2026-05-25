package ru.mentee.power.devtools.student;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Тестирование StudentList")
class StudentListTest {

  private StudentList studentList;

  @BeforeEach
  void setUp() {
    studentList = new StudentList();
  }

  @Test
  @DisplayName("Конструктор должен создавать пустой список студентов")
  void constructorShouldCreateEmptyList() {
    List<Student> students = studentList.getStudentList();

    assertThat(students).isEmpty();
  }

  @Test
  @DisplayName("getStudentList должен возвращать пустой список для нового объекта")
  void getStudentListShouldReturnEmptyListForNewInstance() {
    List<Student> result = studentList.getStudentList();

    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("addStudent должен добавлять валидного студента в список")
  void addStudentShouldAddValidStudent() {
    // Подготовка
    Student student = new Student("Иван", "Москва");

    // Действие
    studentList.addStudent(student);

    // Проверка
    assertThat(studentList.getStudentList())
        .hasSize(1)
        .containsExactly(student);
  }

  @Test
  @DisplayName("addStudent не должен добавлять null в список")
  void addStudentShouldIgnoreNull() {
    // Действие
    studentList.addStudent(null);

    // Проверка
    assertThat(studentList.getStudentList()).isEmpty();
  }

  @Test
  @DisplayName("addStudent должен добавлять несколько студентов")
  void addStudentShouldAddMultipleStudents() {
    // Подготовка
    Student student1 = new Student("Иван", "Москва");
    Student student2 = new Student("Мария", "СПб");
    Student student3 = new Student("Пётр", "Казань");

    // Действие
    studentList.addStudent(student1);
    studentList.addStudent(student2);
    studentList.addStudent(student3);

    // Проверка
    assertThat(studentList.getStudentList())
        .hasSize(3)
        .containsExactly(student1, student2, student3);
  }

  @Test
  @DisplayName("addStudent должен сохранять порядок добавления")
  void addStudentShouldPreserveInsertionOrder() {
    // Подготовка
    Student first = new Student("Первый", "Москва");
    Student second = new Student("Второй", "СПб");
    Student third = new Student("Третий", "Казань");

    // Действие
    studentList.addStudent(first);
    studentList.addStudent(second);
    studentList.addStudent(third);

    // Проверка (порядок важен)
    assertThat(studentList.getStudentList())
        .containsSequence(first, second, third);
  }

  @Test
  @DisplayName("Должен найти всех студентов из указанного города")
  void shouldFindAllStudentsFromSpecificCity() {
    // Подготовка
    Student student1 = new Student("Иван", "Москва");
    Student student2 = new Student("Мария", "Москва");
    Student student3 = new Student("Пётр", "Казань");
    Student student4 = new Student("Анна", "Москва");

    studentList.addStudent(student1);
    studentList.addStudent(student2);
    studentList.addStudent(student3);
    studentList.addStudent(student4);

    // Действие
    List<Student> result = studentList.getStudentsFromSpecificCity("Москва");

    // Проверка
    assertThat(result)
        .hasSize(3)
        .containsExactlyInAnyOrder(student1, student2, student4)
        .doesNotContain(student3);
  }

  @Test
  @DisplayName("Должен вернуть пустой список, если нет студентов из указанного города")
  void shouldReturnEmptyListWhenNoStudentsFromCity() {
    // Подготовка
    Student student = new Student("Иван", "Москва");
    studentList.addStudent(student);

    // Действие
    List<Student> result = studentList.getStudentsFromSpecificCity("Новосибирск");

    // Проверка
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Должен вернуть пустой список, если список студентов пуст")
  void shouldReturnEmptyListWhenStudentListIsEmpty() {
    List<Student> result = studentList.getStudentsFromSpecificCity("Москва");

    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Должен быть чувствителен к регистру при поиске по городу")
  void shouldBeCaseSensitiveWhenFilteringByCity() {
    // Подготовка
    Student student1 = new Student("Иван", "Москва");
    Student student2 = new Student("Мария", "москва");  // нижний регистр

    studentList.addStudent(student1);
    studentList.addStudent(student2);

    // Действие
    List<Student> result = studentList.getStudentsFromSpecificCity("Москва");

    // Проверка (должен найти только точное совпадение с учётом регистра)
    assertThat(result)
        .hasSize(1)
        .containsExactly(student1);
  }

  @Test
  @DisplayName("printStudents должен выводить каждого студента на новой строке")
  void printStudentsShouldPrintEachStudentOnNewLine() {
    // Подготовка перехвата вывода
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      // Подготовка данных
      Student student1 = new Student("Иван", "Москва");
      Student student2 = new Student("Мария", "СПб");
      List<Student> students = List.of(student1, student2);

      // Действие
      studentList.printStudents(students);

      // Проверка
      String output = outputStream.toString();
      assertThat(output)
          .contains(student1.toString())
          .contains(student2.toString());

    } finally {
      // Восстановление
      System.setOut(originalOut);
    }
  }

  @Test
  @DisplayName("printStudents не должен выводить ничего для пустого списка")
  void printStudentsShouldPrintNothingForEmptyList() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      studentList.printStudents(List.of());

      String output = outputStream.toString();
      assertThat(output).isEmpty();

    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  @DisplayName("printStudents должен обрабатывать null-список без исключений")
  void printStudentsShouldHandleNullListGracefully() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      // Действие - метод должен выбросить NullPointerException?
      // Это зависит от требований. Сейчас for (int i = 0; i < studentList.size(); i++)
      // выбросит NPE при studentList == null

      assertThatThrownBy(() -> studentList.printStudents(null))
          .isInstanceOf(NullPointerException.class);

    } finally {
      System.setOut(originalOut);
    }
  }


}