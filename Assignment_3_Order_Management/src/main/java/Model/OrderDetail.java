package Model;

/**
 * A mirror of the "client" table from the "schooldb" database.
 * It is the internal representation of the table.
 *
 * This class and its corresponding table were created to solve the ONE-TO-MANY relationship between an order and its
 * associated products.
 *
 * Unlike the other models, the OrderDetail does not have a unique identification key and all fields may repeat their
 * values.
 */
public class OrderDetail {
    private int orderID, productID;
    private int quantity;

    /**
     * The empty constructor is used by the SQL queries.
     * It creates an instance of a client without initializing its fields.
     */
    public OrderDetail() {}

    /**
     * A constructor that sets for the orderID order a product given by the productID
     *
     * @param orderID The ID of the order to which the (Product, Quantity) pair belongs to.
     * @param productID The ID of one of the products ordered.
     * @param quantity The quantity (amount) of products with the specified ID were ordered
     */
    public OrderDetail(int orderID, int productID, int quantity) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
