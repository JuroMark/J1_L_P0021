package utils;

import java.util.Scanner;

public class Validate {
    public static final Scanner sc = new Scanner(System.in);

    /**
     * Get a string.
     *
     * @param prompt       The message required input.
     * @param regex        The regular expression to check the string.
     * @param errorInvalid The message required if the string is invalid.
     * @return a valid string.
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
     * Get an integer in the allowed range.
     *
     * @param prompt The message required input.
     * @param min    The minimum value.
     * @param max    The maximum value.
     * @return The valid integer.
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
     * Get a real number (double) within the allowed range.
     *
     * @param prompt             The message required input.
     * @param errorOutOfRange    The message required if out of range.
     * @param errorInvalidNumber The message required if the number format is wrong.
     * @param min                The minimum value.
     * @param max                The maximum value.
     * @return The valid real number.
     */
    public static double getDouble(String prompt, String errorOutOfRange, String errorInvalidNumber,
            double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(sc.nextLine().trim());
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
