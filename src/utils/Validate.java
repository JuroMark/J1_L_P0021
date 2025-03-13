package utils;

import java.util.Scanner;

/**
 * Lớp Validate cung cấp các phương thức để nhận và kiểm tra dữ liệu đầu vào.
 */
public class Validate {
    public static final Scanner sc = new Scanner(System.in);

    /**
     * Nhận một chuỗi từ người dùng và kiểm tra với regex.
     *
     * @param prompt       Tin nhắn hiển thị khi nhập.
     * @param regex        Biểu thức chính quy để kiểm tra.
     * @param errorInvalid Thông báo lỗi nếu không khớp.
     * @param errorEmpty   Thông báo lỗi nếu chuỗi rỗng.
     * @return Chuỗi hợp lệ.
     */
    public static String getString(String prompt, String regex, String errorInvalid) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.matches(regex)) {
                System.out.println(errorInvalid);
            } else {
                return input;
            }
        }

    }

    /**
     * Nhận một số nguyên trong khoảng xác định.
     *
     * @param prompt Tin nhắn yêu cầu nhập.
     * @param min    Giá trị tối thiểu.
     * @param max    Giá trị tối đa.
     * @return Số nguyên hợp lệ.
     */
    public static int getInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int number = Integer.parseInt(sc.nextLine().trim());
                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.out.println("Number out of range (" + min + " to " + max + ").");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    /**
     * Nhận một số thực (double) nằm trong khoảng cho phép.
     *
     * @param prompt             Tin nhắn yêu cầu nhập.
     * @param errorOutOfRange    Thông báo nếu số không hợp lệ.
     * @param errorInvalidNumber Thông báo nếu định dạng số sai.
     * @param errorEmpty         Thông báo nếu nhập rỗng.
     * @param min                Giá trị tối thiểu.
     * @param max                Giá trị tối đa.
     * @return Giá trị double hợp lệ.
     */
    public static double getDouble(String prompt, String errorOutOfRange, String errorInvalidNumber,
            String errorEmpty, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(errorEmpty);
                    continue;
                }
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println(errorOutOfRange);
                }
            } catch (NumberFormatException e) {
                System.out.println(errorInvalidNumber);
            }
        }
    }
}
