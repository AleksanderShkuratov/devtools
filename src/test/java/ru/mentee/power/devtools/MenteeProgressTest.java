package ru.mentee.power.devtools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MenteeProgressTest {

    @Test
    void readyForSprint() {
        MenteeProgress progress5 = new MenteeProgress("Александр", 1, 5);
        MenteeProgress progress1 = new MenteeProgress("Александр", 1, 1);

        assertThat(progress5.readyForSprint()).isTrue();
        assertThat(progress1.readyForSprint()).isFalse();
    }

    @Test
    void summary() {
        MenteeProgress progress = new MenteeProgress("Александр", 1, 9);

        String result = progress.summary();

        assertThat(result).isEqualTo("Sprint 1 → Александр: planned 9 h");
    }
}