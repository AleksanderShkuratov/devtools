package ru.mentee.power.devtools.progress;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тестирование Mentee")
class MenteeTest {

  @Test
  @DisplayName("Должен выбросить исключение при валидации полей")
  void shouldThrowExceptionWhenAnyFieldIsInvalid() {
    assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", -1, 12))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Некорректные значения прогресса");

    assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", 1, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Некорректные значения прогресса");

    assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", 5, 4))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Некорректные значения прогресса");
  }

}