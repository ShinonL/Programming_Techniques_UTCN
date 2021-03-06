package Model;

/**
 * A mirror of the "client" table from the "schooldb" database.
 * It is the internal representation of the table.
 */
public class Client {
    private int clientID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    /**
     * The empty constructor is used by the SQL queries.
     * It creates an instance of a client without initializing its fields.
     */
    public Client() {}

    /**
     * A parameterized constructor is used to create an instance of a client with the following pre-set fields.
     * Besides these parameters, a client also has a unique identifying ID (the "clientID" field).
     * This field is automatically generated by the inset query into the database
     *
     * The pre-set parameters are:
     * @param firstName This is the first name of the client. It is saved as a STRING value.
     *                  As in real life, the name values may be the same for more people, so this is not unique.
     *
     * @param lastName This is the last name of the client. It is saved as a STRING value.
     *                 Another name, which means it may be unique.
     *
     * @param email This is the email of the client. It is saved as a STRING value/
     *              An email is a unique way of identification. This means that there may be just one client with this
     *              specific email address.
     *
     * @param phone This is the phone number of the client. It is saved as a STRING value.
     *              Just as an email belongs to a single person, so does a phone number. This field must also be unique.
     */
    public Client(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public int getClientID() {
        return clientID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
