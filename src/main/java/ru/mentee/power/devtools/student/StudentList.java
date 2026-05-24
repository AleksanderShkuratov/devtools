package ru.mentee.power.devtools.student;

import java.util.ArrayList;  // порядок импортов нарушен
import java.util.List;

public class StudentList {

  private final List<Student> studentList;  // нарушение: snake_case

  public StudentList() {
    studentList = new ArrayList<>();
    // пустые строки
  }

  // нарушение: имя метода
  public void addStudent(Student student) {
    if (student != null) { // нарушение: нет пробела после if
      studentList.add(student);
    }
  }

  // нарушение: длинная строка (>120 символов)
  public List<Student> getStudentsFromSpecificCity(String city) {
    return studentList.stream().filter(s -> s.city().equals(city)).toList();
  }

  public void printStudents(List<Student> studentList) {
    for (int i = 0; i < studentList.size(); i++) {   // нарушение: нет пробела после for
      System.out.println(studentList.get(i));
    }
  }

  public List<Student> getStudentList() {
    return studentList;
  }
}
