package ru.mentee.power;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProgressDemoTest {

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