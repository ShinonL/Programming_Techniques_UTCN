package BusinessLayer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import DataLayer.FileWriter;
import DataLayer.Serializator;

public class DeliveryService extends Observable implements DeliveryServiceProcessing, Serializable {
    public List<User> users = new ArrayList<>();
    public List<MenuItem> menuItems = new ArrayList<>();
    public Map<Order, List<MenuItem>> orders = new HashMap<>();
    public int orderId  = 0;

    private int hourlyReportCount = 0;
    private int frequencyCount = 0;
    private int clientCount = 0;
    private int dateCount = 0;


    @Override
    public void loadCvsFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/products.csv"));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                MenuItem menuItem = menuItems.stream()
                        .filter(item -> item.getTitle().equals(values[0]))
                        .findAny()
                        .orElse(null);
                if (menuItem == null)
                    addMenuItem(new BaseProduct(values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert menuItems.size() > 0;
    }

    @Override
    public void createNewBaseProduct(String title, String rating, String calories, String protein, String fat, String sodium, String price) throws IllegalArgumentException {
        assert !isWellFormed();
        assert title != null && rating != null && calories != null && protein != null && fat != null && sodium != null && price != null;

        BaseProduct baseProduct = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
        addMenuItem(baseProduct);

        assert menuItems.contains(baseProduct);
    }

    @Override
    public void createCompositeProduct(String title, List<MenuItem> items) throws IllegalArgumentException {
        assert !isWellFormed();
        assert title != null && items != null;
        assert !title.equals("") && items.size() != 0;

        CompositeProduct compositeProduct = new CompositeProduct(title, items);
        addMenuItem(compositeProduct);

        assert menuItems.contains(compositeProduct);
    }

    @Override
    public void createHourlyReport(int startHour, int endHour) {
        assert startHour >= 0 && startHour <= 23;
        assert endHour >= 0 && endHour <= 23;

        List<Order> validOrders = orders.keySet().stream()
                .filter(order -> order.getHour() >= startHour)
                .filter(order -> order.getHour() <= endHour)
                .collect(Collectors.toList());

        hourlyReportCount++;
        FileWriter.generateHourlyReport(validOrders, startHour, endHour, hourlyReportCount);

        Serializator serializator = new Serializator();
        serializator.serialize(this);
    }

    @Override
    public void createFrequencyReport(int frequency) {
        assert frequency > 0;

        Map<MenuItem, Integer> products = new HashMap<>();
        orders.forEach((order, items) -> items.forEach(item -> {
            if (products.containsKey(item))
                products.computeIfPresent(item, (i, v) -> v + 1);
            else products.put(item, 1);
        }));

        List<MenuItem> validProducts = products.entrySet().stream()
                .filter(entry -> entry.getValue() >= frequency)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        frequencyCount++;
        FileWriter.generateFrequency(validProducts, frequency, frequencyCount);

        Serializator serializator = new Serializator();
        serializator.serialize(this);
    }

    @Override
    public void createClientReport(int orderFrequency, double minimumValue) {
        assert orderFrequency > 0 && minimumValue > 0;

        Map<Order, Double> higherValues = new HashMap<>();
        orders.forEach((order, items) -> items.forEach(item -> {
            if (higherValues.containsKey(order))
                higherValues.computeIfPresent(order, (o, v) -> v + item.computePrice());
            else higherValues.put(order, item.computePrice());
        }));

        List<String> possibleClients = new ArrayList<>();
        higherValues.entrySet().stream()
                .filter(entry -> entry.getValue() >= orderFrequency)
                .forEach(entry -> {
                    possibleClients.add(entry.getKey().getClientId());
                });

        List<String> validClients = possibleClients.stream().distinct().collect(Collectors.toList());

        clientCount++;
        FileWriter.generateClient(validClients, orderFrequency, minimumValue, clientCount);

        Serializator serializator = new Serializator();
        serializator.serialize(this);
    }

    @Override
    public void createDateReport(int day, int month, int year) {
        assert day > 0 && day <= 31;
        assert month > 0 && month <= 12;
        assert year > 0 && year <= LocalDateTime.now().getYear();

        Map<String, Integer> validProducts = new HashMap<>();
        orders.entrySet().stream()
                .filter(entry ->
                        entry.getKey().getDate().getDayOfMonth() == day &&
                        entry.getKey().getDate().getMonthValue() == month &&
                        entry.getKey().getDate().getYear() == year)
                .forEach(items -> items.getValue()
                        .forEach(item -> {
                            if (validProducts.containsKey(item.getTitle()))
                                validProducts.computeIfPresent(item.getTitle(), (c, i) -> i + 1);
                            else validProducts.put(item.getTitle(), 1);
                }));

        dateCount++;
        FileWriter.generateDate(validProducts, day, month, year, dateCount);

        Serializator serializator = new Serializator();
        serializator.serialize(this);
    }

    @Override
    public List<MenuItem> findByKeyword(List<MenuItem> data, String keyword) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert keyword != null && !keyword.equals("");

        List<MenuItem> result = data.stream()
                .filter(item -> item.getTitle().contains(keyword))
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respeting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMaxRating(List<MenuItem> data, double value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeRating() <= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMinRating(List<MenuItem> data, double value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeRating() >= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMaxPrice(List<MenuItem> data, double value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computePrice() <= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMinPrice(List<MenuItem> data, double value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computePrice() >= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMaxCalories(List<MenuItem> data, int value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeCalories() <= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }
    @Override
    public List<MenuItem> findByMinCalories(List<MenuItem> data, int value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeCalories() >= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMaxFat(List<MenuItem> data, int value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeFat() <= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMinFat(List<MenuItem> data, int value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeFat() >= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMaxProtein(List<MenuItem> data, int value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeProtein() <= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMinProtein(List<MenuItem> data, int value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeProtein() >= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMaxSodium(List<MenuItem> data, int value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeSodium() <= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public List<MenuItem> findByMinSodium(List<MenuItem> data, int value) throws IllegalArgumentException {
        assert data != null && data.size() > 0;
        assert value > 0;

        List<MenuItem> result = data.stream()
                .filter(item -> item.computeSodium() >= value)
                .collect(Collectors.toList());

        if (result.size() == 0)
            throw new IllegalArgumentException("There are no products respecting the criteria");
        return result;
    }

    @Override
    public void createOrder(List<MenuItem> items, String username) {
        assert items != null && items.size() != 0;
        assert users.stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null) != null;

        orderId++;
        Order order = new Order(orderId, username);

        orders.put(order, items);
        FileWriter.generateBill(items, order);

        assert orders.keySet().stream().filter(o -> o.getClientId().equals(username)).findAny().orElse(null) != null;

        setChanged();
        notifyObservers(FileWriter.generateObserverMessage(items, order));
    }

    @Override
    public void modifyProduct(int index, String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        assert index < menuItems.size();
        assert title != null && rating != null && calories != null && protein != null && fat != null && sodium != null && price != null;

        menuItems.get(index).setAll(title, rating, calories, protein, fat, sodium, price);
    }

    /**
     * Add a user to the list of users
     *
     * @param user user to be added
     * @throws Exception If user already exists
     */
    public void addUser(User user) throws Exception {
        if (findByUsername(user.getUsername()) != null)
            throw new Exception("Username already taken");
        users.add(user);
    }

    /**
     * Find a user by the username
     *
     * @param username the username to search for
     * @return the found user
     */
    public User findByUsername(String username) {
        assert username != null && !username.equals("");
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        return user;
    }

    public void addMenuItem(MenuItem menuItem) throws IllegalArgumentException {
        assert menuItem != null;

        boolean validation = menuItems.stream()
                .anyMatch(item -> item.getTitle().equals(menuItem.getTitle()));
        if (validation)
            throw new IllegalArgumentException("This product already exists.");

        menuItems.add(menuItem);
        menuItems = menuItems.stream()
                .sorted(Comparator.comparing(MenuItem::getTitle))
                .collect(Collectors.toList());

        assert menuItems.contains(menuItem);
    }

    /**
     * Get all the products on the menu
     *
     * @return a list with the items
     */
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * @return whether the Delivery Service is well-formed or not
     */
    public boolean isWellFormed() {
        if (users == null)
            return false;
        if (menuItems == null)
            return false;
        if (orders == null)
            return false;
        if (orderId == 0)
            return false;

        if (menuItems.size() > 0)
            for (MenuItem item: menuItems) {
                if (item.getTitle() == null || item.getTitle().equals(""))
                    return false;
            }
        else if (orders.size() > 0)
            return false;

        long count = 0;
        if (orders.size() > 0)
            count = orders.entrySet().stream()
                    .filter(entry -> entry.getValue() != null && entry.getValue().size() == 0)
                    .count();
        if (count == 0)
            return false;

        if (users.size() == 0)
            return false;
        for (User user: users) {
            if (user.getRole() == null || user.getRole().equals(""))
                return false;
            if (user.getUsername() == null || user.getUsername().equals(""))
                return false;
            if (user.getPassword() == null || user.getPassword().equals(""))
                return false;
        }

        return true;
    }
}
