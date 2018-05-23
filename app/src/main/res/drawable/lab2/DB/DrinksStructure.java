package co.edu.udea.compumovil.gr02_20181.lab2.DB;

import android.content.ContentValues;

/**
 * Created by personal on 18/03/18.
 */

public class DrinksStructure {
    private String drink_name, drink_ingredients,drink_picture;
    private int drink_price;

    public DrinksStructure(String drink_name, int drink_price, String drink_ingredients, String drink_picture) {
        this.drink_name = drink_name;
        this.drink_price = drink_price;
        this.drink_ingredients = drink_ingredients;
        this.drink_picture = drink_picture;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RestaurantDB.ColumnDrinks.DRINK_NAME, drink_name);
        values.put(RestaurantDB.ColumnDrinks.DRINK_PRICE, drink_price);
        values.put(RestaurantDB.ColumnDrinks.DRINK_INGREDIENTS, drink_ingredients);
        values.put(RestaurantDB.ColumnDrinks.DRINK_PICTURE, drink_picture);
        return values;
    }

    public String getDrink_name() {
        return drink_name;
    }

    public void setDrink_name(String drink_name) {
        this.drink_name = drink_name;
    }

    public String getDrink_ingredients() {
        return drink_ingredients;
    }

    public void setDrink_ingredients(String drink_ingredients) {
        this.drink_ingredients = drink_ingredients;
    }

    public String getDrink_picture() {
        return drink_picture;
    }

    public void setDrink_picture(String drink_picture) {
        this.drink_picture = drink_picture;
    }

    public int getDrink_price() {
        return drink_price;
    }

    public void setDrink_price(int drink_price) {
        this.drink_price = drink_price;
    }


}
