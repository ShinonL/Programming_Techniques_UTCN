package BusinessLayer;

import java.util.List;

public interface DeliveryServiceProcessing {
    /// ADMINISTRATOR
    /**
     * @pre true
     * @post menuItems.size() > 0
     */
    public void loadCvsFile();

    /**
     * @pre title != null && rating != null && calories != null && protein != null && fat != null && sodium != null && price != null
     * @post menuItems.contains(baseProduct)
     *
     * @param title name (title) of the new product
     * @param rating the rating given by the customers
     * @param calories number of calories of the product
     * @param protein protein of calories of the product
     * @param fat fat of calories of the product
     * @param sodium sodium of calories of the product
     * @param price price of calories of the product
     */
    public void createNewBaseProduct(String title, String rating, String calories, String protein, String fat, String sodium, String price);

    /**
     * @pre title != null && items != null
     * @pre !title.equals("") && items.size() != 0
     * @post menuItems.contains(compositeProduct)
     *
     * @param title name (title) of the new product
     * @param items list of the items which are part of the compound product
     */
    public void createCompositeProduct(String title, List<MenuItem> items);

    /**
     * @pre startHour >= 0 && startHour <= 23
     * @pre endHour >= 0 && endHour <= 23
     * @pre startHour < endHour
     * @post validOrders.size() != 0
     *
     * @param startHour the starting hour of the time interval
     * @param endHour the ending hour of the time interval
     */
    public void createHourlyReport(int startHour, int endHour);

    /**
     * @pre frequency > 0
     * @post validProducts.size() != 0
     *
     * @param frequency the minimum number a product has to be ordered
     */
    public void createFrequencyReport(int frequency);

    /**
     * @pre orderFrequency > 0 && minimumValue > 0
     * @post validClients.size() != 0
     *
     * @param orderFrequency the minimum number a client has to order something
     * @param minimumValue the minimum value of the order
     */
    public void createClientReport(int orderFrequency, double minimumValue);

    /**
     * @pre day > 0 && day <= 31
     * @pre month > 0 && month <= 12
     * @pre year > 0 && year <= LocalDateTime.now().getYear()
     * @post validProducts.size() != 0
     *
     * @param day the day of the month
     * @param month the month number
     * @param year the year
     */
    public void createDateReport(int day, int month, int year);
    /**
     * @pre items != null && items.size() > 0
     * @pre users.stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null) != null
     * @post orders.keySet().stream().filter(o -> o.getClientId().equals(username)).findAny().orElse(null) != null
     *
     * @param items items to be ordered
     * @param username client who ordered them
     */
    public void createOrder(List<MenuItem> items, String username);
    /**
     * @pre title != null && rating != null && calories != null && protein != null && fat != null && sodium != null && price != null
     * @post @nochange
     *
     * @param index index in the products list
     * @param title name (title) of the new product
     * @param rating the rating given by the customers
     * @param calories number of calories of the product
     * @param protein protein of calories of the product
     * @param fat fat of calories of the product
     * @param sodium sodium of calories of the product
     * @param price price of calories of the product
     */
    public void modifyProduct(int index, String title, String rating, String calories, String protein, String fat, String sodium, String price);

    /// CLIENT

    /**
     * @pre data != null && data.size() > 0
     * @pre keyword != null && !keyword.equals("")
     * @post return value.size() != 0
     *
     * @param data items to filter
     * @param keyword keyword based on which to filter
     * @return products that contain the keyword (case sensitive)
     */
    public List<MenuItem> findByKeyword(List<MenuItem> data, String keyword);

    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value bigger than the filtered items
     * @return products that have a rating smaller than the given value
     */
    public List<MenuItem> findByMaxRating(List<MenuItem> data, double value);

    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value smaller than the filtered items
     * @return products that have a rating bigger than the given value
     */
    public List<MenuItem> findByMinRating(List<MenuItem> data, double value);

    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value bigger than the filtered items
     * @return products that have a price smaller than the given value
     */
    public List<MenuItem> findByMaxPrice(List<MenuItem> data, double value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value smaller than the filtered items
     * @return products that have a price bigger than the given value
     */
    public List<MenuItem> findByMinPrice(List<MenuItem> data, double value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value bigger than the filtered items
     * @return products that have a calories smaller than the given value
     */
    public List<MenuItem> findByMaxCalories(List<MenuItem> data, int value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value smaller than the filtered items
     * @return products that have a calories bigger than the given value
     */
    public List<MenuItem> findByMinCalories(List<MenuItem> data, int value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value bigger than the filtered items
     * @return products that have a fat smaller than the given value
     */
    public List<MenuItem> findByMaxFat(List<MenuItem> data, int value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value smaller than the filtered items
     * @return products that have a fat bigger than the given value
     */
    public List<MenuItem> findByMinFat(List<MenuItem> data, int value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value bigger than the filtered items
     * @return products that have a protein smaller than the given value
     */
    public List<MenuItem> findByMaxProtein(List<MenuItem> data, int value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value smaller than the filtered items
     * @return products that have a protein bigger than the given value
     */
    public List<MenuItem> findByMinProtein(List<MenuItem> data, int value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value bigger than the filtered items
     * @return products that have a sodium smaller than the given value
     */
    public List<MenuItem> findByMaxSodium(List<MenuItem> data, int value);
    /**
     * @pre data != null && data.size() > 0
     * @pre value > 0
     * @post returnValue.size() != 0
     *
     * @param data items to filter
     * @param value value smaller than the filtered items
     * @return products that have a price sodium than the given value
     */
    public List<MenuItem> findByMinSodium(List<MenuItem> data, int value);
}
