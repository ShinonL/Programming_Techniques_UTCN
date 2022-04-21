package Controller;

import BusinessLayer.DeliveryService;
import BusinessLayer.User;
import DataLayer.Serializator;
import PresentationLayer.LoginGUI;
import Validators.UserValidator;

import javax.swing.*;

public class LoginController {
    LoginGUI loginGUI = new LoginGUI();
    DeliveryService service;

    /**
     * Create a new controller for the login window
     * 
     * @param service set the delivery service to the one which was deserialized
     */
    public LoginController(DeliveryService service) {
        this.service = service;
    }

    /**
     * Add the action listeners for the buttons in the login window
     */
    public void addActionListeners() {
        loginGUI.createAccount.addActionListener(e -> {
            loginGUI.createNewAccount();
        });
        loginGUI.submitCreateButton.addActionListener(e -> {
            String username = loginGUI.usernameField.getText();
            String password = new String(loginGUI.passwordField.getPassword());
            String role = loginGUI.roleBox.getSelectedItem().toString();

            User user = new User(username, password, role);
            try {
                UserValidator.validateUser(username, password);
                service.addUser(user);

                Serializator serializator = new Serializator();
                serializator.serialize(service);

                JOptionPane.showMessageDialog(null, "Account created.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginGUI.loginButton.addActionListener(e -> {
            loginGUI.loginAccount();
        });
        loginGUI.submitLoginButton.addActionListener(e -> {
            String username = loginGUI.usernameField.getText();
            String password = new String(loginGUI.passwordField.getPassword());

            try {
                User user = service.findByUsername(username);
                if(user == null)
                    throw new Exception("Username / Password incorrect");
                if (!user.getPassword().equals(password))
                    throw new Exception("Username / Password incorrect");

                if (service.findByUsername(username).getRole().equals("Administrator")) {
                    AdminController adminController = new AdminController(service);
                    adminController.addActionListeners();
                } else if (service.findByUsername(username).getRole().equals("Client")) {
                    ClientController clientController = new ClientController(service, username);
                    clientController.addActionListeners();
                } else if (service.findByUsername(username).getRole().equals("Employee")) {
                    EmployeeController employeeController = new EmployeeController(service);
                    //employeeController.addActionListeners();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginGUI.backButton.addActionListener(e -> {
            loginGUI.reset();
        });
    }
}
