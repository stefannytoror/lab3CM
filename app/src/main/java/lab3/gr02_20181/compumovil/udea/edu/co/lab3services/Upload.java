package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;

/**
 * Created by santiago.molinae on 19/05/18.
 */

public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload() {

    }

    public Upload(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }



    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
