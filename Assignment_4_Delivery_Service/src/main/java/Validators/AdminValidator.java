package Validators;

public class AdminValidator {
    /**
     * Validate the input for a new Base product
     * @param title string to be validated
     * @param rating string to be validated
     * @param calories string to be validated
     * @param protein string to be validated
     * @param fat string to be validated
     * @param sodium string to be validated
     * @param price string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validateNewProduct(String title, String rating, String calories, String protein,
                                          String fat, String sodium, String price) throws IllegalArgumentException {
        if (title.matches("^[ ]+$") || title.equals(""))
            throw new IllegalArgumentException("Title cannot be empty");

        if (!rating.matches("^[0-5](\\.[0-9]+)?$"))
            throw new IllegalArgumentException("Rating is a real number between 0 and five.");
        double value = Double.parseDouble(rating);
        if (value > 5)
            throw new IllegalArgumentException("Rating is a real number between 0 and 5.");

        if (!calories.matches("^[1-9]+[0-9]*$"))
            throw new IllegalArgumentException("Calories should be a positive integer.");

        if (!protein.matches("^[1-9]+[0-9]*$"))
            throw new IllegalArgumentException("Protein should be a positive integer.");

        if (!fat.matches("^[1-9]+[0-9]*$"))
            throw new IllegalArgumentException("Fat should be a positive integer.");

        if (!sodium.matches("^[1-9]+[0-9]*$"))
            throw new IllegalArgumentException("Sodium should be a positive integer.");

        if (!price.matches("^[1-9]+[0-9]*(\\.[0-9]+)?$"))
            throw new IllegalArgumentException("Price is a real number.");
    }

    /**
     * Validate input for the Time Report
     * @param startHour string to be validated
     * @param endHour string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validateTimeReport(String startHour, String endHour) throws IllegalArgumentException {
        if (!startHour.matches("^[0-9]{1,2}$"))
            throw new IllegalArgumentException("The hours are numbers between 00 and 23");
        if (!endHour.matches("^[0-9]{1,2}$"))
            throw new IllegalArgumentException("The hours are numbers between 00 and 23");

        int start = Integer.parseInt(startHour);
        int end = Integer.parseInt(endHour);
        if (start > 23 || end > 23)
            throw new IllegalArgumentException("The hours are numbers between 00 and 23");

        if (start >= end)
            throw new IllegalArgumentException("Starting hour must occur before the ending hour");
    }

    /**
     * Validate input for the Frequency Report
     * @param frequency string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validateFrequencyReport(String frequency) throws IllegalArgumentException {
        if (!frequency.matches("^[1-9]+[0-9]*$"))
            throw new IllegalArgumentException("Frequency should be a positive integer.");
    }

    /**
     * Validate input for the Clients Report
     * @param frequency string to be validated
     * @param value string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validateClientsReport(String frequency, String value) throws IllegalArgumentException {
        if (!frequency.matches("^[1-9]+[0-9]*$"))
            throw new IllegalArgumentException("Frequency should be a positive integer.");

        if (!value.matches("^[1-9]+[0-9]*(\\.[0-9]+)?$"))
            throw new IllegalArgumentException("Price is a real number.");
    }

    /**
     * Validate input for the Clients Report
     * @param str string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validateDateReport(String str) throws IllegalArgumentException {
        if (!str.matches("^[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}$"))
            throw new IllegalArgumentException("Wrong date format");

        String[] splitStr = str.split("\\.");
        if (!splitStr[0].matches("^[0-3]?[0-9]$") || Integer.parseInt(splitStr[0]) > 31)
            throw new IllegalArgumentException("The days are numbers between 1 and 31");
        if (!splitStr[1].matches("^[0-1]?[0-9]$") || Integer.parseInt(splitStr[1]) > 12)
            throw new IllegalArgumentException("The hours are numbers between 1 and 1");
        if (!splitStr[2].matches("^[0-2][0-9][0-9][0-9]$") || Integer.parseInt(splitStr[2]) > 2021)
            throw new IllegalArgumentException("The hours are numbers between 0 and 2021");
    }
}
