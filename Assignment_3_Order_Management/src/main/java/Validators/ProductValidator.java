package Validators;

import DataAccesObject.ProductDAO;

public class ProductValidator {
    /**
     * Validate the ID of an order
     *
     * The ID must be formed only from digits (this assures that it doesn't have illegal characters and that it is a
     * positive integer)
     * Also, because the ID is inserted only while searching for a product, it must be found in the database
     *
     * @param ID The ID entered by the user
     * @throws IllegalArgumentException If the ID is invalid, throws an exception with a description of the error
     */
    public static void validateID(String ID) throws IllegalArgumentException {
        if (ID.isEmpty() || !ID.matches("^[1-9][0-9]*$"))
            throw new IllegalArgumentException("Wrong ID. Please enter an integer ID!");

        int productID = Integer.parseInt(ID);
        ProductDAO productDAO = new ProductDAO();
        if (productDAO.findByID(productID) == null)
            throw new IllegalArgumentException("Wrong ID. The product does not exist!");
    }

    /**
     * Validate the input for the database fields
     *
     * @param name A name should not contain Illegal characters
     * @param quantity The quantity must be a positive integer
     * @param price The price must be a positive rational number
     * @throws IllegalArgumentException If any of the fields violates the conditions imposed
     */
    public static void validateInput(String name, String quantity, String price) throws IllegalArgumentException {
        if (name.isEmpty() || !name.matches("^[^<>;=#{}`~/\\\\*+\\[\\]?()]*$"))
            throw new IllegalArgumentException("Illegal product name.");

        if (quantity.isEmpty() || !quantity.matches("^[1-9][0-9]*$"))
            throw new IllegalArgumentException("Wrong quantity. Quantity should be a positive integer!");

        if (price.isEmpty() || !price.matches("^[1-9][0-9]*(\\.[0-9]+)?$"))
            throw new IllegalArgumentException("Wrong price. Price should be a positive real number!");
    }
}
