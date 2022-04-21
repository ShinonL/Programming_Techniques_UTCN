package DataAccesObject;

import DatabaseConnection.ConnectionFactory;
import Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


/**
 * The client data access object are defined here
 *
 * This class extends the genericDAO class. All the methods are done by calling the super class methods with the product
 * specific type of items
 */
public class ProductDAO extends GenericDAO<Product> {
    public ProductDAO() {
        super(Product.class);
    }

    public void insert(Product product) {
        super.insert(product);
    }
    public void delete(int ID) {
        super.delete(ID, "productID");
    }
    public List<Product> findAll() {
        return super.findAll();
    }
    public Product findByID(int ID) {
        return super.findByID(ID, "productID");
    }
    public void update(int ID, List<Object> values) {
        super.update(ID, "productID", values);
    }

    /**
     * Decrease the in stock quantity
     * @param ID the product for which the quantity should be decreased
     * @param value the value with which it should be decreased
     */
    public void decreaseQuantity(int ID, int value) {
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "UPDATE product SET Quantity = Quantity - " + value + " WHERE ProductID = " + ID;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
