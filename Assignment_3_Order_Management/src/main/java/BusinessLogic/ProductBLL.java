package BusinessLogic;

import DataAccesObject.OrderDetailDAO;
import DataAccesObject.ProductDAO;
import Model.Product;
import Validators.ProductValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Based on the request of the controller, it manages the raw input given, validates it, transforms it into essential
 * product data and then applies one of the CRUD operations
 */
public class ProductBLL {
    private ProductDAO productDAO;
    private int productID;

    /**
     * Initializes the product data access object
     */
    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * Validates the ID given by the user
     * If found to be correct, it searches for the corresponding product
     * @param ID The id given by the user
     * @return A product object with the given ID
     * @throws IllegalArgumentException If the ID was invalid
     */
    public Product findProduct(String ID) throws IllegalArgumentException {
        ProductValidator.validateID(ID);

        setProductID(ID);
        return productDAO.findByID(productID);
    }

    /**
     * Returns all the products for JTable insertion format
     * @return A String matrix with the data corresponding to the products
     */
    public String[][] getAllProducts() {
        List<Product> products = productDAO.findAll();

        int count = 0;
        String[][] data = new String[products.size()][];
        for (Product product: products) {
            data[count] = new String[4];
            data[count][0] = Integer.toString(product.getProductID());
            data[count][1] = product.getName();
            data[count][2] = Integer.toString(product.getQuantity());
            data[count][3] = Double.toString(product.getPrice());
            count++;
        }
        return data;
    }

    /**
     * Insert into the database a product with the given fields.
     * All of the data is checked before continuing with the insertion
     * @param name The name of the new Product
     * @param quantity The quantity currently in stock
     * @param price The current price of the product
     * @throws IllegalArgumentException If any of the fields has an invalid format
     */
    public void createProduct(String name, String quantity, String price) throws IllegalArgumentException{
        ProductValidator.validateInput(name, quantity, price);

        productDAO.insert(new Product(name, Integer.parseInt(quantity), Double.parseDouble(price)));
    }

    /**
     * Deletes a product with the ID saved in the productID attribute of the class
     * @throws Exception If the product has links with any previous orders
     */
    public void deleteProduct() throws Exception {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        if (orderDetailDAO.findByProductID(productID) != null)
            throw new Exception("Cannot remove this product because it is recorded in an order.");

        productDAO.delete(productID);
    }

    /**
     * Updates an existing product. The product was previously searched so its identification code is saved in the
     * productID attribute of the class.
     *
     * The new fields may have the same value as the previous ones
     *
     * @param name The new name
     * @param quantity The new stock quantity
     * @param price The new price
     * @throws IllegalArgumentException If any of the field modifications is invalid
     */
    public void updateProduct(String name, String quantity, String price) throws IllegalArgumentException {
        ProductValidator.validateInput(name, quantity, price);

        List<Object> values = new ArrayList<>();
        values.add(name);
        values.add(quantity);
        values.add(price);

        productDAO.update(productID, values);
    }

    /**
     * Sets the values of the attribute productID
     * @param ID String containing the ID
     */
    public void setProductID(String ID) {
        productID = Integer.parseInt(ID);
    }
}
