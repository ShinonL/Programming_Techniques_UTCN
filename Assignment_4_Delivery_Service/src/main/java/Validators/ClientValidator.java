package Validators;

public class ClientValidator {
    /**
     * Validate input for the rating
     * @param rating string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validateRating(String rating) throws IllegalArgumentException {
        if (!rating.matches("^[0-5](\\.[0-9]+)?$"))
            throw new IllegalArgumentException("Rating is a real number between 0 and five.");
        double value = Double.parseDouble(rating);
        if (value > 5)
            throw new IllegalArgumentException("Rating is a real number between 0 and five.");
    }

    /**
     * Validate input for the price
     * @param price string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validatePrice(String price) throws IllegalArgumentException {
        if (!price.matches("^[1-9]+[0-9]*(\\.[0-9]+)?$"))
            throw new IllegalArgumentException("Price is a real number.");
    }

    /**
     * Validate input for the expected integer values
     * @param value string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validateInterger(String value) throws IllegalArgumentException {
        if (!value.matches("^[1-9]+[0-9]*$"))
            throw new IllegalArgumentException("Please insert an integer.");
    }
}
