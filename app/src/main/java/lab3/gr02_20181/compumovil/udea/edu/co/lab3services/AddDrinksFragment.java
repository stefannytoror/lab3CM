package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDrinksFragment extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ImageView img;

    private EditText name_drink , price_drink , ingredients_drink;
    private RecyclerView cardViewList;
    private List<DrinkInfo> list_drinks = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private StorageReference mStorageReference;



    public AddDrinksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_drinks, container, false);

        Button btnAddDrink = (Button)view.findViewById(R.id.btnAgregarBebida);
        Button btnAddImage = (Button)view.findViewById(R.id.btnAgregarImgBebida);
        btnAddDrink.setOnClickListener(this);
        btnAddImage.setOnClickListener(this);
        ((NDActivity)getActivity()).setActionBarTitle("Agregar bebida");

        img = (ImageView)view.findViewById(R.id.imgBebida);




        name_drink = (EditText) view.findViewById(R.id.txtNombreBebida);
        price_drink = (EditText) view.findViewById(R.id.txtPrecioBebida);
        ingredients_drink = (EditText) view.findViewById(R.id.txtIngredientesBebida);
        cardViewList = (RecyclerView)view.findViewById(R.id.recycler_drinks) ;

        //firebase
        initFirebase();
        addEventFirebaseListener();

        mStorageReference = FirebaseStorage.getInstance().getReference("drinks");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Food");

        return view;
    }

    private void addEventFirebaseListener() {
        mDatabaseReference.child("drinks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (list_drinks.size() > 0)
                    list_drinks.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    DrinkInfo drink = postSnapshot.getValue(DrinkInfo.class);
                    list_drinks.add(drink);
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
            case R.id.btnAgregarBebida:

                // organizar
                /*DrinkInfo drink = new DrinkInfo(UUID.randomUUID().toString()
                        ,name_drink.getText().toString(),String.valueOf(price_drink.getText()),
                        ingredients_drink.getText().toString());*/

                // Adgregar un registro a la base de datos

                //mDatabaseReference.child("drinks").child(drink.getUid()).setValue(drink);

                uploadFile();

                /*Intent ListDrinks = new Intent(getContext(), DrinksFragment.class);
                startActivity(ListDrinks);*/

                Fragment drinksf = new DrinksFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.container, drinksf).commit();
                break;

            case R.id.btnAgregarImgBebida:
                openFileChooser();
                break;
        }

    }

    private String getFileExtension(Uri uri){

        // getActivity() o getCOntext()

        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    //firebase imagen
    private void uploadFile() {
        if (mImageUri != null){
            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() +
                    "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.d("TAG", "onSuccess: subio la imagen");

                    DrinkInfo drink = new DrinkInfo(UUID.randomUUID().toString()
                            ,name_drink.getText().toString(),String.valueOf(price_drink.getText()),
                            ingredients_drink.getText().toString(), taskSnapshot.getDownloadUrl().toString());

                    mDatabaseReference.child("drinks").child(drink.getUid()).setValue(drink);
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
}
