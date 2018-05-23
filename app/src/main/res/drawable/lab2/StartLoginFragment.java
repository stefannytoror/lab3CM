package co.edu.udea.compumovil.gr02_20181.lab2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link co.edu.udea.compumovil.gr02_20181.lab2.StartLoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link co.edu.udea.compumovil.gr02_20181.lab2.StartLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartLoginFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button btnCreateAccount, btnSignIn;
    private String email;

    public StartLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static co.edu.udea.compumovil.gr02_20181.lab2.StartLoginFragment newInstance(String param1, String param2) {
        co.edu.udea.compumovil.gr02_20181.lab2.StartLoginFragment fragment = new co.edu.udea.compumovil.gr02_20181.lab2.StartLoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start_login,
                container, false);
        ((co.edu.udea.compumovil.gr02_20181.lab2.LoginActivity)getActivity()).setActionBarTitle("Iniciar sesión");


        btnSignIn = (Button) view.findViewById(R.id.btn_SignIn);
        btnSignIn.setOnClickListener(this);

        btnCreateAccount = view.findViewById(R.id.btn_createAccountUser);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new co.edu.udea.compumovil.gr02_20181.lab2.RegisterUserFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_loginContainer,frag,"registerUserFragment").addToBackStack(null).commit();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {

        EditText txtNameUser, txtPasswordUser;
        String user = "", password = "";
        txtNameUser = (EditText) getView().findViewById(R.id.txt_nameUser);
        txtPasswordUser = (EditText) getView().findViewById(R.id.txt_passwordUser);

        user = txtNameUser.getText().toString();
        password = txtPasswordUser.getText().toString();

        if (user.equals("") || password.equals("")) {
            Toast.makeText(getContext(),"Información Incompleta",Toast.LENGTH_SHORT).show();

        }else{
            co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper dbHelper = new co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper(getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT "+ RestaurantDB.ColumnUser.USER_EMAIL +
                    " FROM " + RestaurantDB.TABLE_USER +
                    " WHERE "+ RestaurantDB.ColumnUser.USER_NAME + " = '"+ user +
                    "' AND " + RestaurantDB.ColumnUser.USER_PASSWORD + " = '"+ password +"'", null);

            if (c.moveToFirst()){
                // save email for use to savedInstance
                email = c.getString(0);
                Log.d("DB", "onClick: login " + email);

                ContentValues contentValues = new ContentValues();
                contentValues.put(RestaurantDB.ColumnUser.USER_STATE,"ACTIVO");
                db.updateWithOnConflict(RestaurantDB.TABLE_USER,contentValues,
                        RestaurantDB.ColumnUser.USER_NAME + "='" + user + "'",null,SQLiteDatabase.CONFLICT_IGNORE);
                Intent other = new Intent(getActivity().getApplicationContext(), co.edu.udea.compumovil.gr02_20181.lab2.NDRestaurant.class);
                Bundle bundleP = new Bundle();
                onSaveInstanceState(bundleP);
                other.putExtras(bundleP);
                getActivity().finish();
                startActivity(other);


            }else {
                Toast.makeText(getContext(),"Usuario o Contraseña Incorrecta No estas registrado? Crea una cuenta ",Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(RestaurantDB.ColumnUser.USER_EMAIL,email);

    }
}
