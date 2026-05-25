package ru.mentee.power.devtools.progress;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование ProgressTracker")
class ProgressLoopTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("main должен вывести корректный суммарный прогресс для трёх mentee")
    void mainShouldPrintCorrectTotalProgressForThreeMentees() {
        // Действие
        ProgressTracker.main();

        // Получаем вывод
        String output = outputStream.toString().trim();

        // Ожидаемый результат: 5+8+12 = 25 пройдено, 12+12+12 = 36 всего, осталось 11
        assertThat(output)
            .isEqualTo("Суммарно: пройдено 25 из 36 уроков, осталось 11 уроков");
    }

    @Test
    @DisplayName("main должен вывести строку, начинающуюся со 'Суммарно: пройдено'")
    void mainShouldStartWithExpectedPrefix() {
        ProgressTracker.main();

        String output = outputStream.toString().trim();

        assertThat(output).startsWith("Суммарно: пройдено");
    }

    @Test
    @DisplayName("main должен вывести строку, содержащую числа 25, 36 и 11")
    void mainShouldContainCorrectNumbers() {
        ProgressTracker.main();

        String output = outputStream.toString();

        assertThat(output)
            .contains("25")
            .contains("36")
            .contains("11");
    }

    @Test
    @DisplayName("main не должен выводить пустую строку или null")
    void mainShouldNotPrintEmptyOrNull() {
        ProgressTracker.main();

        String output = outputStream.toString();

        assertThat(output).isNotBlank();
        assertThat(output).doesNotContain("null");
    }

    @Test
    @DisplayName("main должен выводить результат на отдельной строке")
    void mainShouldPrintOnNewLine() {
        ProgressTracker.main();

        String output = outputStream.toString();

        assertThat(output).endsWith("\n");
    }

    @Test
    @DisplayName("Должен корректно вычислить суммарный прогресс когда передан массив mentee")
    void shouldCalculateTotalProgress_whenMultipleMentees() {
        // given - подготовка данных
        ProgressTracker tracker = new ProgressTracker();
        Mentee[] mentees = {
                new Mentee("Иван", "Москва", "Backend разработка", 5, 12),
                new Mentee("Мария", "Санкт-Петербург", "Fullstack", 8, 12),
                new Mentee("Пётр", "Казань", "Java Backend", 12, 12)
        };

        // when - выполнение действия
        String result = tracker.calculateTotalProgress(mentees);

        // then - проверка результата с assertJ
        assertThat(result)
                .contains("пройдено 25 из 36 уроков")
                .contains("осталось 11 уроков");
    }

    @Test
    @DisplayName("Должен корректно обработать массив когда все mentee завершили курс")
    void shouldCalculateTotalProgress_whenAllMenteesCompleted() {
        // given
        ProgressTracker tracker = new ProgressTracker();
        Mentee[] mentees = {
                new Mentee("Иван", "Москва", "Backend", 12, 12),
                new Mentee("Мария", "СПб", "Fullstack", 12, 12)
        };

        // when
        String result = tracker.calculateTotalProgress(mentees);

        // then
        assertThat(result)
                .contains("пройдено 24 из 24 уроков")
                .contains("осталось 0 уроков");
    }

    @Test
    @DisplayName("Должен выдать сообщение при валидации массива равного null")
    void shouldCalculateTotalProgress_validateMenteesIsNull() {
        // given
        ProgressTracker tracker = new ProgressTracker();
        Mentee[] mentees = null;

        // when
        String result = tracker.calculateTotalProgress(mentees);

        // then
        assertThat(result)
            .contains("Массив mentees равен null либо пустой");
    }

    @Test
    @DisplayName("Должен выдать сообщение при валидации пустого массива")
    void shouldCalculateTotalProgress_validateMenteesIsEmpty() {
        // given
        ProgressTracker tracker = new ProgressTracker();
        Mentee[] mentees = new Mentee[0];

        // when
        String result = tracker.calculateTotalProgress(mentees);

        // then
        assertThat(result)
            .contains("Массив mentees равен null либо пустой");
    }

}