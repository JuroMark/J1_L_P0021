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
     * createStudent(): create at least 10 students.
     * - If < 10, display a message to input more students.
     * - If >= 10, ask if the user wants to continue adding students.
     * - If duplicate ID, ask if the user wants to add a new course for this
     * student.
     */
    public void createStudent() {
        while (true) {
            // if students < 10, display message to input more students
            if (students.size() < 10) {
                System.err.println(IMessage.CREATE_STUDENT + students.size());
            } else {
                String cont = Validate.getString("Do you want to continue adding students? (Y/N): ",
                        IConstant.REGEX_YN, "Invalid choice! Please enter only 'Y' or 'N'.").toUpperCase();
                if (cont.equals("N")) {
                    break;
                }
            }

            String id = Validate.getString("Enter student ID: ", IConstant.REGEX_ID, IMessage.ID_WARNING);

            // check duplicate ID
            Student existingStudent = findStudentById(id);
            if (existingStudent != null) {
                String dupChoice = Validate.getString(
                        IMessage.ID_DUPLICATE + "(Y/N): ",
                        IConstant.REGEX_YN, "Invalid choice! Please enter only 'Y' or 'N'.").toUpperCase();
                if (dupChoice.equals("Y")) {
                    String newSemester = Validate.getString("Enter new semester: ", IConstant.REGEX_SEMESTER,
                            IMessage.SEMESTER_WARNING);
                    String newCourse = existingStudent.chooseCourse();
                    existingStudent.addSemester(newSemester);
                    existingStudent.addCourse(newCourse);
                    System.err.println(IMessage.NEW_COURSE_SUCCESSFULLY);
                } else {
                    System.err.println(IMessage.NO_CHANGE_DUPLICATEID);
                }
                continue;
            }

            // if ID is unique, continue to input other information
            String name = Validate.getString("Enter student name: ", IConstant.REGEX_NAME, IMessage.NAME_WARNING);
            String semester = Validate.getString("Enter semester: ", IConstant.REGEX_SEMESTER,
                    IMessage.SEMESTER_WARNING);
            String course = new Student().chooseCourse();
            Student s = new Student(id, name, semester, course);
            students.add(s);
            System.out.println(IMessage.STUDENT_ADD_SUCCESSFULLY);

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
     * findSort() find and sort students by name.
     * return a list of students whose name contains the keyword.
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
     * updateOrDelete() update or delete a student by ID.
     * return true if the operation is successful, false if the student is not
     * found.
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

    // updateStudent() update information of a student.
    private void updateStudent(Student student) {
        String newName = Validate.getString("Enter new student name: ", IConstant.REGEX_NAME,
                IMessage.NAME_WARNING);
        student.setName(newName);
        String newSemester = Validate.getString("Enter new semester: ", IConstant.REGEX_SEMESTER,
                IMessage.SEMESTER_WARNING);
        student.getSemesters().clear();
        student.getCourses().clear();
        student.addSemester(newSemester);
        String newCourse = student.chooseCourse();
        student.addCourse(newCourse);
        System.out.println("Student updated successfully.");
    }

    /**
     * report() return a report of students and their courses.
     * for each student, list the courses they are taking and the total number of
     * courses.
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
     * displayStudents() return a list of students and their information.
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
