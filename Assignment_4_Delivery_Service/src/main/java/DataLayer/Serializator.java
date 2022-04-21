package DataLayer;

import BusinessLayer.DeliveryService;
import java.io.*;

public class Serializator {
    private String path = "src/main/resources/SerializedFiles/DeliveryService.ser";

    /**
     * Serialize the delivery service to save its information
     * @param service the object to be serialized
     */
    public void serialize(DeliveryService service) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(service);
            outputStream.close();

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize the file containing past information
     * @return the new delivery service file
     */
    public DeliveryService deserialize() {
        DeliveryService service = new DeliveryService();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

            service = (DeliveryService) inputStream.readObject();

            inputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return service;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return service;
    }
}
