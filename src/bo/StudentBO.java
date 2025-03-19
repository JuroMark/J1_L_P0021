package bo;

import entity.Student;
import constant.IConstant;
import constant.IMessage;
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
            if (students.size() < 10) {
                System.err.println(IMessage.ERR_CREATE_STUDENT + students.size());
            } else {
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
                    System.err.println(IMessage.ERR_NEW_COURSE_SUCCESSFULLY);
                } else {
                    System.err.println(IMessage.ERR_NO_CHANGE_DUPLICATEID);
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
            System.out.println(IMessage.ERR_STUDENT_ADD_SUCCESSFULLY);

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
     * findSort() Find students by name containing keyword,
     * then sort the result by name.
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
