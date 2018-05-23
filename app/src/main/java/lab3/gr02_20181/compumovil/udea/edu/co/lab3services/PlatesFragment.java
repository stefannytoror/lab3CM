package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PlatesFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private DatabaseReference mreference;

    public PlatesFragment() {
        //Empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view = inflater.inflate(R.layout.fragment_plates, container, false);
        ((NDActivity)getActivity()).setActionBarTitle("Platos");
        mreference = FirebaseDatabase.getInstance().getReference().child("Food/plates");
        mreference.keepSynced(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_plates);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // revisar




        ImageView plateImage;

        return view;
    }

    @Override
    public  void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<PlateInfo,PlatesFragment.PlateViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<PlateInfo,PlatesFragment.PlateViewHolder>
                (PlateInfo.class,R.layout.card_view_plate,PlatesFragment.PlateViewHolder.class,mreference){

            @Override
            public  void populateViewHolder(PlatesFragment.PlateViewHolder plateViewHolder, final PlateInfo model , int position){

                plateViewHolder.setNombre(model.getNamePlate());
                String price = String.valueOf(model.getPricePlate());
                plateViewHolder.setPrecio(price);
                plateViewHolder.setTipo(model.getTypePlate());
                plateViewHolder.setTiempo(model.getTimePlate());
                //URL urlImage = ConvertToUrl(model.getmImageUrl());

                Log.d("TAG", "populateViewHolder: " + model.getmImageUrl());

                plateViewHolder.setImageDrink(model.getmImageUrl(),getActivity());

                plateViewHolder.cardViewPlate.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        PlateInfo model1 = model;
                        Bundle b = new Bundle();
                        b.putString("namePlate",model1.getNamePlate());
                        b.putString("schedulePlate",model1.getSchedulePlate());
                        b.putString("typePlate",model1.getTypePlate());
                        b.putInt("pricePlate",model1.getPricePlate());
                        b.putString("timePlate",model1.getTimePlate());
                        b.putString("ingredientsPlate",model1.getIngridientsPlate());
                        b.putString("picturePlate",model1.getmImageUrl());




                        Fragment platesf = new ShowCompleteInfoPlate();
                        platesf.setArguments(b);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, platesf).commit();
                    }
                });

            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }





    public static class PlateViewHolder extends RecyclerView.ViewHolder {

        View mview;
        ImageView plateImage = (ImageView) itemView.findViewById(R.id.imgCV_plate);
        CardView cardViewPlate = (CardView) itemView.findViewById(R.id.cardViewPlate);

        public PlateViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setNombre(String name) {
            TextView plateName = (TextView) itemView.findViewById(R.id.txtCV_namePlate);
            plateName.setText(name);

        }

        public void setPrecio(String price) {
            TextView platePrice = (TextView) itemView.findViewById(R.id.txtCV_pricePlate);
            platePrice.setText(price);

        }

        public void setTipo(String type){
            TextView plateType = (TextView) itemView.findViewById(R.id.txtCV_typePlate);
            plateType.setText(type);
        }

        public void setTiempo(String time){
            TextView plateTime = (TextView) itemView.findViewById(R.id.txtCV_timePlate);
            plateTime.setText(time);
        }


        public void setImageDrink(String src, Context c) {
            ImageView plateImage = (ImageView) itemView.findViewById(R.id.imgCV_plate);
            Picasso.with(c).load(src)
                    .fit()
                    .centerCrop()
                    .into(plateImage);


        }
    }


}
