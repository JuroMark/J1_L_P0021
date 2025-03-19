package constant;

public interface IMessage {
    String ERR_CREATE_STUDENT = "You must create at least 10 students. Current count: ";
    String ERR_ID_EXIST = "This ID already exists. Do you want to add a new course for this student? (Y/N): ";
    String ERR_ID_NOT_FOUND = "Student not found.";
    String ERR_NEW_COURSE_SUCCESSFULLY = "New course added successfully.";
    String ERR_NO_CHANGE_DUPLICATEID = "No changes made for student with duplicate ID.";
    String ERR_STUDENT_ADD_SUCCESSFULLY = "Student added successfully.";
}
