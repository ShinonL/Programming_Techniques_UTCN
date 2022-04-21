package Controller;

import BusinessLayer.DeliveryService;
import DataLayer.Serializator;

public class Application {
    /**
     * The main function to run the application
     * @param args are ignored
     */
    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        DeliveryService service = serializator.deserialize();

        LoginController controller = new LoginController(service);
        controller.addActionListeners();
    }
}
