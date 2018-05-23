package co.edu.udea.compumovil.gr02_20181.lab2.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr02_20181.lab2.R;


public class DbHelper  extends SQLiteOpenHelper {

    private Context cont;

    public DbHelper(Context context) {
        super(context, RestaurantDB.DB_RESTAURANT_NAME, null, RestaurantDB.DB_VERSION);
        this.cont = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //user table
        db.execSQL("CREATE TABLE " + RestaurantDB.TABLE_USER + " ("
                + RestaurantDB.ColumnUser.USER_NAME + " TEXT NOT NULL,"
                + RestaurantDB.ColumnUser.USER_EMAIL + " TEXT NOT NULL,"
                + RestaurantDB.ColumnUser.USER_PASSWORD + " TEXT NOT NULL,"
                + RestaurantDB.ColumnUser.USER_PICTURE + " TEXT,"
                + RestaurantDB.ColumnUser.USER_STATE + " TEXT NOT NULL,"
                + "UNIQUE (" + RestaurantDB.ColumnUser.USER_NAME + "),"
                + "PRIMARY KEY (" + RestaurantDB.ColumnUser.USER_EMAIL + "))");



        Bitmap user_picture = BitmapFactory.decodeResource(cont.getResources(), R.drawable.future);
        String user_pictureString = encodeImage(user_picture);

        UserStructure user = new UserStructure("a", "a@gmail.com", "a", user_pictureString);
        db.insert(RestaurantDB.TABLE_USER, null, user.toContentValues());


        //drinks table

        db.execSQL("CREATE TABLE " + RestaurantDB.TABLE_DRINKS + " ("
                + RestaurantDB.ColumnDrinks.DRINK_NAME + " TEXT NOT NULL,"
                + RestaurantDB.ColumnDrinks.DRINK_PRICE + " INT NOT NULL,"
                + RestaurantDB.ColumnDrinks.DRINK_INGREDIENTS + " TEXT NOT NULL,"
                + RestaurantDB.ColumnDrinks.DRINK_PICTURE + " TEXT,"
                + "UNIQUE (" + RestaurantDB.ColumnDrinks.DRINK_NAME + "),"
                + "PRIMARY KEY (" + RestaurantDB.ColumnDrinks.DRINK_NAME + "))");
        Bitmap imageBitmap;
        String picture;

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(), R.drawable.bebida2);
        picture = encodeImage(imageBitmap);
        DrinksStructure drink1 = new DrinksStructure("Limonada",2500,
                "Limón, agua y azucar",picture);

        //db.insert(RestaurantDB.TABLE_DRINKS, null, drink2.toContentValues());
        db.insert(RestaurantDB.TABLE_DRINKS, null, drink1.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(), R.drawable.bebida1);
        picture = encodeImage(imageBitmap);
        DrinksStructure drink2 = new DrinksStructure("Jugo de Fresa",2500,
                "Pulpa de fresa y agua",picture);
        db.insert(RestaurantDB.TABLE_DRINKS, null, drink2.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(), R.drawable.bebida3);
        picture = encodeImage(imageBitmap);
        DrinksStructure drink3 = new DrinksStructure("Batido de coco",2500,
                "Agua de coco, coco rayado y agua",picture);
        db.insert(RestaurantDB.TABLE_DRINKS, null, drink3.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(), R.drawable.bebida4);
        picture = encodeImage(imageBitmap);
        DrinksStructure drink4 = new DrinksStructure("Jugo Tropical",2500,
                "Pulpa de frutas, frutiño y agua",picture);
        db.insert(RestaurantDB.TABLE_DRINKS, null, drink4.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(), R.drawable.bebida5);
        picture = encodeImage(imageBitmap);
        DrinksStructure drink5 = new DrinksStructure("Brebaje Rojo",2500,
                "Pulpa de frutos rojos y agua",picture);
        db.insert(RestaurantDB.TABLE_DRINKS, null, drink5.toContentValues());


        //Plates table
        db.execSQL("CREATE TABLE " + RestaurantDB.TABLE_PLATES + " ("
                + RestaurantDB.ColumnPlates.PLATE_NAME + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_SCHEDULE + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_TYPE + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_PRICE + " INT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_TIME + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_INGREDIENTS + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_PICTURE + " TEXT,"
                + "UNIQUE (" + RestaurantDB.ColumnPlates.PLATE_NAME + "),"
                + "PRIMARY KEY (" + RestaurantDB.ColumnPlates.PLATE_NAME + "))");



        imageBitmap = BitmapFactory.decodeResource(cont.getResources(),R.drawable.plato1);
        picture = encodeImage(imageBitmap);
        co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure plate1 = new co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure("Chuzo de carne",
                "Tarde",
                "Entrada", 3000,"02:15",
                "Carne , lechuga tomate", picture);
        db.insert(RestaurantDB.TABLE_PLATES, null, plate1.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(),R.drawable.plato2);
        picture = encodeImage(imageBitmap);
        co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure plate2 = new co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure("Nueva delicia",
                "Mañana",
                "Entrada", 3000,"02:15",
                "harina,huevos,granos", picture);
        db.insert(RestaurantDB.TABLE_PLATES, null, plate2.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(),R.drawable.plato3);
        picture = encodeImage(imageBitmap);
        co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure plate3 = new co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure("sancocho",
                "Tarde",
                "Plato fuerte", 3000,"05:15",
                "mazorca,agua, papa,platano", picture);
        db.insert(RestaurantDB.TABLE_PLATES, null, plate3.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(),R.drawable.plato4);
        picture = encodeImage(imageBitmap);
        co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure plate4 = new co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure("Carne con ensalada",
                "Noche",
                "Plato fuerte", 3000,"02:15",
                "carne, salsa de la casa, lechuga y tomate", picture);
        db.insert(RestaurantDB.TABLE_PLATES, null, plate4.toContentValues());


        imageBitmap = BitmapFactory.decodeResource(cont.getResources(),R.drawable.plato5);
        picture = encodeImage(imageBitmap);
        co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure plate5 = new co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure("Pasta",
                "Noche",
                "Plato fuerte", 3000,"02:15",
                "Pasta y salsa", picture);
        db.insert(RestaurantDB.TABLE_PLATES, null, plate5.toContentValues());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String encodeImage(Bitmap bit) {
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, temp);
        byte[] p = temp.toByteArray();
        return (Base64.encodeToString(p, Base64.DEFAULT));
    }

    public Bitmap decodeString(String code) {
        byte[] temp = Base64.decode(code, Base64.DEFAULT);
        return (BitmapFactory.decodeByteArray(temp, 0, temp.length));
    }

    public List<DrinksStructure> drinksList() {
        SQLiteDatabase db = getReadableDatabase();
        List<DrinksStructure> listDrinks = new ArrayList<DrinksStructure>();
        String[] columns = {
                RestaurantDB.ColumnDrinks.DRINK_NAME,
                RestaurantDB.ColumnDrinks.DRINK_PRICE,
                RestaurantDB.ColumnDrinks.DRINK_INGREDIENTS,
                RestaurantDB.ColumnDrinks.DRINK_PICTURE};
        Cursor cursor = db.query(RestaurantDB.TABLE_DRINKS, columns, null, null,
                null, null, null, null);
        cursor.moveToFirst();
        do {
            DrinksStructure drinkObtain = new DrinksStructure(
                    cursor.getString(0), cursor.getInt(1), cursor.getString(2),
                    cursor.getString(3));
            listDrinks.add(drinkObtain);
        } while (cursor.moveToNext());
        db.close();
        cursor.close();
        return listDrinks;
    }

    public List<co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure> platesList() {
        SQLiteDatabase db = getReadableDatabase();
        List<co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure> listPlates = new ArrayList<co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure>();
        String[] columns = {
                RestaurantDB.ColumnPlates.PLATE_NAME,
                RestaurantDB.ColumnPlates.PLATE_SCHEDULE,
                RestaurantDB.ColumnPlates.PLATE_TYPE,
                RestaurantDB.ColumnPlates.PLATE_PRICE,
                RestaurantDB.ColumnPlates.PLATE_TIME,
                RestaurantDB.ColumnPlates.PLATE_INGREDIENTS,
                RestaurantDB.ColumnPlates.PLATE_PICTURE};
        Cursor cursor = db.query(RestaurantDB.TABLE_PLATES, columns, null, null,
                null, null, null, null);
        cursor.moveToFirst();
        do {
            co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure drinkObtain = new co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure(
                    cursor.getString(0),cursor.getString(1),cursor.getString(2),
                    cursor.getInt(3),cursor.getString(4), cursor.getString(5),
                    cursor.getString(6));
            listPlates.add(drinkObtain);
        } while (cursor.moveToNext());
        db.close();
        cursor.close();
        return listPlates;
    }



}
