package ru.mentee.power;

public class ProgressDemo {
    public static void main(String[] args) {
        // напиши new MenteeProgress( ⟪§⟫, 1, 6)  затем выдели и набери Ctrl + Alt + V (Windows/Linux) или Option + Command + V (macOS) выделяет выражение в переменную.
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
}