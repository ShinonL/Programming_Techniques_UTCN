package Validators;

public class UserValidator {
    /**
     * Validate the user information
     * @param username string to be validated
     * @param password string to be validated
     * @throws IllegalArgumentException if any of the values was invalid
     */
    public static void validateUser(String username, String password) throws IllegalArgumentException {
        if (username.length() < 3)
            throw new IllegalArgumentException("Username must have more than 3 characters");
        if (!username.matches("^[a-zA-z0-9\\-\\._]+$"))
            throw new IllegalArgumentException("The username should contain only letters, digits and characters . - _");

        if (password.length() < 3)
            throw new IllegalArgumentException("Password must have more than 3 characters");
        if (!password.matches("^[a-zA-z0-9\\-\\._]+$"))
            throw new IllegalArgumentException("The password should contain only letters, digits and characters . - _");
    }
}
