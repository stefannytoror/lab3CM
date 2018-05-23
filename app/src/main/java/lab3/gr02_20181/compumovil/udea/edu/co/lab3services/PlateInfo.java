package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;

public class PlateInfo {

    private String uid, namePlate,ingridientsPlate, schedulePlate, typePlate, timePlate, mImageUrl;
    private int pricePlate;

    public PlateInfo() {
        //Empty constructor
    }

    public PlateInfo(String uid,
                     String namePlate,
                     String ingridientsPlate,
                     String schedulePlate,
                     String typePlate,
                     String timePlate,
                     String mImageUrl,
                     int pricePlate) {
        this.uid = uid;
        this.namePlate = namePlate;
        this.ingridientsPlate = ingridientsPlate;
        this.schedulePlate = schedulePlate;
        this.typePlate = typePlate;
        this.timePlate = timePlate;
        this.mImageUrl = mImageUrl;
        this.pricePlate = pricePlate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNamePlate() {
        return namePlate;
    }

    public void setNamePlate(String namePlate) {
        this.namePlate = namePlate;
    }

    public String getIngridientsPlate() {
        return ingridientsPlate;
    }

    public void setIngridientsPlate(String ingridientsPlate) {
        this.ingridientsPlate = ingridientsPlate;
    }

    public String getSchedulePlate() {
        return schedulePlate;
    }

    public void setSchedulePlate(String schedulePlate) {
        this.schedulePlate = schedulePlate;
    }

    public String getTypePlate() {
        return typePlate;
    }

    public void setTypePlate(String typePlate) {
        this.typePlate = typePlate;
    }

    public String getTimePlate() {
        return timePlate;
    }

    public void setTimePlate(String timePlate) {
        this.timePlate = timePlate;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public int getPricePlate() {
        return pricePlate;
    }

    public void setPricePlate(int pricePlate) {
        this.pricePlate = pricePlate;
    }
}
