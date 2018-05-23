package co.edu.udea.compumovil.gr02_20181.lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.DrinksStructure;

/**
 * Created by personal on 18/03/18.
 */

public class AdapterDrinks  extends RecyclerView.Adapter<co.edu.udea.compumovil.gr02_20181.lab2.AdapterDrinks.DrinksViewH> implements Filterable{

    OnListener listen;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try{
            listen = (OnListener)recyclerView.getContext();
        }catch (Exception e){

        }
    }

    List<DrinksStructure> drinks;
    List<DrinksStructure> drinksArray = new ArrayList<DrinksStructure>();
    Filter filter;

    public AdapterDrinks(List<DrinksStructure> drinks){
        this.drinks = drinks;
        this.drinksArray.addAll(drinks);
    }

    @Override
    public DrinksViewH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_drink, parent,false);
        DrinksViewH vh = new DrinksViewH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(co.edu.udea.compumovil.gr02_20181.lab2.AdapterDrinks.DrinksViewH holder, final int position) {

        holder.nameDrink.setText(drinksArray.get(position).getDrink_name());
        int price = drinksArray.get(position).getDrink_price();

        holder.priceDrink.setText(String.valueOf(price));


        byte[] picture_drink = Base64.decode(drinksArray.get(position).getDrink_picture(),Base64.DEFAULT);
        Bitmap bit = BitmapFactory.decodeByteArray(picture_drink, 0, picture_drink.length);
        holder.pictureDrink.setImageBitmap(bit);


        holder.cardViewDrink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("nameDrink",drinksArray.get(position).getDrink_name());
                b.putInt("priceDrink",drinksArray.get(position).getDrink_price());
                b.putString("ingredientsDrinks",drinksArray.get(position).getDrink_ingredients());
                b.putString("pictureDrinks",drinksArray.get(position).getDrink_picture());
                listen.getPosition(b);
            }
        });
    }
    @Override
    public int getItemCount() {


        Log.d("hola","numero de items" + drinksArray.size());
        return drinksArray.size();
    }

    public class DrinksViewH extends RecyclerView.ViewHolder {
        CardView cardViewDrink;
        TextView nameDrink, priceDrink;
        ImageView pictureDrink;


        DrinksViewH(View itemView) {
            super(itemView);
            cardViewDrink = (CardView)itemView.findViewById(R.id.cardViewDrink);
            nameDrink = (TextView)itemView.findViewById(R.id.txtCV_nameDrink);
            priceDrink = (TextView)itemView.findViewById(R.id.txtCV_priceDrink);
            pictureDrink = (ImageView)itemView.findViewById(R.id.imgCV_drink);
        }
    }
    public interface OnListener{
        public void getPosition(Bundle datos);
    }

    @Override
    public Filter getFilter() {
        if(filter==null){filter = new myFilter(this,drinks);}
        return filter;
    }
    class myFilter extends Filter {

        co.edu.udea.compumovil.gr02_20181.lab2.AdapterDrinks adapterDrinks;
        final List<DrinksStructure> drinks;
        List<DrinksStructure> drinksArray;

        public myFilter(co.edu.udea.compumovil.gr02_20181.lab2.AdapterDrinks adapter, List<DrinksStructure> drinks) {
            super();
            this.adapterDrinks = adapter;
            this.drinks = drinks;
            this.drinksArray = new ArrayList<DrinksStructure>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            drinksArray.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                drinksArray.addAll(drinks);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final co.edu.udea.compumovil.gr02_20181.lab2.DB.DrinksStructure drink : drinks) {
                    if (drink.getDrink_name().toLowerCase().trim().contains(filterPattern)) {
                        drinksArray.add(drink);
                    }else if(String.valueOf(drink.getDrink_price()).trim().contains(filterPattern)){
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
                adapterDrinks.drinksArray.addAll((ArrayList<DrinksStructure>) results.values);
                adapterDrinks.notifyDataSetChanged();
            }catch (Exception e){

            }
        }
    }

}
