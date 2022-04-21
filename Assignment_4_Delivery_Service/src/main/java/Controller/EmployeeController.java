package Controller;

import BusinessLayer.DeliveryService;
import PresentationLayer.EmployeeGUI;

import java.util.Observable;
import java.util.Observer;

public class EmployeeController implements Observer {
    EmployeeGUI employeeGUI = new EmployeeGUI();
    DeliveryService service;

    /**
     * Create a new controller for an employee
     * @param service the delivery service with the delivery information
     */
    public EmployeeController(DeliveryService service) {
        this.service = service;
        service.addObserver(this);
    }

    /**
     * The update method of the Observer
     * @param o the object for which the modification was observed
     * @param arg the arguments given as a notification for the modification
     */
    @Override
    public void update(Observable o, Object arg) {
        employeeGUI.addOrder((String) arg);
    }
}
