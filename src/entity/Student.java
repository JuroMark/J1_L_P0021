package entity;

import constant.IConstant;
import utils.Validate;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Student chứa thông tin của một sinh viên:
 * - id, name
 * - Danh sách học kỳ (semesters) và khóa học (courses)
 *
 * Lớp cũng tích hợp phương thức nhập dữ liệu inputStudents(),
 * theo yêu cầu của giảng viên (mặc dù về lý thuyết nên tách riêng).
 */
public class Student {
    private String id;
    private String name;
    private List<String> semesters;
    private List<String> courses;

    public Student() {
        this.semesters = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public Student(String id, String name, String semester, String course) {
        this.id = id;
        this.name = name;
        this.semesters = new ArrayList<>();
        this.courses = new ArrayList<>();
        addSemester(semester);
        addCourse(course);
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<String> semesters) {
        this.semesters = semesters;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public void addSemester(String semester) {
        this.semesters.add(semester);
    }

    public void addCourse(String course) {
        this.courses.add(course);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(String.format("ID: %s, Name: %s\n", id, name));
        for (int i = 0; i < semesters.size(); i++) {
            result.append(String.format("  Semester: %s | Course: %s\n", semesters.get(i), courses.get(i)));
        }
        return result.toString();
    }

    /**
     * Method of entering student list from keyboard.
     * If a student has a duplicate ID (case insensitive), then
     * requires the user to select to add a new course for that student.
     *
     * @return The student list is imported.
     */
    public static List<Student> inputStudents() {
        List<Student> students = new ArrayList<>();
        System.out.println("====== Student Management Program ======");
        while (true) {
            System.out.println("Please input student information:");
            String id = Validate.getString("Enter student ID: ", IConstant.REGEX_ID,
                    "ID can only contain letters and numbers.");
            String name = Validate.getString("Enter student name: ", IConstant.REGEX_NAME,
                    "Name can only contain letters and spaces.");
            String semester = Validate.getString("Enter semester: ", IConstant.REGEX_SEMESTER,
                    "Semester must be a number.");
            String course = Validate.getString("Enter course name: ", IConstant.REGEX_COURSE,
                    "Course name can only contain letters, numbers and spaces.");

            // Check duplicate by ID (no matter uppercase or lowercase)
            Student existingStudent = null;
            for (Student s : students) {
                if (s.getId().equalsIgnoreCase(id)) {
                    existingStudent = s;
                    break;
                }
            }

            if (existingStudent != null) {
                String choice = Validate
                        .getString("This ID already exists. Do you want to add a new course for this student? (Y/N): ",
                                IConstant.REGEX_YN, "Invalid choice! Please enter only 'Y' or 'N'.")
                        .toUpperCase();
                if (choice.equals("Y")) {
                    // Hiển thị danh sách khóa học hiện có
                    System.out.println("Current courses and semesters:");
                    for (int i = 0; i < existingStudent.getSemesters().size(); i++) {
                        System.out.println((i + 1) + ". " + existingStudent.getSemesters().get(i) +
                                " - " + existingStudent.getCourses().get(i));
                    }
                    String newSemester = Validate.getString("Enter new semester: ", IConstant.REGEX_SEMESTER,
                            "Semester must be a number.");
                    String newCourse = Validate.getString("Enter new course name: ", IConstant.REGEX_COURSE,
                            "Course name can only contain letters, numbers and spaces.");
                    // Check if that course already exists for the same semester
                    boolean exists = false;
                    for (int i = 0; i < existingStudent.getSemesters().size(); i++) {
                        if (existingStudent.getSemesters().get(i).equalsIgnoreCase(newSemester) &&
                                existingStudent.getCourses().get(i).equalsIgnoreCase(newCourse)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        existingStudent.addSemester(newSemester);
                        existingStudent.addCourse(newCourse);
                        System.out.println("New course added successfully.");
                    } else {
                        System.out.println("This course already exists for that semester.");
                    }
                } else {
                    System.out.println("No changes made for student with duplicate ID.");
                }
            } else {
                Student s = new Student(id, name, semester, course);
                students.add(s);
                System.out.println("Student added successfully.");
            }

            String cont = Validate.getString("Do you want to add another student? (Y/N): ",
                    IConstant.REGEX_YN, "Invalid choice! Please enter only 'Y' or 'N'.")
                    .toUpperCase();
            if (!cont.equals("Y")) {
                break;
            }
        }
        return students;
    }
}
