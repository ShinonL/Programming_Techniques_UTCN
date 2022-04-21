package BusinessLogic;

import DataAccesObject.ClientDAO;
import DataAccesObject.OrderDAO;
import Model.Client;
import Validators.ClientValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Based on the request of the controller, it manages the raw input given, validates it, transforms it into essential
 * client data and then applies one of the CRUD operations
 */
public class ClientBLL {
    private ClientDAO clientDAO;
    private int clientID;

    /**
     * Initializes the client data access object
     */
    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    /**
     * Validates the ID given by the user
     * If found to be correct, it searches for the corresponding client
     * @param ID The id given by the user
     * @return A client object with the given ID
     * @throws IllegalArgumentException If the ID was invalid
     */
    public Client findClient(String ID) throws IllegalArgumentException{
        ClientValidator.validateID(ID);

        setClientID(ID);
        return clientDAO.findByID(clientID);
    }

    /**
     * Returns all the clients for JTable insertion format
     * @return A String matrix with the data corresponding to the clients
     */
    public String[][] getAllClients() {
        List<Client> clients = clientDAO.findAll();

        int count = 0;
        String[][] data = new String[clients.size()][];
        for (Client client: clients) {
            data[count] = new String[5];
            data[count][0] = Integer.toString(client.getClientID());
            data[count][1] = client.getFirstName();
            data[count][2] = client.getLastName();
            data[count][3] = client.getEmail();
            data[count][4] = client.getPhone();
            count++;
        }
        return data;
    }

    /**
     * Insert into the database a client with the given fields.
     * All of the data is checked before continuing with the insertion
     * @param firstName The first name of the new Client
     * @param lastName The last name of the new Client
     * @param email The email address
     * @param phone The phone number
     * @throws IllegalArgumentException If any of the fields has an invalid format
     */
    public void createClient(String firstName, String lastName, String email, String phone) throws IllegalArgumentException {
        ClientValidator.validateInput(firstName, lastName, email, phone);

        clientDAO.insert(new Client(firstName, lastName, email, phone));
    }

    /**
     * Deletes a client with the ID saved in the clientID attribute of the class
     * @throws Exception If the client has links with any previous orders
     */
    public void deleteClient() throws Exception{
        OrderDAO orderDAO = new OrderDAO();
        if (orderDAO.findByClientID(clientID) != null)
            throw new Exception("Cannot remove this client because he/she made some orders");

        clientDAO.delete(clientID);
    }

    /**
     * Updates an existing client. The client was previously searched so its identification code is saved in the
     * clientID attribute of the class.
     *
     * The new fields may have the same value as the previous ones
     *
     * @param firstName The new first name
     * @param lastName The new last name
     * @param email The new email address
     * @param phone The new phone number
     */
    public void updateClient(String firstName, String lastName, String email, String phone) {
        ClientValidator.validateInput(firstName, lastName, email, phone);

        List<Object> values = new ArrayList<>();
        values.add(firstName);
        values.add(lastName);
        values.add(email);
        values.add(phone);

        clientDAO.update(clientID, values);
    }

    /**
     * Sets the value of the attribute clientID
     * @param ID String containing the ID
     */
    public void setClientID(String ID) {
        clientID = Integer.parseInt(ID);
    }
}
