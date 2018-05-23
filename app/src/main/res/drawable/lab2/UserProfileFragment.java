package co.edu.udea.compumovil.gr02_20181.lab2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile,
                container, false);
        ((co.edu.udea.compumovil.gr02_20181.lab2.NDRestaurant)getActivity()).setActionBarTitle("Perfil");


        TextView nameUserProfile , emailUserProfile;
        ImageView imgUserProfile;

        nameUserProfile = (TextView)view.findViewById(R.id.txtUserNameProfile);
        emailUserProfile = (TextView)view.findViewById(R.id.txtUserEmailProfile);
        imgUserProfile = (ImageView) view.findViewById(R.id.imgUserProfile);

        co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper dbHelper = new co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Bundle bundle = getActivity().getIntent().getExtras();

        String columns = RestaurantDB.ColumnUser.USER_NAME + ", " +
                RestaurantDB.ColumnUser.USER_EMAIL + ", " +
                RestaurantDB.ColumnUser.USER_PICTURE  ;

        email = bundle.getString(RestaurantDB.ColumnUser.USER_EMAIL);
        String query = "SELECT "+ columns +
                        " FROM " + RestaurantDB.TABLE_USER +
                        " WHERE "+ RestaurantDB.ColumnUser.USER_EMAIL + " = '"+ email +"'";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        nameUserProfile.setText("Usuario: "+c.getString(0));
        emailUserProfile.setText("Correo "+c.getString(1));
        Log.d("bd", "onCreateView: " + c.getString(0));
        Log.d("bd", "onCreateView: " + c.getString(1));
        Log.d("bd", "onCreateView: " + c.getString(2));
        byte[] picture_plate = Base64.decode(c.getString(2),Base64.DEFAULT);
        Bitmap bit = BitmapFactory.decodeByteArray(picture_plate, 0, picture_plate.length);
        //Bitmap aux = dbHelper.decodeString(c.getString(2));
        imgUserProfile.setImageBitmap(BitmapFactory.decodeByteArray(picture_plate,0,picture_plate.length));
        return view;
    }

}
