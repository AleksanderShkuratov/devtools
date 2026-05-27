package ru.mentee.power.devtools;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ProgressDemo {

  static void main(String[] args) {
    // 1. Путь к файлу .git/HEAD относительно корня проекта
    // TODO возможно нужно переделать получение пути - подумать...
    Path gitHeadPath = Paths.get(".git", "HEAD");
    String branchName = getCurrentBranchName(gitHeadPath);
    if (branchName != null) {
      System.out.println("Текущая ветка Git: " + branchName);
      System.out.println("---");
    } else {
     // throw new RuntimeException ("Git-информация недоступна");
      System.out.println("Git-информация недоступна");
      System.out.println("---");
    }

    // напиши new MenteeProgress( ⟪§⟫, 1, 6)  затем выдели и набери Ctrl + Alt + V (Windows/Linux)
    // или Option + Command + V (macOS) выделяет выражение в переменную.
    var progress = new MenteeProgress(
        "Александр", // возьми значение из своего плана DVT-0
        1,               // номер спринта
        6                // запланированные часы на спринт
    );

    System.out.println(progress.summary());
    if (progress.readyForSprint()) {
      System.out.println("Status: sprint ready");
    } else {
      System.out.println("Status: backlog first");
    }
  }

  public static String getCurrentBranchName(Path gitHeadPath) {
    try {
      // 2. Проверяем, существует ли файл
      if (!Files.exists(gitHeadPath)) {
        System.out.println("Файл .git/HEAD не найден.");
        return null;
      }

      // 3. Читаем первую строку файла
      List<String> lines = Files.readAllLines(gitHeadPath);
      if (lines.isEmpty()) {
        System.out.println("Файл .git/HEAD пуст");
        return null;
      }

      String headContent = lines.get(0).trim();

      // 4. Анализируем содержимое
      if (headContent.startsWith("ref: refs/heads/")) {
        // Отрезаем префикс "ref: refs/heads/"
        String branchName = headContent.substring("ref: refs/heads/".length());
        return branchName;
      } else if (headContent.matches("[a-f0-9]{40}")) {
        // Detached HEAD - мы на конкретном коммите, а не на ветке
        System.out.println(
            "Detached HEAD состояние. Текущая позиция: коммит " + headContent.substring(0, 7));
        return null;
      } else {
        // Неизвестный формат
        System.out.println("Неизвестный формат .git/HEAD: " + headContent);
        return null;
      }

    } catch (Exception e) {
      System.out.println("Ошибка при чтении .git/HEAD: " + e.getMessage());
      return null;
    }
  }
}