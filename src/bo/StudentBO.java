package bo;

import entity.Student;
import constant.IConstant;
import utils.Validate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * StudentBO handles the business operations for Student Management:
 * - Creating students (adding new students and adding courses to an existing
 * student if duplicate ID found)
 * - Finding and sorting students by name
 * - Updating or deleting a student
 * - Reporting course counts per student
 */
public class StudentBO {
    private List<Student> students;

    public StudentBO() {
        this.students = new ArrayList<>();
    }

    public StudentBO(List<Student> students) {
        this.students = students;
    }

    // Helper method to find a student by ID (case-insensitive)
    private Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * addStudent() prompts the user to create new students.
     * If the entered ID already exists, it allows adding a new course for that
     * student.
     */
    public void createStudent() {
        System.out.println("===== Create Students =====");
        while (true) {
            if (students.size() < 10) {
                System.out.println("You must create at least 10 students. Current count: " + students.size());
            } else {
                // Nếu đã đủ 10, hỏi người dùng có muốn tiếp tục nhập không.
                String choice = Validate.getString("Do you want to continue adding students? (Y/N): ",
                        IConstant.REGEX_YN,
                        "Invalid choice! Please enter only 'Y' or 'N'.").toUpperCase();
                if (choice.equals("N")) {
                    break;
                }
            }
            // If there are already 10 or more students, ask to continue adding.
            String id = Validate.getString("Enter student ID: ", IConstant.REGEX_ID,
                    "ID can only contain letters and numbers.");
            // Check for duplicate by ID (case-insensitive)
            Student existingStudent = findStudentById(id);
            if (existingStudent != null) {
                String choice = Validate
                        .getString("This ID already exists. Do you want to add a new course for this student? (Y/N): ",
                                IConstant.REGEX_YN,
                                "Invalid choice! Please enter only 'Y' or 'N'.")
                        .toUpperCase();
                if (choice.equalsIgnoreCase("Y")) {
                    System.out.println("Current courses and semesters:");
                    for (int i = 0; i < existingStudent.getSemesters().size(); i++) {
                        System.out.println((i + 1) + ". " + existingStudent.getSemesters().get(i)
                                + " - " + existingStudent.getCourses().get(i));
                    }
                    String newSemester = Validate.getString("Enter new semester: ", IConstant.REGEX_SEMESTER,
                            "Semester must be a number.");
                    String newCourse = Validate.getString("Enter new course name: ", IConstant.REGEX_COURSE,
                            "Course name can only contain letters, numbers and spaces.");
                    // Check if the course for the semester already exists
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
                String name = Validate.getString("Enter student name: ", IConstant.REGEX_NAME,
                        "Name can only contain letters and spaces.");
                String semester = Validate.getString("Enter semester: ", IConstant.REGEX_SEMESTER,
                        "Semester must be a number.");
                String course = Validate.getString("Enter course name: ", IConstant.REGEX_COURSE,
                        "Course name can only contain letters, numbers and spaces.");
                Student s = new Student(id, name, semester, course);
                students.add(s);
                System.out.println("Student added successfully.");
            }
            if (students.size() >= 10) {
                String cont = Validate.getString("Do you want to add another student? (Y/N): ",
                        IConstant.REGEX_YN, "Invalid choice! Please enter only 'Y' or 'N'.").toUpperCase();
                if (!cont.equalsIgnoreCase("Y")) {
                    break;
                }
            }
        }
    }

    /**
     * findSort() prompts the user for a keyword and finds all students whose name
     * contains the keyword,
     * then sorts and displays them in alphabetical order.
     */
    public void findSort() {
        String keyword = Validate.getString("Enter student name to search: ",
                ".*", "Invalid input.");
        List<Student> found = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                found.add(s);
            }
        }
        if (found.isEmpty()) {
            System.out.println("No student found with that name. Please try again.");
            findSort();
        }
        Collections.sort(found, new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
        System.out.println("Found and sorted students:");
        for (Student s : found) {
            System.out.println(s.output());
        }
    }

    /**
     * updateOrDelete() prompts the user for a student ID, then allows the user to
     * update or delete the student.
     */
    public void updateOrDelete() {
        String id = Validate.getString("Enter student ID to search: ", IConstant.REGEX_ID,
                "ID can only contain letters and numbers.");
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found, Try again.");
            updateOrDelete();
            return;
        }
        System.out.println("Student found:");
        System.out.println(students);
        String choice = Validate.getString("Do you want to update (U) or delete (D) this student? ",
                IConstant.REGEX_UD, "Invalid choice. Enter U or D.").toUpperCase();
        if (choice.equalsIgnoreCase("U")) {
            String newName = Validate.getString("Enter new student name: ", IConstant.REGEX_NAME,
                    "Name can only contain letters and spaces.");
            student.setName(newName);
            String newSemester = Validate.getString("Enter new semester: ", IConstant.REGEX_SEMESTER,
                    "Semester must be a number.");
            student.getSemesters().clear();
            student.addSemester(newSemester);
            String newCourse = Validate.getString("Enter new course name: ", IConstant.REGEX_COURSE,
                    "Course name can only contain letters, numbers and spaces.");
            student.setCourses(new ArrayList<>());
            student.addCourse(newCourse);
            System.out.println("Student updated successfully.");
        } else if (choice.equalsIgnoreCase("D")) {
            students.remove(student);
            System.out.println("Student deleted successfully.");
        }
    }

    /**
     * report() displays a report showing, for each student, each course and the
     * total number of times the student has taken that course.
     */
    public void report() {
        System.out.println("Student Name | Course | Total Courses");
        System.out.println("--------------------------------------");
        for (Student student : students) {
            List<String> checkedCourses = new ArrayList<>();
            for (int i = 0; i < student.getCourses().size(); i++) {
                String course = student.getCourses().get(i);
                if (checkedCourses.contains(course))
                    continue;
                int count = 0;
                for (String c : student.getCourses()) {
                    if (c.equalsIgnoreCase(course)) {
                        count++;
                    }
                }
                System.out.printf("%-15s | %-10s | %d%n", student.getName(), course, count);
                checkedCourses.add(course);
            }
        }
    }

    /**
     * getStudents() returns the current list of students.
     */
    public List<Student> getStudents() {
        return students;
    }
}
