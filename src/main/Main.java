package main;

import bo.StudentBO;
import mocks.Data;
import utils.Validate; // Ensure this import is correct and the Validate class is in the util package

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
            System.out.println("5. Exit");
            int choice = Validate.getInt("Please choose an option (1-6): ", 1, 6);
            switch (choice) {
                case 1:
                    // Add student to list
                    studentBO.createStudent();
                    break;
                case 2:
                    // Find and sort student from list
                    studentBO.findSort();
                    break;
                case 3:
                    // Update or delete student from list
                    studentBO.updateOrDelete();
                    break;
                case 4:
                    // Report student list
                    studentBO.report();
                    break;
                case 5:
                    // Load data from mocks
                    studentBO.getStudents().addAll(Data.getStudents());
                    System.out.println("Sample data loaded successfully.");
                    break;
                case 6:
                    // Exit program
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
