package ru.mentee.power.devtools.student;

import java.util.List;

public class StudentDemo {

  static void main() {

    StudentList students = new StudentList();

    students.addStudent(new Student("Петр", "Москва"));
    students.addStudent(new Student("Софья", "Новгород"));
    students.addStudent(new Student("Иван", "Ярославль"));
    students.addStudent(new Student("Владимир", "Ярославль"));
    students.addStudent(new Student("Ольга", "Тверь"));

    System.out.println("Общий список студентов: ");
    students.printStudents(students.getStudentList());
    System.out.println("------------------------------------------");

    List<Student> studentsByYaroslavl = students.getStudentsFromSpecificCity("Ярославль");
    System.out.println("Студенты из Ярославля: ");
    students.printStudents(studentsByYaroslavl);
    System.out.println("------------------------------------------");

  }

}
