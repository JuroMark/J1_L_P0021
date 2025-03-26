package entity;

import constant.IConstant;
import constant.IMessage;
import utils.Validate;
import bo.StudentBO;
import java.util.ArrayList;
import java.util.List;

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
        this();
        this.id = id;
        this.name = name;
        this.semesters.add(semester);
        this.courses.add(course);
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

    public void addCourse(String course) {
        this.courses.add(course);
    }

    public void addSemester(String semester) {
        this.semesters.add(semester);
    }

    public boolean input(StudentBO studentBO) {
        this.id = Validate.getString("Enter student ID: ", IConstant.REGEX_ID, IMessage.ID_WARNING);
        Student existing = studentBO.findStudentById(this.id);
        if (existing != null) {
            String dupChoice = Validate.getString(
                    "Duplicate ID found. Do you want to add a new course for this student? (Y/N): ",
                    IConstant.REGEX_YN,
                    "Invalid input! Please enter only 'Y' or 'N'.").toUpperCase();

            if (dupChoice.equals("Y")) {
                String semester = Validate.getString("Enter semester: ", IConstant.REGEX_SEMESTER,
                        IMessage.SEMESTER_WARNING);
                String course = chooseCourse();
                existing.addSemester(semester);
                existing.addCourse(course);
                System.out.println("New course added successfully to student with ID " + existing.getId());
            }
            // Trả về false, nghĩa là không cần thêm đối tượng mới
            return false;
        }

        // Nếu ID chưa tồn tại, nhập đầy đủ thông tin cho sinh viên mới:
        this.name = Validate.getString("Enter student name: ", IConstant.REGEX_NAME, IMessage.NAME_WARNING);
        String semester = Validate.getString("Enter semester: ", IConstant.REGEX_SEMESTER, IMessage.SEMESTER_WARNING);
        this.semesters.add(semester);
        String course = chooseCourse();
        this.courses.add(course);
        return true;
    }

    /**
     * Hàm hiển thị thông tin của Student.
     */
    public void display() {
        // Nối danh sách semester và course thành chuỗi
        String semesterStr = String.join(", ", semesters);
        String courseStr = String.join(", ", courses);
        System.out.printf("%-10s%-20s%-20s%-20s\n", this.id, this.name, semesterStr, courseStr);
    }

    /**
     * Cho phép người dùng chọn khóa học.
     */
    public String chooseCourse() {
        int choice = Validate.getInt("1. Java\n2. .Net\n3. C/C++\nChoose course (1-3): ",
                "Out of range. Please try again.", "Please input a number.", 1, 3);
        switch (choice) {
            case 1:
                System.out.println("You chose: Java");
                return "Java";
            case 2:
                System.out.println("You chose: .Net");
                return ".Net";
            case 3:
                System.out.println("You chose: C/C++");
                return "C/C++";
            default:
                return "";
        }
    }
}
