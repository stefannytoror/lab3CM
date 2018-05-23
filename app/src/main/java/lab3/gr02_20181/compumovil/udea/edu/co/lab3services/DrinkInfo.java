package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;

/**
 * Created by personal on 18/05/18.
 */

public class DrinkInfo {
    private String uid , nombre , precio , ingredientes ;
    private String mImageUrl;

    public DrinkInfo(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public DrinkInfo(String uid, String nombre, String precio,String ingredientes,String mImageUrl) {
        this.uid = uid;
        this.nombre = nombre;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.mImageUrl = mImageUrl;
    }
}
