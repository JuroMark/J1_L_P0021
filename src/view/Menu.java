package view;

import bo.StudentBO;
import utils.Validate;

/**
 * Lớp Menu hiển thị giao diện chính của chương trình Student Management.
 */
public class Menu {
    public static void displayMenu() {
        StudentBO studentBO = new StudentBO();
        // Nếu bạn muốn sử dụng inputStudents() từ lớp Student (entity),
        // bạn có thể lấy danh sách đã nhập bằng:
        // List<Student> students = Student.inputStudents();
        // Và sau đó gán vào StudentBO (ví dụ: studentBO.setStudents(students))
        // Tuy nhiên, ở phiên bản này chúng ta sử dụng các chức năng từ StudentBO.
        while (true) {
            System.out.println("\nWELCOME TO STUDENT MANAGEMENT");
            System.out.println("1. Create");
            System.out.println("2. Find and Sort");
            System.out.println("3. Update/Delete");
            System.out.println("4. Report");
            System.out.println("5. Exit");
            int choice = Validate.getInt("Please choose an option (1-5): ", 1, 5);
            switch (choice) {
                case 1:
                    // Nếu muốn dùng hàm nhập từ Student (entity):
                    // List<Student> list = Student.inputStudents();
                    // studentBO.getStudents().addAll(list);
                    studentBO.addStudent();
                    break;
                case 2:
                    studentBO.findSort();
                    break;
                case 3:
                    studentBO.updateOrDelete();
                    break;
                case 4:
                    studentBO.report();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
