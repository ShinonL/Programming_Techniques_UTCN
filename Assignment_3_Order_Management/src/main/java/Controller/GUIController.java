package Controller;

import Presentation.GUI;

/**
 * The main menu controller class
 *
 * Decides what the presented buttons should do after clicking them
 */
public class GUIController {
    public static GUI gui = new GUI();

    public GUIController() {
        gui.start();
    }

    public void addActionListeners() {
        gui.orderButton.addActionListener(e -> {
            gui.frame.remove(gui.startPanel);
            OrderGUIController orderGUIController = new OrderGUIController(gui.frame);
            orderGUIController.addActionListeners();
        });
        gui.clientButton.addActionListener(e -> {
            gui.frame.remove(gui.startPanel);
            ClientGUIController clientGUIController = new ClientGUIController(gui.frame);
            clientGUIController.addActionListeners();
        });
        gui.productButton.addActionListener(e -> {
            gui.frame.remove(gui.startPanel);
            ProductGUIController productGUIController = new ProductGUIController(gui.frame);
            productGUIController.addActionListeners();
        });
    }
}
