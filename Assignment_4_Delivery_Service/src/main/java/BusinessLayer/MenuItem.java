package BusinessLayer;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {

    /**
     * Create a menu item. Am abstract product that is either a base product or a composite product
     */
    protected MenuItem() { }

    /**
     * Abstract method for computing the rating of a product
     * @return rating
     */
    public abstract double computeRating();

    /**
     * Abstract method for computing the calories of a product
     * @return calories
     */
    public abstract int computeCalories();

    /**
     * Abstract method for computing the proteins of a product
     * @return proteins
     */
    public abstract int computeProtein();

    /**
     * Abstract method for computing the fats of a product
     * @return fats
     */
    public abstract int computeFat();

    /**
     * Abstract method for computing the sodium of a product
     * @return sodium
     */
    public abstract int computeSodium();

    /**
     * Abstract method for computing the price of a product
     * @return price
     */
    public abstract double computePrice();

    /**
     * Abstract method for getting the title of a product
     * @return title
     */
    public abstract String getTitle();

    /**
     * Abstract method for seting the fields of a base product
     */
    public abstract void setAll(String title, String rating, String calories, String protein, String fat, String sodium, String price);
}
