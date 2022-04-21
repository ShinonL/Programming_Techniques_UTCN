package Controller;

import BusinessLogic.ClientBLL;
import Presentation.ClientGUI;

import javax.swing.*;

/**
 * Controls all the buttons presented in the ClientGUI
 *
 * It calls upon an clientBLL to come and handle the requests made by the user
 */
public class ClientGUIController {
    private static ClientGUI clientGUI;
    private ClientBLL clientBLL;

    public ClientGUIController(JFrame frame) {
        clientBLL = new ClientBLL();

        clientGUI = new ClientGUI(frame);
        clientGUI.start();
    }
    public void addActionListeners() {
        clientGUI.insertButton.addActionListener(e -> {
            clientGUI.insertClient();
        });
        clientGUI.insertSubmitButton.addActionListener(e -> {
            String firstName = clientGUI.firstNameField.getText();
            String lastName = clientGUI.lastNameField.getText();
            String email = clientGUI.mailField.getText();
            String phone = clientGUI.phoneField.getText();

            try {
                clientBLL.createClient(firstName, lastName, email, phone);
                JOptionPane.showMessageDialog(null, "The client was created.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        clientGUI.deleteButton.addActionListener(e -> {
            clientGUI.deleteSearch();
        });
        clientGUI.deleteSearchButton.addActionListener(e -> {
            try {
                clientGUI.deleteClient(clientBLL.findClient(clientGUI.idField.getText()));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        clientGUI.deleteConfirmButton.addActionListener(e -> {
            try {
                clientBLL.deleteClient();
                JOptionPane.showMessageDialog(null, "The client was deleted.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        clientGUI.updateButton.addActionListener(e -> {
            clientGUI.updateSearch();
        });
        clientGUI.updateSearchButton.addActionListener(e -> {
            try {
                clientGUI.update(clientBLL.findClient(clientGUI.idField.getText()));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        clientGUI.updateConfirmButton.addActionListener(e -> {
            String firstName = clientGUI.firstNameField.getText();
            String lastName = clientGUI.lastNameField.getText();
            String email = clientGUI.mailField.getText();
            String phone = clientGUI.phoneField.getText();

            try {
                clientBLL.updateClient(firstName, lastName, email, phone);
                JOptionPane.showMessageDialog(null, "The client was updated.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        clientGUI.showButton.addActionListener(e -> {
            clientGUI.showAll(clientBLL.getAllClients());
        });

        clientGUI.backButton.addActionListener(e -> {
            clientGUI.frame.remove(clientGUI.clientPanel);
            GUIController.gui.reset();
        });
    }
}