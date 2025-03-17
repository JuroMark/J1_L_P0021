package mocks;

import entity.Student;
import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("1", "Son", "1", "Java"));
        students.add(new Student("2", "Nghia", "7", ".Net"));
        students.add(new Student("3", "Hung", "2", "Java"));
        students.add(new Student("4", "Duy", "4", "C/C++"));
        students.add(new Student("5", "Hai", "6", "Python"));
        students.add(new Student("6", "An", "3", "Python"));
        students.add(new Student("7", "Huong", "5", "Ai"));
        students.add(new Student("8", "Trang", "4", "Java"));
        students.add(new Student("9", "Linh", "1", "C#"));
        students.add(new Student("10", "Thao", "2", "Ai"));
        return students;
    }
}
