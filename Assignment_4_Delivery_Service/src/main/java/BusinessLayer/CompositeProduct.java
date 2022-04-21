package BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem {
    private String title;
    private List<MenuItem> products = new ArrayList<>();

    /**
     * Create a new composite product
     *
     * @param title Title of the new product
     * @param products list of items contained by the product
     */
    public CompositeProduct(String title, List<MenuItem> products) {
        this.products = products;
        this.title = title;
    }

    /**
     * Getter for the title
     *
     * @return title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Compute the price based on the prices of the items list
     *
     * @return the total value
     */
    @Override
    public double computePrice() {
        double price = 0;
        for (MenuItem product: products) {
            price += product.computePrice();
        }
        return price;
    }

    /**
     * Compute the rating as an average of the ratings of the items list
     *
     * @return average rating
     */
    @Override
    public double computeRating() {
        double rating = 0;
        for (MenuItem product: products) {
            rating += product.computeRating();
        }
        return rating / products.size();
    }

    /**
     * Compute the calories based on the calories of the items list
     *
     * @return total calories
     */
    @Override
    public int computeCalories() {
        int calories = 0;
        for (MenuItem product: products) {
            calories += product.computeCalories();
        }
        return calories;
    }

    /**
     * Compute the proteins based on the proteins of the items list
     *
     * @return total proteins
     */
    @Override
    public int computeProtein() {
        int proteins = 0;
        for (MenuItem product: products) {
            proteins += product.computeProtein();
        }
        return proteins;
    }

    /**
     * Compute the fats based on the fats of the items list
     *
     * @return total fats
     */
    @Override
    public int computeFat() {
        int fats = 0;
        for (MenuItem product: products) {
            fats += product.computeFat();
        }
        return fats;
    }

    /**
     * Compute the sodium based on the sodium of the items list
     *
     * @return total sodium
     */
    @Override
    public int computeSodium() {
        int sodium = 0;
        for (MenuItem product: products) {
            sodium += product.computeSodium();
        }
        return sodium;
    }

    /**
     * Unused
     *
     * @param title
     * @param rating
     * @param calories
     * @param protein
     * @param fat
     * @param sodium
     * @param price
     */
    @Override
    public void setAll(String title, String rating, String calories, String protein, String fat, String sodium, String price) {

    }
}
