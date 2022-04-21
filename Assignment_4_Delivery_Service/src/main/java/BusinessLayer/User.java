package BusinessLayer;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String role;

    /**
     * Create a new user
     *
     * @param username an unique identification string
     * @param password password string
     * @param role "Administrator", "Employee" or "Client"
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }
}
