package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDrinksFragment extends Fragment implements View.OnClickListener {

    private EditText name_drink , price_drink , ingredients_drink;
    private RecyclerView cardViewList;
    private List<DrinkInfo> list_drinks = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;



    public AddDrinksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_drinks, container, false);
        Button btnAddDrink = (Button)view.findViewById(R.id.btnAgregarBebida);
        btnAddDrink.setOnClickListener(this);


        name_drink = (EditText) view.findViewById(R.id.txtNombreBebida);
        price_drink = (EditText) view.findViewById(R.id.txtPrecioBebida);

        ingredients_drink = (EditText) view.findViewById(R.id.txtIngredientesBebida);

        cardViewList = (RecyclerView)view.findViewById(R.id.recycler_drinks) ;
        //firebase
        initFirebase();
        addEventFirebaseListener();

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
                DrinkInfo drink = new DrinkInfo(UUID.randomUUID().toString()
                        ,name_drink.getText().toString(),String.valueOf(price_drink.getText()),
                        ingredients_drink.getText().toString());

                mDatabaseReference.child("drinks").child(drink.getUid()).setValue(drink);

                Log.d("NUNCA", "onClick: Esto nunca se hace");

                /*Intent ListDrinks = new Intent(getContext(), DrinksFragment.class);
                startActivity(ListDrinks);*/

                Fragment drinksf = new DrinksFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.container, drinksf).commit();
                break;
        }

    }


}
