package co.edu.udea.compumovil.gr02_20181.lab2;

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

import co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB;
import co.edu.udea.compumovil.gr02_20181.lab2.DB.UserStructure;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link co.edu.udea.compumovil.gr02_20181.lab2.RegisterUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link co.edu.udea.compumovil.gr02_20181.lab2.RegisterUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterUserFragment extends Fragment implements  View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String photo;

    public RegisterUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static co.edu.udea.compumovil.gr02_20181.lab2.RegisterUserFragment newInstance(String param1, String param2) {
        co.edu.udea.compumovil.gr02_20181.lab2.RegisterUserFragment fragment = new co.edu.udea.compumovil.gr02_20181.lab2.RegisterUserFragment();
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
        View view = inflater.inflate(R.layout.fragment_register_user,
                container, false);
        ((LoginActivity)getActivity()).setActionBarTitle("Registro");
        Button btnRegisterUser = (Button) view.findViewById(R.id.btn_registerUser);
        btnRegisterUser.setOnClickListener(this);
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

    public void onClick(View view){
        EditText txtNameUserRegister, txtEmailUserRegister, txtPasswordUserRegister;
        txtNameUserRegister = (EditText) getView().findViewById(R.id.txt_nameRegisterUser);
        txtEmailUserRegister = (EditText) getView().findViewById(R.id.txt_emailRegisterUser);
        txtPasswordUserRegister = (EditText) getView().findViewById(R.id.txt_passwordRegisterUser);


        String nameUserRegister, emailUserRegister, passwordUserRegister;
        nameUserRegister = txtNameUserRegister.getText().toString();
        emailUserRegister = txtEmailUserRegister.getText().toString();
        passwordUserRegister = txtPasswordUserRegister.getText().toString();

        if (nameUserRegister.equals("")  ||
                emailUserRegister.equals("") ||
                passwordUserRegister.equals("")||
                photo==null)
        {
            Toast.makeText(getContext(), "Falta Información", Toast.LENGTH_SHORT).show();

        } else {
            DbHelper dbHelper = new DbHelper(getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query1 = "SELECT " + RestaurantDB.ColumnUser.USER_NAME +
                            " FROM " + RestaurantDB.TABLE_USER +
                            " WHERE " + RestaurantDB.ColumnUser.USER_NAME + " = '" +
                                        nameUserRegister + "'";

            String query2 = "SELECT " + RestaurantDB.ColumnUser.USER_EMAIL +
                            " FROM " + RestaurantDB.TABLE_USER +
                            " WHERE " + RestaurantDB.ColumnUser.USER_EMAIL + " = '" +
                                        emailUserRegister + "'";

            Cursor c1 = db.rawQuery(query1, null);
            Cursor c2 = db.rawQuery(query2,null);

            if (c1.getCount()!=0||c2.getCount()!=0) {
                if(c1.getCount()!=0&&c2.getCount()!=0){
                    Toast.makeText(getContext(),"El correo y el nombre están en uso",Toast.LENGTH_SHORT).show();
                }else{
                    if(c1.getCount()!=0){
                        Toast.makeText(getContext(),"El nombre de usuario ya está en uso",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"El correo ya está en uso",Toast.LENGTH_SHORT).show();
                    }
                }
            } else {

                UserStructure user = new UserStructure(nameUserRegister,
                                                       emailUserRegister,
                                                       passwordUserRegister,
                                                       photo);

                db.insert(RestaurantDB.TABLE_USER,null,user.toContentValues());
                Log.d("tabla usuario", "onClick: inserto usuario ");

                //call startlogin fragment
                Fragment frag = new StartLoginFragment();
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_loginContainer,frag).commit();
            }

        }

    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
