package BusinessLayer;

public class BaseProduct extends MenuItem {
    protected String title;
    protected double rating;
    protected int calories;
    protected int protein;
    protected int fat;
    protected int sodium;
    protected double price;

    /**
     * Create a base product with the known fields
     *
     * @param title String with the title
     * @param rating double with the rating
     * @param calories int with the calories
     * @param protein int with the protein
     * @param fat int with the fat
     * @param sodium int with the sodiium
     * @param price double with the price
     */
    public BaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, double price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    /**
     * Create a base product with the known string values of the fields
     *
     * @param title String with the title
     * @param rating String with the rating
     * @param calories String with the calories
     * @param protein String with the protein
     * @param fat String with the fat
     * @param sodium String with the sodium
     * @param price String with the price
     */
    public BaseProduct(String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        this.title = title;
        this.rating = Double.parseDouble(rating);
        this.calories = Integer.parseInt(calories);
        this.protein = Integer.parseInt(protein);
        this.fat = Integer.parseInt(fat);
        this.sodium = Integer.parseInt(sodium);
        this.price = Double.parseDouble(price);
    }

    /**
     * Getter for the rating
     *
     * @return rating
     */
    @Override
    public double computeRating() {
        return rating;
    }

    /**
     * Getter for the calories
     *
     * @return calories
     */
    @Override
    public int computeCalories() {
        return calories;
    }

    /**
     * Getter for the protein
     *
     * @return protein
     */
    @Override
    public int computeProtein() {
        return protein;
    }

    /**
     * Getter for the fat
     *
     * @return fat
     */
    @Override
    public int computeFat() {
        return fat;
    }

    /**
     * Getter for the sodium
     *
     * @return sodium
     */
    @Override
    public int computeSodium() {
        return sodium;
    }

    /**
     * @return title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the price
     *
     * @return price
     */
    @Override
    public double computePrice() {
        return price;
    }

    /**
     * Set all the fields of am existing product with new values
     *
     * @param title String with the title
     * @param rating String with the rating
     * @param calories String with the calories
     * @param protein String with the protein
     * @param fat String with the fat
     * @param sodium String with the sodium
     * @param price String with the price
     */
    public void setAll(String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        this.title = title;
        this.rating = Double.parseDouble(rating);
        this.calories = Integer.parseInt(calories);
        this.protein = Integer.parseInt(protein);
        this.fat = Integer.parseInt(fat);
        this.sodium = Integer.parseInt(sodium);
        this.price = Double.parseDouble(price);
    }
}
