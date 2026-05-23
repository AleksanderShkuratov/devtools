package ru.mentee.power.devtools;

public record MenteeProgress(String menteeName, int sprintNumber, int plannedHoursPerWeek) {
    public boolean readyForSprint() {
        return plannedHoursPerWeek >= 3;
    }

    public String summary() {
        return "Sprint %d → %s: planned %d h".formatted(sprintNumber, menteeName, plannedHoursPerWeek);
    }

}
