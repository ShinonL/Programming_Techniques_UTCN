package Validators;

import DataAccesObject.ClientDAO;

public class ClientValidator {
    /**
     * Validate the ID of an order
     *
     * The ID must be formed only from digits (this assures that it doesn't have illegal characters and that it is a
     * positive integer)
     * Also, because the ID is inserted only while searching for an order, it must be found in the database
     *
     * @param ID The ID entered by the user
     * @throws IllegalArgumentException If the ID is invalid, throws an exception with a description of the error
     */
    public static void validateID(String ID) throws IllegalArgumentException {
        if (ID.isEmpty() || !ID.matches("^[1-9][0-9]*$"))
            throw new IllegalArgumentException("Wrong ID. Please enter an integer ID!");

        int clientID = Integer.parseInt(ID);
        ClientDAO clientDAO = new ClientDAO();
        if (clientDAO.findByID(clientID) == null)
            throw new IllegalArgumentException("Wrong ID. The client does not exist!");
    }

    /**
     * Validate the input for the database fields
     *
     * @param firstName Should contain only Camel case words and "-" or " " between 2 words
     * @param lastName Should contain only Camel case words and "-" between 2 words
     * @param email Should respect the format of a basic email: address@email.com
     * @param phone It must contain only 10 digits
     * @throws IllegalArgumentException If any of the fields violates the conditions imposed
     */
    public static void validateInput(String firstName, String lastName, String email, String phone) throws IllegalArgumentException {
        if (firstName.isEmpty() || !firstName.matches("^[A-Z][a-z]+([\\- ][A-Z][a-z]+)?+$"))
            throw new IllegalArgumentException("Wrong first name!");

        if (lastName.isEmpty() || !lastName.matches("^[A-Z][a-z]+([\\-][A-Z][a-z]+)?+$"))
            throw new IllegalArgumentException("Wrong last name!");

        if (email.isEmpty() || !email.matches("^[\\w.]+@[\\w.]+\\.[A-Za-z]+$"))
            throw new IllegalArgumentException("Wrong email format!");

        if (phone.isEmpty() || !phone.matches("^\\d{10}$"))
            throw new IllegalArgumentException("Wrong phone number format. A phone number should have 10 digits.");

        ClientDAO clientDAO = new ClientDAO();
        if (clientDAO.findByEmail(email) != null)
            throw new IllegalArgumentException("Email already used!");

        if (clientDAO.findByPhone(phone) != null)
            throw new IllegalArgumentException("Phone number already used!");
    }
}
