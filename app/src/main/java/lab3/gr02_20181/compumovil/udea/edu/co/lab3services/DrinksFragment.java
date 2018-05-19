package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DatabaseReference mreference;


    public DrinksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);
        mreference = FirebaseDatabase.getInstance().getReference().child("drinks");
        mreference.keepSynced(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_drinks);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // revisar

        return view;
    }

    @Override
    public  void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<DrinkInfo,DrinkViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DrinkInfo,DrinkViewHolder>
                (DrinkInfo.class,R.layout.card_view_drink,DrinkViewHolder.class,mreference){

            @Override
            public  void populateViewHolder(DrinkViewHolder drinkViewHolder, DrinkInfo model ,int position){
                drinkViewHolder.setNombre(model.getNombre());
                drinkViewHolder.setPrecio(model.getPrecio());

            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {

        View mview;

        public DrinkViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setNombre(String name) {
            TextView drinkName = (TextView) itemView.findViewById(R.id.txtCV_nameDrink);
            drinkName.setText(name);

        }

        public void setPrecio(String price) {
            TextView drinkPrice = (TextView) itemView.findViewById(R.id.txtCV_priceDrink);
            drinkPrice.setText(price);

        }
    }

}
