package bo;

import entity.Student;
import constant.IConstant;
import utils.Validate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentBO {
    private List<Student> students;

    public StudentBO() {
        this.students = new ArrayList<>();
    }

    public StudentBO(List<Student> students) {
        this.students = students;
    }

    private Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * createStudent() Enter student list from keyboard.
     */
    public void createStudent() {
        while (true) {
            // Nếu số lượng chưa đủ 10, thông báo bắt buộc nhập thêm.
            if (students.size() < 10) {
                System.out.println("You must create at least 10 students. Current count: " + students.size());
            } else {
                // Nếu đã đủ 10, hỏi người dùng có muốn tiếp tục thêm không.
                String cont = Validate.getString("Do you want to continue adding students? (Y/N): ",
                        IConstant.REGEX_YN, "Invalid choice! Please enter only 'Y' or 'N'.").toUpperCase();
                if (cont.equals("N")) {
                    break;
                }
            }

            // Enter ID from keyboard.
            String id = Validate.getString("Enter student ID: ", IConstant.REGEX_ID,
                    "ID can only contain letters and numbers.");

            // Check duplicate ID.
            Student existingStudent = findStudentById(id);
            if (existingStudent != null) {
                String dupChoice = Validate
                        .getString("This ID already exists. Do you want to add a new course for this student? (Y/N): ",
                                IConstant.REGEX_YN, "Invalid choice! Please enter only 'Y' or 'N'.")
                        .toUpperCase();
                if (dupChoice.equals("Y")) {
                    String newSemester = Validate.getString("Enter new semester: ", IConstant.REGEX_SEMESTER,
                            "Semester must be a number.");
                    String newCourse = existingStudent.chooseCourse();
                    existingStudent.addSemester(newSemester);
                    existingStudent.addCourse(newCourse);
                    System.out.println("New course added successfully.");
                } else {
                    System.out.println("No changes made for student with duplicate ID.");
                }
                continue;
            }

            String name = Validate.getString("Enter student name: ", IConstant.REGEX_NAME,
                    "Name can only contain letters and spaces.");
            String semester = Validate.getString("Enter semester: ", IConstant.REGEX_SEMESTER,
                    "Semester must be a number.");
            String course = new Student().chooseCourse();
            Student s = new Student(id, name, semester, course);
            students.add(s);
            System.out.println("Student added successfully.");

            if (students.size() >= 10) {
                String contAgain = Validate.getString("Do you want to add another student? (Y/N): ",
                        IConstant.REGEX_YN, "Invalid choice! Please enter only 'Y' or 'N'.").toUpperCase();
                if (!contAgain.equals("Y")) {
                    break;
                }
            }
        }
    }

    /**
     * findSort() trả về danh sách các sinh viên tìm được (theo từ khóa tên) sau khi
     * sắp xếp theo tên.
     */
    public List<Student> findSort(String keyword) {
        List<Student> found = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                found.add(s);
            }
        }
        if (!found.isEmpty()) {
            Collections.sort(found, new Comparator<Student>() {
                public int compare(Student s1, Student s2) {
                    return s1.getName().compareToIgnoreCase(s2.getName());
                }
            });
        }
        return found;
    }

    /**
     * updateOrDelete() Process update or delete students by ID.
     * Returns true if the operation is successful,
     * false if the student is not found.
     */
    public boolean updateOrDelete(String id, String action) {
        Student student = findStudentById(id);
        if (student == null) {
            return false;
        }
        if (action.equalsIgnoreCase("U")) {
            updateStudent(student);
        } else if (action.equalsIgnoreCase("D")) {
            students.remove(student);
        }
        return true;
    }

    // updateStudent() Update student information.
    private void updateStudent(Student student) {
        String newName = Validate.getString("Enter new student name: ", IConstant.REGEX_NAME,
                "Name can only contain letters and spaces.");
        student.setName(newName);
        String newSemester = Validate.getString("Enter new semester: ", IConstant.REGEX_SEMESTER,
                "Semester must be a number.");
        student.getSemesters().clear();
        student.getCourses().clear();
        student.addSemester(newSemester);
        String newCourse = student.chooseCourse();
        student.addCourse(newCourse);
    }

    /**
     * report() return a report string of student list,
     * for each student list each course and total number of times taken.
     */
    public String report() {
        StringBuilder report = new StringBuilder();
        report.append("Student Name | Course | Total Courses\n");
        report.append("--------------------------------------\n");
        for (Student student : students) {
            // Dùng map logic đơn giản: duyệt qua danh sách course của sinh viên
            List<String> checkedCourses = new ArrayList<>();
            for (String course : student.getCourses()) {
                if (!checkedCourses.contains(course)) {
                    int count = 0;
                    for (String c : student.getCourses()) {
                        if (c.equalsIgnoreCase(course)) {
                            count++;
                        }
                    }
                    report.append(String.format("%-15s | %-10s | %d%n", student.getName(), course, count));
                    checkedCourses.add(course);
                }
            }
        }
        return report.toString();
    }

    /**
     * displayStudents() returns a string representing the list of students.
     */
    public String displayStudents() {
        StringBuilder result = new StringBuilder();
        if (students.isEmpty()) {
            result.append("No students available.\n");
        } else {
            int count = 1;
            for (Student student : students) {
                result.append("Student ").append(count).append(":\n");
                result.append(student.output()).append("\n");
                count++;
            }
        }
        return result.toString();
    }

    public List<Student> getStudents() {
        return students;
    }
}
