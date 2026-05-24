package ru.mentee.power.devtools.progress;

public class ProgressTracker {

  /**
   * Вычисляет суммарный прогресс группы mentee.
   *
   * @param mentees массив с информацией
   * @return строка с информацией о суммарном прогрессе (пройдено/осталось уроков)
   */
  public String calculateTotalProgress(Mentee[] mentees) {
    // TODO: реализовать логику суммарного прогресса с использованием цикла while
    // Шаги:
    // 1. Проверить валидность массива(null, пустой)
    if (mentees == null || mentees.length == 0) {
      return "Массив mentees равен null либо пустой";
    }

    // 2. Инициализировать аккумуляторы: totalComplected = 0, totalTotal = 0, index = 0
    int totalComplected = 0;
    int totalTotal = 0;
    int index = 0;

    // 3. Использовать цикл while (index < mentees.length) для переборки массива
    while (index < mentees.length) {
      // 4. На каждой итерации:
      // totalComplected += mentees[index].completedLessons(), totalLessons(), index++
      totalComplected += mentees[index].completedLessons();
      totalTotal += mentees[index].totalLessons();
      index++;
    }

    // 5. Вычислить оставшиеся: totalRemaining = totalTotal - totalCompleted
    int totalRemaining = totalTotal - totalComplected;

    // 6. Вернуть строку формата <<$$>>
    return "Суммарно: пройдено " + totalComplected + " из " + totalTotal + " уроков, осталось "
        + totalRemaining + " уроков";

    //throw new UnsupportedOperationException("Метод calculateTotalProgress ещё не реализован");
  }

  static void main() {
    ProgressTracker tracker = new ProgressTracker();

    // Создаем массив mentees (продолжение DVT-2: добавляем прогресс к личной карточке)
    Mentee[] mentees = {
        new Mentee("Иван", "Москва", "Backend разработка", 5, 12),
        new Mentee("Мария", "Санкт-Петербург", "Fullstack", 8, 12),
        new Mentee("Пётр", "Казань", "Java Backend", 12, 12),
    };

    String progress = tracker.calculateTotalProgress(mentees);
    System.out.println(progress);
  }

}
