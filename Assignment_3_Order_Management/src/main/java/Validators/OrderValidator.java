package Validators;

import DataAccesObject.OrderDAO;
import DataAccesObject.ProductDAO;

public class OrderValidator {
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

        int orderID = Integer.parseInt(ID);
        OrderDAO orderDAO = new OrderDAO();
        if (orderDAO.findByID(orderID) == null)
            throw new IllegalArgumentException("Wrong ID. The order does not exist!");
    }

    /**
     * Validate the address
     * An address should contain only, letters, digits, blank spaces, comas, dots and hyphens
     *
     * @param address The address given by the user
     * @throws IllegalArgumentException If the address is invalid, throws an exception with a description of the error
     */
    public static void validateAddress(String address) throws IllegalArgumentException {
        if (address.isEmpty() || !address.matches("^[\\w\\d ,.-]+$")) {
            throw new IllegalArgumentException("Illegal address!");
        }
    }

    /**
     * Validate the (Product, Quantity) pair
     *
     * First, make sure that the introduced quantity is a positive integer
     * Then, check if there are enough pieces in the stock of the specified product
     *
     * @param productID The ID of the product
     * @param quantity The amount of pieces taken out of stock
     * @throws IllegalArgumentException If the quantity number is invalid or if there are not enough pieces in stock,
     *                                  throws an exception with a description of the error
     */
    public static void validateInput(String productID, String quantity) throws IllegalArgumentException {
        if (quantity.isEmpty() || !quantity.matches("^[1-9][0-9]*$"))
            throw new IllegalArgumentException("Wrong quantity. Quantity should be a positive integer!");

        int intQuantity = Integer.parseInt(quantity);
        ProductDAO productDAO = new ProductDAO();
        int stock = productDAO.findByID(Integer.parseInt(productID)).getQuantity();
        if (intQuantity > stock)
            throw new IllegalArgumentException("There are " + stock + " products left in stock.");
    }
}
