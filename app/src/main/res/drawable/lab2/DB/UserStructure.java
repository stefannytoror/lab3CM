package co.edu.udea.compumovil.gr02_20181.lab2.DB;

import android.content.ContentValues;


public class UserStructure {

    private String user_name, user_email, user_password, user_picture, user_state;


    public UserStructure(String user_name, String user_email, String user_password, String user_picture) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_picture = user_picture;
        this.user_state = "INACTIVO";
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB.ColumnUser.USER_NAME, user_name);
        values.put(co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB.ColumnUser.USER_EMAIL,user_email);
        values.put(co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB.ColumnUser.USER_PASSWORD, user_password);
        values.put(co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB.ColumnUser.USER_PICTURE, user_picture);
        values.put(co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB.ColumnUser.USER_STATE, user_state);
        return values;
    }

    //set

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public void setUserEmail(String user_email){ this.user_email = user_email; }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }

    public void setUserPicture(String user_picture) {
        this.user_picture = user_picture;
    }

    public void setUserState(String user_state) {
        this.user_state = user_state;
    }


    //get

    public String getUserName() {
        return user_name;
    }

    public String getUserEmail(){ return user_email; }

    public String getUserPassword() {
        return user_password;
    }

    public String getUserPicture() {
        return user_picture;
    }

    public String getUserState() {
        return user_state;
    }


}