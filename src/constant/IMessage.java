package constant;

public interface IMessage {
    String CREATE_STUDENT = "You must create at least 10 students. Current count: ";
    String ID_DUPLICATE = "This ID already exists. Do you want to add a new course for this student? (Y/N): ";
    String NAME_WARNING = "Name can only contain letters.";
    String SEMESTER_WARNING = "Semester must be a number.";
    String ID_WARNING = "ID can only contain letters and numbers.";
    String ID_NOT_FOUND = "Student not found.";
    String NEW_COURSE_SUCCESSFULLY = "New course added successfully.";
    String NO_CHANGE_DUPLICATEID = "No changes made for student with duplicate ID.";
    String STUDENT_ADD_SUCCESSFULLY = "Student added successfully.";
}
