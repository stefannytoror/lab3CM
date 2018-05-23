package co.edu.udea.compumovil.gr02_20181.lab2;

import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link co.edu.udea.compumovil.gr02_20181.lab2.AddPlatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link co.edu.udea.compumovil.gr02_20181.lab2.AddPlatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlatesFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // photo
    private String photo;

    //informacion del picker
    private TextView mTimeDisplay;
    private int mHour;
    private int mMinute;

    public AddPlatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPlatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static co.edu.udea.compumovil.gr02_20181.lab2.AddPlatesFragment newInstance(String param1, String param2) {
        co.edu.udea.compumovil.gr02_20181.lab2.AddPlatesFragment fragment = new co.edu.udea.compumovil.gr02_20181.lab2.AddPlatesFragment();
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
        //Organizar cuando se haga el layout

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_plates,
                container, false);
        ((co.edu.udea.compumovil.gr02_20181.lab2.NDRestaurant)getActivity()).setActionBarTitle("Agregar Plato");

        Button btntimePlate = (Button) view.findViewById(R.id.btnTiempoPlato);
        btntimePlate.setOnClickListener(this);

        Button btnaddPlateButton = (Button) view.findViewById(R.id.btnAgregarPlato);
        btnaddPlateButton.setOnClickListener(this);

        mTimeDisplay = (TextView)view.findViewById(R.id.txtTiempoPlato);


        updateDisplay();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void updateDisplay() {
        mTimeDisplay.setText(new StringBuilder()
                .append(String.format("%02d",mHour)).append(":").append(String.format("%02d", mMinute)));


    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        updateDisplay();
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnTiempoPlato:
                DialogFragment timePickerFragment = new co.edu.udea.compumovil.gr02_20181.lab2.TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), "timePicker");
                break;

            case R.id.btnAgregarPlato:
                addPlate();
                break;

        }
    }

    public void setPhoto(String photo){this.photo = photo;}

    public void addPlate(){
        EditText addPlate;
        TextView addtimePlate;
        String namePlateDB, schedulePlateDB,typePlateDB, pricePlateDB,timePlateDB, ingredientsPlateDB;
        CheckBox scheduleMorning , scheduleAfternoon, scheduleNight;
        RadioButton typePlateE ,typePlateS;

        addPlate = (EditText) getView().findViewById(R.id.txtNombrePlato);
        namePlateDB = addPlate.getText().toString();

        scheduleMorning = (CheckBox)getView().findViewById(R.id.checkBoxManana);
        scheduleAfternoon = (CheckBox)getView().findViewById(R.id.checkBoxTarde);
        scheduleNight = (CheckBox)getView().findViewById(R.id.checkBoxNoche);


        // checkbox
        schedulePlateDB= "";
        if (scheduleMorning.isChecked()) {

            schedulePlateDB = scheduleMorning.getText().toString();
        }
        if (scheduleAfternoon.isChecked()) {

            schedulePlateDB =scheduleAfternoon.getText().toString();
        }
        if (scheduleNight.isChecked()) {

            schedulePlateDB = scheduleNight.getText().toString();
        }

        //radiobutton
        typePlateDB = "";
        typePlateE = getView().findViewById(R.id.radioBtnEntrada);
        typePlateS = getView().findViewById(R.id.radioBtnPlatoFuerte);

        if(typePlateE.isChecked()){
            typePlateDB = typePlateE.getText().toString();
        }
        else if(typePlateS.isChecked()){
            typePlateDB = typePlateS.getText().toString();
        }

        //price
        addPlate = (EditText) getView().findViewById(R.id.txtPrecioPlato);
        pricePlateDB = addPlate.getText().toString();

        addtimePlate = (TextView) getView().findViewById(R.id.txtTiempoPlato);
        timePlateDB = addtimePlate.getText().toString();

        addPlate = (EditText) getView().findViewById(R.id.txtIngredientes);
        ingredientsPlateDB = addPlate.getText().toString();

        if (photo == null) {
            Toast.makeText(getContext(), "no se pudo asignar foto", Toast.LENGTH_SHORT).show();
        }

        if (namePlateDB.equals("") || !scheduleMorning.isChecked() &&
                !scheduleAfternoon.isChecked() &&
                !scheduleNight.isChecked() ||
                typePlateE.isChecked() &&
                typePlateS.isChecked() ||
                pricePlateDB.equals("") || timePlateDB.equals("") ||
                ingredientsPlateDB.equals("") || photo == null) {
            Toast.makeText(getContext(), "Información Incompleta", Toast.LENGTH_SHORT).show();
        } else {

            int pricePlateDb = Integer.parseInt(pricePlateDB);

            DbHelper dbHelper = new DbHelper(getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure plate_structure = new co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure(namePlateDB,
                                                                  schedulePlateDB,
                                                                  typePlateDB,
                                                                  pricePlateDb,
                                                                  timePlateDB,
                                                                  ingredientsPlateDB,
                                                                  photo);

            //insert in  DB
            db.insert(RestaurantDB.TABLE_PLATES, null, plate_structure.toContentValues());

            Fragment platesf = new co.edu.udea.compumovil.gr02_20181.lab2.PlatesFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.container, platesf).commit();
        }


    }

}
