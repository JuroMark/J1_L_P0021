package constant;

public interface IMessage {
    String CREATE_STUDENT = "You must create at least 10 students. Current count: ";
    String ENTER_AGAIN = "Enter again!";
    String ID_DUPLICATE = "This ID already exists. Do you want to add a new course for this student? (Y/N): ";
    String ID_NOT_FOUND = "Student not found.";
    String ID_WARNING = "ID can only contain letters and numbers.";
    String NAME_WARNING = "Name can only contain letters.";
    String NEW_COURSE_SUCCESSFULLY = "New course added successfully.";
    String NO_CHANGE_DUPLICATEID = "No changes made for student with duplicate ID.";
    String SEMESTER_WARNING = "Semester must be a number.";
    String STUDENT_ADD_SUCCESSFULLY = "Student added successfully.";
}
