package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
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
import java.util.ArrayList;
import java.util.List;




/**
 * A simple {@link Fragment} subclass.
 */
public class DrinksFragment extends Fragment implements Filterable {

    private RecyclerView mRecyclerView;
    private DatabaseReference mreference;
    private SearchView searchView;


    public DrinksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);

        mreference = FirebaseDatabase.getInstance().getReference().child("Food/drinks");
        mreference.keepSynced(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_drinks);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // revisar

        ImageView drinkImage;

        searchView = (SearchView) getActivity().findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<DrinkInfo, DrinkViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DrinkInfo, DrinkViewHolder>
                (DrinkInfo.class, R.layout.card_view_drink, DrinkViewHolder.class, mreference) {


            @Override
            public void populateViewHolder(DrinkViewHolder drinkViewHolder, final DrinkInfo model, final int position) {

                drinkViewHolder.setNombre(model.getNombre());
                drinkViewHolder.setPrecio(model.getPrecio());
                //URL urlImage = ConvertToUrl(model.getmImageUrl());

                Log.d("TAG", "populateViewHolder: " + model.getmImageUrl());

                drinkViewHolder.setImageDrink(model.getmImageUrl(), getActivity());


                drinkViewHolder.cardViewDrink.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        DrinkInfo model1 = model;
                        Bundle b = new Bundle();
                        b.putString("nameDrink", model1.getNombre());
                        b.putString("priceDrink", model1.getPrecio());
                        b.putString("ingredientsDrinks", model1.getIngredientes());
                        b.putString("pictureDrinks", model1.getmImageUrl());

                        Intent intent = new Intent(getActivity().getBaseContext(),
                                ShowCompleteInfoDrink.class);
                        intent.putExtra("drinkData", b);


                        Fragment drinksf = new ShowCompleteInfoDrink();
                        drinksf.setArguments(b);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, drinksf).commit();
                    }
                });
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {

        View mview;
        ImageView drinkImage = (ImageView) itemView.findViewById(R.id.imgCV_drink);
        CardView cardViewDrink = (CardView) itemView.findViewById(R.id.cardViewDrink);

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

    List<DrinkInfo> drinks;
    List<DrinkInfo> drinksArray = new ArrayList<DrinkInfo>();
    Filter filter;

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new myFilter(this, drinks);
        }
        return filter;
    }

    class myFilter extends Filter {

        DrinksFragment adapterDrinks;
        final List<DrinkInfo> drinks;
        List<DrinkInfo> drinksArray;

        public myFilter(DrinksFragment drinksFragment, List<DrinkInfo> drinks) {
            super();
            this.adapterDrinks = drinksFragment;
            this.drinks = drinks;
            this.drinksArray = new ArrayList<DrinkInfo>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            drinksArray.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                drinksArray.addAll(drinks);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final DrinkInfo drink : drinks) {
                    if (drink.getNombre().toLowerCase().trim().contains(filterPattern)) {
                        drinksArray.add(drink);
                    } else if (String.valueOf(drink.getPrecio()).trim().contains(filterPattern)) {
                        drinksArray.add(drink);
                    }
                }
            }
            results.values = drinksArray;
            results.count = drinksArray.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                adapterDrinks.drinksArray.clear();
                adapterDrinks.drinksArray.addAll((ArrayList<DrinkInfo>) results.values);
                adapterDrinks.notifyAll();
            } catch (Exception e) {

            }
        }
    }
}
