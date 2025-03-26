package main;

import bo.StudentBO;
import constant.IConstant;
import entity.Student;
import mocks.Data;
import utils.Validate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentBO studentBO = new StudentBO();
        while (true) {
            System.out.println("\nWELCOME TO STUDENT MANAGEMENT");
            System.out.println("1. Create");
            System.out.println("2. Find and Sort");
            System.out.println("3. Update/Delete");
            System.out.println("4. Report");
            System.out.println("5. Load sample data");
            System.out.println("6. Exit");
            int choice = Validate.getInt("Please choose an option (1-6): ",
                    "Out of range (1-6)", "Please input a number.", 1, 6);
            switch (choice) {
                case 1:
                    System.out.println("===== Create Students =====");
                    studentBO.createStudent();
                    break;
                case 2:
                    String keyword = Validate.getString("Enter student name to search: ", ".*", "Invalid input.");
                    List<Student> found = studentBO.findSort(keyword);
                    if (found.isEmpty()) {
                        System.out.println("No student found with that name.");
                    } else {
                        System.out.println("Found and sorted students:");
                        for (Student s : found) {
                            s.display();
                        }
                    }
                    break;
                case 3:
                    String id = Validate.getString("Enter student ID to update/delete: ", IConstant.REGEX_ID,
                            "ID can only contain letters and numbers.");
                    String action = Validate.getString("Do you want to update (U) or delete (D) this student?: ",
                            IConstant.REGEX_UD, "Invalid choice.").toUpperCase();
                    boolean result = studentBO.updateOrDelete(id, action);
                    if (result) {
                        System.out.println("Operation successful. Current student list:");
                        // Hiển thị bằng cách gọi display() của từng Student
                        for (Student student : studentBO.getStudents()) {
                            student.display();
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    String rep = studentBO.report();
                    System.out.println(rep);
                    break;
                case 5:
                    studentBO.getStudents().addAll(Data.getStudents());
                    System.out.println("Sample data loaded successfully.");
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
