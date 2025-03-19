package entity;

import constant.IConstant;
import utils.Validate;
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

    public String output() {
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
    public void inputStudent() {
        this.id = Validate.getString("Enter student ID: ", IConstant.REGEX_ID,
                "ID can only contain letters and numbers.");
        this.name = Validate.getString("Enter student name: ", IConstant.REGEX_NAME,
                "Name can only contain letters and spaces.");
        String semester = Validate.getString("Enter semester: ", IConstant.REGEX_SEMESTER,
                "Semester must be a number.");
        String course = "";
        while (true) {
            System.out.println("Choose a course:");
            System.out.println("1. Java");
            System.out.println("2. .Net");
            System.out.println("3. C/C++");
            System.out.print("Enter your choice (1-3): ");
            int choice = Validate.getInt("Invalid choice. Please enter 1, 2, or 3.", 1, 3);

            switch (choice) {
                case 1:
                    course = "Java";
                    break;
                case 2:
                    course = ".Net";
                    break;
                case 3:
                    course = "C/C++";
                    break;
            }
            break;
        }
        this.semesters.add(semester);
        this.courses.add(course);
    }
}
