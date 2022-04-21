package BusinessLogic;

import DataAccesObject.ClientDAO;
import DataAccesObject.OrderDAO;
import DataAccesObject.OrderDetailDAO;
import DataAccesObject.ProductDAO;
import Model.Client;
import Model.Order;
import Model.OrderDetail;
import Model.Product;
import Validators.OrderValidator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Based on the request of the controller, it manages the raw input given, validates it, transforms it into essential
 * client data and then applies one of the CRUD operations
 */
public class OrderBLL {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private ClientDAO clientDAO;
    private OrderDetailDAO orderDetailDAO;

    /**
     * Initializes client, product, order and order details data access objects
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        clientDAO = new ClientDAO();
        orderDetailDAO = new OrderDetailDAO();
    }

    /**
     * Gets the IDs and the names of all the clients
     * @return An array of Strings containing the ID and the full name of the clients
     */
    public String[] getClientsString() {
        List<Client> clients = clientDAO.findAll();
        String[] clientString = new String[clients.size()];
        int i = 0;
        for (Client client: clients) {
            clientString[i] = client.getClientID() + " " + client.getFirstName() + " " + client.getLastName();
            i++;
        }
        return clientString;
    }

    /**
     * Gets the IDs and the names of all the products
     * @return An array of Strings containing the ID and the name of the products
     */
    public String[] getProductsString() {
        List<Product> products = productDAO.findAll();
        String[] productString = new String[products.size()];
        int i = 0;
        for (Product product: products) {
            productString[i] = product.getProductID() + " " + product.getName();
            i++;
        }
        return productString;
    }

    /**
     * Create an order for the selected client
     *
     * @param client A string containing data about the selected client. Is is selected from the array given by the
     *               method getClientsString()
     * @param address The shipping address
     * @param products A list of selected products formatted as the output of the method getProductsString()
     * @param quantities A list of quantities for each product
     * @throws IllegalArgumentException If any of the values was invalid
     */
    public void createOrder(String client, String address, List<String> products, List<String> quantities) throws IllegalArgumentException {
        OrderValidator.validateAddress(address);

        // get the ID from the ID+NAME string
        String[] clientComponents = client.split(" ");
        int clientId = Integer.parseInt(clientComponents[0]);

        double total = 0;

        int i = 0;
        for (String product: products) {
            String[] productComponents = product.split(" ");

            OrderValidator.validateInput(productComponents[0], quantities.get(i));

            int productId = Integer.parseInt(productComponents[0]);

            Product selectedProduct = productDAO.findByID(productId);
            total += selectedProduct.getPrice() * Integer.parseInt(quantities.get(i));
            i++;
        }

        orderDAO.insert(new Order(clientId, address, total));
        createOrderDetails(orderDAO.findLatestOrder().getOrderID(), products, quantities);
    }

    /**
     * After creating the order, create its details
     *
     * @param orderID The ID of the previously created order
     * @param products The list of products corresponding to the order
     * @param quantities The quantity removed from each product stock
     */
    public void createOrderDetails(int orderID, List<String> products, List<String> quantities) {
        int i = 0;
        for (String product: products) {
            String[] productComponents = product.split(" ");
            int productId = Integer.parseInt(productComponents[0]);

            int quantity = Integer.parseInt(quantities.get(i));
            productDAO.decreaseQuantity(productId, quantity);

            orderDetailDAO.insert(new OrderDetail(orderID, productId, quantity));

            i++;
        }

        createBill(orderDAO.findLatestOrder());
    }

    /**
     * For each order, create a bill with all the detailed information
     * @param order The order for which the bill should be created
     */
    private void createBill(Order order) {
        File bill = new File("Order Bills/Order_" + order.getOrderID() + ".txt");

        Client client = clientDAO.findByID(order.getClientID());

        try {
            bill.createNewFile();

            FileWriter writer = new FileWriter(bill.getPath(), true);
            writer.write("--------------------- ORDER NUMBER " + order.getOrderID() + " ---------------------\n\n");
            writer.write("Client Name: " + client.getFirstName() + " " + client.getLastName() + "\n");
            writer.write("Client Phone Number: " + client.getPhone() + "\n");
            writer.write("Client Email Address: " + client.getEmail() + "\n\n");
            writer.write("Shipping Address: " + order.getAddress() + "\n\n");
            writer.write("--------------------- ORDERED PRODUCTS ---------------------\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (OrderDetail orderDetail: orderDetailDAO.getDetails(order.getOrderID())) {
            Product product = productDAO.findByID(orderDetail.getProductID());

            try {
                FileWriter writer = new FileWriter(bill.getPath(), true);
                writer.write("Product Name: " + product.getName() + "\n");
                writer.write("                  " + orderDetail.getQuantity() + " x " + product.getPrice() + " = " + orderDetail.getQuantity() * product.getPrice() + "\n\n");

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter writer = new FileWriter(bill.getPath(), true);
            writer.write("      TOTAL: " + order.getTotal());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Re-format all the orders into a printable form to be inserted into a JTable
     * Because the order may have a lot of details, only the order ID, the client name, the shipping address and the
     * total price would be shown
     *
     * @return A String matrix with the data corresponding to the previously mentioned values
     */
    public String[][] getAllOrders() {
        List<Order> orders = orderDAO.findAll();

        int count = 0;
        String[][] data = new String[orders.size()][];
        for (Order order: orders) {
            data[count] = new String[4];
            data[count][0] = Integer.toString(order.getOrderID());
            data[count][1] = clientDAO.findByID(order.getClientID()).getFirstName() + " " + clientDAO.findByID(order.getClientID()).getLastName();
            data[count][2] = order.getAddress();
            data[count][3] = Double.toString(order.getTotal());
            count++;
        }
        return data;
    }

    /**
     * Cancel an order by cancelling the bill
     *
     * @param order ID or the order to be cancelled
     * @throws IllegalArgumentException If the ID has an invalid format
     */
    public void cancelOrder(String order) throws IllegalArgumentException {
        OrderValidator.validateID(order);
        int orderID = Integer.parseInt(order);

        orderDetailDAO.delete(orderID);
        orderDAO.delete(orderID);

        String file = "######################### CANCELED ########################\n";
        try {
            File bill = new File("Order Bills/Order_" + orderID + ".txt");
            Scanner scanner = new Scanner(bill);
            while (scanner.hasNextLine()) {
                file = file + scanner.nextLine() + "\n";
            }
            scanner.close();

            FileWriter writer = new FileWriter("Order Bills/Order_" + orderID + ".txt");
            writer.write(file);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
