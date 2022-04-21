package Controller;

/**
 * The main class which calls upon the user interface controller
 */
public class MainController {
    public static void main(String[] args) {
        GUIController guiController = new GUIController();
        guiController.addActionListeners();
    }
}
