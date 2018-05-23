package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;

import android.content.ContentResolver;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddPlatesFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ImageView img;

    private EditText name_plate , price_plate , ingredients_plate;
    private TextView timePlate;
    private String schedulePlateDB, typePlateDB;
    CheckBox scheduleMorning , scheduleAfternoon, scheduleNight;
    RadioButton typePlateE ,typePlateS;
    private RecyclerView cardViewList;
    private List<PlateInfo> list_plate = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    //informacion del picker
    private TextView mTimeDisplay;
    private int mHour;
    private int mMinute;

    public AddPlatesFragment() {
        //Empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        ((NDActivity)getActivity()).setActionBarTitle("Agregar Plato");

        Button btntimePlate = (Button) view.findViewById(R.id.btnTiempoPlato);
        btntimePlate.setOnClickListener(this);

        Button btnaddPlateButton = (Button) view.findViewById(R.id.btnAgregarPlato);
        btnaddPlateButton.setOnClickListener(this);

        Button btnAgregarImagen = (Button) view.findViewById(R.id.btnAgregarImagen);
        btnAgregarImagen.setOnClickListener(this);

        mTimeDisplay = (TextView)view.findViewById(R.id.txtTiempoPlato);

        img = (ImageView)view.findViewById(R.id.imgPlato);




        name_plate = (EditText) view.findViewById(R.id.txtNombrePlato);
        price_plate = (EditText) view.findViewById(R.id.txtPrecioPlato);
        ingredients_plate = (EditText) view.findViewById(R.id.txtIngredientes);

        cardViewList = (RecyclerView)view.findViewById(R.id.recycler_plates) ;
        timePlate = (TextView)view.findViewById(R.id.txtTiempoPlato);

        //firebase
        initFirebase();
        addEventFirebaseListener();

        mStorageReference = FirebaseStorage.getInstance().getReference("plates");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Food");
        updateDisplay();
        return view;

    }

    private void addEventFirebaseListener() {
        mDatabaseReference.child("Food/plates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (list_plate.size() > 0)
                    list_plate.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    PlateInfo plate = postSnapshot.getValue(PlateInfo.class);
                    list_plate.add(plate);
                }
                //AdapterEvents adapter = new AdapterEvents(CreateEventFragment.this,list_events);
                //cardViewList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this.getContext());
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btnAgregarPlato:
                uploadFile();
                Fragment platesf = new DrinksFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.container, platesf).commit();
                break;

            case R.id.btnAgregarImagen:
                openFileChooser();
                break;

            case R.id.btnTiempoPlato:
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), "timePicker");
                break;
        }

    }

    private String getFileExtension(Uri uri){

        // getActivity() o getCOntext()

        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void uploadFile() {
        scheduleMorning = (CheckBox)getView().findViewById(R.id.checkBoxManana);
        scheduleAfternoon = (CheckBox)getView().findViewById(R.id.checkBoxTarde);
        scheduleNight = (CheckBox)getView().findViewById(R.id.checkBoxNoche);
        typePlateE = (RadioButton) getView().findViewById(R.id.radioBtnEntrada);
        typePlateS = (RadioButton) getView().findViewById(R.id.radioBtnPlatoFuerte);

        // checkbox

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
        typePlateE = getView().findViewById(R.id.radioBtnEntrada);
        typePlateS = getView().findViewById(R.id.radioBtnPlatoFuerte);

        if(typePlateE.isChecked()){
            typePlateDB = typePlateE.getText().toString();
        }
        else if(typePlateS.isChecked()){
            typePlateDB = typePlateS.getText().toString();
        }

        if (mImageUri != null){
            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() +
                    "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.d("TAG", "onSuccess: subio la imagen");

                    PlateInfo plateInfo = new PlateInfo(
                            UUID.randomUUID().toString()
                            ,name_plate.getText().toString(),
                            ingredients_plate.getText().toString(),
                            schedulePlateDB,
                            typePlateDB,
                            timePlate.getText().toString(),
                            taskSnapshot.getDownloadUrl().toString(),
                            Integer.valueOf(price_plate.getText().toString())
                    );

                    mDatabaseReference.child("plates").child(plateInfo.getUid()).setValue(plateInfo);
                }

            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {

                }

            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        }
        else {
            Toast.makeText(getContext(),"Image no selected",Toast.LENGTH_SHORT).show();
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            mImageUri = data.getData();
            img.setImageURI(mImageUri);

        }
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
}
