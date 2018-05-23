package co.edu.udea.compumovil.gr02_20181.lab2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_GALLERY = 98;
    String email;
    long Delay = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_loginContainer, new SplashFragment());
        ft.commit();
        getSupportActionBar().hide();

        // Create a Timer
        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        final TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {

                co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper dbHelper = new co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT "+ RestaurantDB.ColumnUser.USER_EMAIL +
                        " FROM " + RestaurantDB.TABLE_USER +
                        " WHERE " + RestaurantDB.ColumnUser.USER_STATE+ " = 'ACTIVO' " , null);


                if(c.moveToFirst()){
                    email = c.getString(0);
                    Intent other = new Intent(getApplicationContext(), co.edu.udea.compumovil.gr02_20181.lab2.NDRestaurant.class);
                    Bundle bundleP = new Bundle();
                    bundleP.putString(RestaurantDB.ColumnUser.USER_EMAIL,email);
                    other.putExtras(bundleP);
                    finish();
                    startActivity(other);


                }else{
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_loginContainer, new StartLoginFragment());
                    ft.commit();
                }
            }
        };
        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }

    public void photoGallery(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, REQUEST_IMAGE_GALLERY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper db = new co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper(getApplicationContext());
                co.edu.udea.compumovil.gr02_20181.lab2.RegisterUserFragment frag = (co.edu.udea.compumovil.gr02_20181.lab2.RegisterUserFragment) getSupportFragmentManager().findFragmentByTag("registerUserFragment");

                if (frag != null) {
                    String TAG = "que";
                    Log.d(TAG, "onActivityResult: guardo la imagen");
                    frag.setPhoto(db.encodeImage(selectedImage));

                }
            } catch (IOException e) {
            }

        }
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
