package ru.mentee.power.devtools;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProgressDemoTest {

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

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("main должен вывести корректный ответ")
    void mainShouldPrintCorrectTotalProgress() {
        // Действие
        ProgressDemo.main(new String[0]);

        // Получаем вывод
        String output = outputStream.toString().trim();


        assertThat(output)
            .contains("Текущая ветка Git:")
            .contains("Status: sprint ready");
    }

    @Test
    @DisplayName("main не должен выводить пустую строку")
    void mainShouldNotPrintEmptyLine() {
        // Действие
        ProgressDemo.main(new String[0]);

        String output = outputStream.toString();

        assertThat(output).isNotBlank();
        assertThat(output).doesNotMatch("\\s*"); // Не состоит только из пробелов
    }


    @Test
    @DisplayName("main должен корректно обрабатывать трёх mentee с разным прогрессом")
    void mainShouldHandleThreeMenteesWithDifferentProgress() {
        // Действие
        ProgressDemo.main(new String[0]);

        String output = outputStream.toString();

        // Проверяем, что прогресс между минимальным (5/12≈41.67%) и максимальным (100%)
        assertThat(output)
            .doesNotContain("100%")  // Не все закончили
            .doesNotContain("41.67%"); // Не только самый низкий
    }

    @Test
    @DisplayName("main должен выводить одинаковый результат при многократном вызове")
    void mainShouldBeIdempotent() {
        // Первый вызов
        ProgressDemo.main(new String[0]);
        String firstOutput = outputStream.toString();

        // Очищаем перехватчик
        outputStream.reset();

        // Второй вызов
        ProgressDemo.main(new String[0]);
        String secondOutput = outputStream.toString();

        assertThat(secondOutput).isEqualTo(firstOutput);
    }




    @Test
    @DisplayName("Должен вернуть имя ветки для корректного ref-файла")
    void shouldReturnBranchNameForValidRef() throws Exception {
        Path headFile = tempDir.resolve("HEAD");
        Files.writeString(headFile, "ref: refs/heads/main");

        String result = ProgressDemo.getCurrentBranchName(headFile);

        assertThat(result).isEqualTo("main");
    }

    @Test
    @DisplayName("Должен вернуть null, когда HEAD не указывает на ветку (detached)")
    void shouldReturnNullForDetachedHead() throws Exception {
        Path headFile = tempDir.resolve("HEAD");
        Files.writeString(headFile, "a1b2c3d4e5f6789012345678901234567890abcd");

        String result = ProgressDemo.getCurrentBranchName(headFile);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Должен вернуть null, если файл .git/HEAD не существует")
    void shouldReturnNullWhenHeadFileDoesNotExist() {
        Path nonExistentFile = tempDir.resolve("NONEXISTENT");

        String result = ProgressDemo.getCurrentBranchName(nonExistentFile);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Должен вернуть null, если файл .git/HEAD пуст")
    void shouldReturnNullWhenHeadFileIsEmpty() throws Exception {
        Path headFile = tempDir.resolve("HEAD");
        Files.createFile(headFile);

        String result = ProgressDemo.getCurrentBranchName(headFile);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Должен извлечь имя ветки из сложного ref")
    void shouldExtractBranchNameFromComplexRef() throws Exception {
        Path headFile = tempDir.resolve("HEAD");
        Files.writeString(headFile, "ref: refs/heads/feature/DVT-3/add-tests");

        String result = ProgressDemo.getCurrentBranchName(headFile);

        assertThat(result).isEqualTo("feature/DVT-3/add-tests");
    }

    @Test
    void shouldFormatSummary_whenProgressCreated() {
        MenteeProgress progress = new MenteeProgress("Александр", 1, 9);

        String result = progress.summary();

        assertThat(result).isEqualTo("Sprint 1 → Александр: planned 9 h");
    }

    @Test
    void shouldDetectReadiness_whenHoursAboveThreshold() {
        MenteeProgress progress = new MenteeProgress("Александр", 1, 5);

        assertThat(progress.readyForSprint()).isTrue();
    }

    @Test
    void shouldDetectLackOfReadiness_whenHoursBelowThreshold() {
        MenteeProgress progress = new MenteeProgress("Александр", 1, 1);

        assertThat(progress.readyForSprint()).isFalse();
    }

}