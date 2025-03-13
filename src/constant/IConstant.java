package constant;

/**
 * Interface IConstant chứa các hằng số Regex dùng để kiểm tra đầu vào.
 */
public interface IConstant {
    String REGEX_NAME = "^[A-Za-z\\s]+$"; // Tên chỉ chứa chữ cái và khoảng trắng.
    String REGEX_COURSE = "^[A-Za-z0-9\s.]+$"; // Course chỉ chứa chữ, số và khoảng trắng.
    String REGEX_SEMESTER = "^[0-9]+$"; // Semester là số.
    String REGEX_ID = "^[A-Za-z0-9]+$"; // ID chứa chữ và số, không có khoảng trắng.
    String REGEX_YN = "^[YN]$"; // Chỉ cho phép Y hoặc N.
    String REGEX_UD = "^[UDud]$";
}
