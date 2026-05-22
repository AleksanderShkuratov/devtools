package ru.mentee.power.devtools.progress;

/**
 * Record для представления mentee с информацией о прогрессе обучения.
 * Автоматически создаёт конструктор и геттеры: name(), city(), goal(), completedLessons(), totalLessons().
 */
public record Mentee(
        String name,
        String city,
        String goal,
        int completedLessons,
        int totalLessons
) {
    // Компактный конструктор с валидацией (опционально)
    public Mentee {
        if (completedLessons < 0 || totalLessons <= 0 || completedLessons > totalLessons) {
            throw new IllegalArgumentException("Некорректные значения прогресса");
        }
    }
}
