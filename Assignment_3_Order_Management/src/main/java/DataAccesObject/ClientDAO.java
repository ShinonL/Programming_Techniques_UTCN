package DataAccesObject;

import Model.Client;

import java.util.List;

/**
 * The client data access object are defined here
 *
 * This class extends the genericDAO class. All the methods are done by calling the super class methods with the client
 * specific type of items
 */
public class ClientDAO extends GenericDAO<Client> {
    public ClientDAO() {
        super(Client.class);
    }

    public void insert(Client client) {
        super.insert(client);
    }

    public void delete(int ID){
        super.delete(ID, "clientID");
    }

    public List<Client> findAll() {
        return super.findAll();
    }

    public Client findByID(int ID) {
        return super.findByID(ID, "clientID");
    }

    public void update(int ID, List<Object> values) {
        super.update(ID, "clientID", values);
    }

    public List<Client> findByEmail(String email) {
        return super.findByString(email, "Email");
    }

    public List<Client> findByPhone(String phone) {
        return super.findByString(phone, "Email");
    }
}
