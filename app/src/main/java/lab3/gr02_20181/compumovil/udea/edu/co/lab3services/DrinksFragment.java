package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


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

        mreference = FirebaseDatabase.getInstance().getReference().child("drinks/drinks");
        mreference.keepSynced(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_drinks);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // revisar

        ImageView drinkImage;

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
                //URL urlImage = ConvertToUrl(model.getmImageUrl());

                Log.d("TAG", "populateViewHolder: " + model.getmImageUrl());

                drinkViewHolder.setImageDrink(model.getmImageUrl(),getActivity());

                /*Picasso.with(getActivity()).load(model.getmImageUrl())
                        .fit()
                        .centerCrop()
                        .into(drinkViewHolder.drinkImage);*/
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }





    public static class DrinkViewHolder extends RecyclerView.ViewHolder {

        View mview;
        ImageView drinkImage = (ImageView) itemView.findViewById(R.id.imgCV_drink);

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

        public void setImageDrink(String src, Context c) {
            ImageView drinkImage = (ImageView) itemView.findViewById(R.id.imgCV_drink);
            Picasso.with(c).load(src)
                        .fit()
                        .centerCrop()
                        .into(drinkImage);


        }
    }

}
