package co.edu.udea.compumovil.gr02_20181.lab2.DB;

import android.content.ContentValues;

/**
 * Created by santiago.molinae on 23/03/18.
 */

public class PlatesStructure {
    private String plate_name, plate_ingredients,plate_picture,plate_type,plate_time,plate_schedule;
    private int plate_price;

    public PlatesStructure(String plate_name,String plate_schedule,String plate_type,int plate_price,
                           String plate_time, String plate_ingredients, String plate_picture) {
        this.plate_name = plate_name;
        this.plate_schedule = plate_schedule;
        this.plate_type = plate_type;
        this.plate_price = plate_price;
        this.plate_time = plate_time;
        this.plate_ingredients = plate_ingredients;
        this.plate_picture = plate_picture;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RestaurantDB.ColumnPlates.PLATE_NAME, plate_name);
        values.put(RestaurantDB.ColumnPlates.PLATE_SCHEDULE, plate_schedule);
        values.put(RestaurantDB.ColumnPlates.PLATE_TYPE, plate_type);
        values.put(RestaurantDB.ColumnPlates.PLATE_PRICE, plate_price);
        values.put(RestaurantDB.ColumnPlates.PLATE_TIME, plate_time);
        values.put(RestaurantDB.ColumnPlates.PLATE_INGREDIENTS, plate_ingredients);
        values.put(RestaurantDB.ColumnPlates.PLATE_PICTURE, plate_picture);
        return values;
    }

    public String getPlate_name() {
        return plate_name;
    }

    public void setPlate_name(String plate_name) {
        this.plate_name = plate_name;
    }

    public String getPlate_ingredients() {
        return plate_ingredients;
    }

    public void setPlate_ingredients(String plate_ingredients) {
        this.plate_ingredients = plate_ingredients;
    }

    public String getPlate_picture() {
        return plate_picture;
    }

    public void setPlate_picture(String plate_picture) {
        this.plate_picture = plate_picture;
    }

    public String getPlate_type() {
        return plate_type;
    }

    public void setPlate_type(String plate_type) {
        this.plate_type = plate_type;
    }

    public String getPlate_time() {
        return plate_time;
    }

    public void setPlate_time(String plate_time) {
        this.plate_time = plate_time;
    }

    public String getPlate_schedule() {
        return plate_schedule;
    }

    public void setPlate_schedule(String plate_schedule) {
        this.plate_schedule = plate_schedule;
    }

    public int getPlate_price() {
        return plate_price;
    }

    public void setPlate_price(int plate_price) {
        this.plate_price = plate_price;
    }
}
