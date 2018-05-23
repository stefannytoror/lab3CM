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

import co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure;


public class AdapterPlates  extends RecyclerView.Adapter<co.edu.udea.compumovil.gr02_20181.lab2.AdapterPlates.PlatesViewH> implements Filterable{

    OnListener listen;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try{
            listen = (OnListener)recyclerView.getContext();
        }catch (Exception e){

        }
    }

    List<PlatesStructure> plates;
    List<PlatesStructure> platesArray = new ArrayList<PlatesStructure>();
    Filter filter;

    public AdapterPlates(List<PlatesStructure> plates){
        this.plates = plates;
        this.platesArray.addAll(plates);
    }

    @Override
    public PlatesViewH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_plate, parent,false);
        PlatesViewH vh = new PlatesViewH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(co.edu.udea.compumovil.gr02_20181.lab2.AdapterPlates.PlatesViewH holder, final int position) {

        holder.namePlate.setText(platesArray.get(position).getPlate_name());

        int price = platesArray.get(position).getPlate_price();
        holder.pricePlate.setText(String.valueOf(price));
        Log.d("tag", "onBindViewHolder: " + String.valueOf(price));

        holder.typePlate.setText(platesArray.get(position).getPlate_type());
        holder.timePlate.setText(platesArray.get(position).getPlate_time());



        byte[] picture_drink = Base64.decode(platesArray.get(position).getPlate_picture(),Base64.DEFAULT);
        Bitmap bit = BitmapFactory.decodeByteArray(picture_drink, 0, picture_drink.length);
        holder.picturePlate.setImageBitmap(bit);


        holder.cardViewPlate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("namePlate",platesArray.get(position).getPlate_name());
                b.putString("schedulePlate",platesArray.get(position).getPlate_schedule());
                b.putString("typePlate",platesArray.get(position).getPlate_type());
                b.putInt("pricePlate",platesArray.get(position).getPlate_price());
                b.putString("timePlate",platesArray.get(position).getPlate_time());
                b.putString("ingredientsPlate",platesArray.get(position).getPlate_ingredients());
                b.putString("picturePlate",platesArray.get(position).getPlate_picture());
                listen.getPositionPlates(b);
            }
        });
    }
    @Override
    public int getItemCount() {


        Log.d("hola","numero de items" + platesArray.size());
        return platesArray.size();
    }

    public class PlatesViewH extends RecyclerView.ViewHolder {
        CardView cardViewPlate;
        TextView namePlate, pricePlate, typePlate, timePlate;
        ImageView picturePlate;


        PlatesViewH(View itemView) {
            super(itemView);
            cardViewPlate = (CardView)itemView.findViewById(R.id.cardViewPlate);
            namePlate = (TextView)itemView.findViewById(R.id.txtCV_namePlate);
            pricePlate = (TextView)itemView.findViewById(R.id.txtCV_pricePlate);
            typePlate = (TextView)itemView.findViewById(R.id.txtCV_typePlate);
            timePlate = (TextView)itemView.findViewById(R.id.txtCV_timePlate);
            picturePlate = (ImageView)itemView.findViewById(R.id.imgCV_plate);
        }
    }
    public interface OnListener{
        public void getPositionPlates(Bundle datos);
    }

    @Override
    public Filter getFilter() {
        if(filter==null){filter = new myFilter(this,plates);}
        return filter;
    }
    class myFilter extends Filter {

        co.edu.udea.compumovil.gr02_20181.lab2.AdapterPlates adapterPlates;
        final List<PlatesStructure> plates;
        List<PlatesStructure> platesArray;

        public myFilter(co.edu.udea.compumovil.gr02_20181.lab2.AdapterPlates adapterPlates, List<PlatesStructure> plates) {
            super();
            this.adapterPlates = adapterPlates;
            this.plates = plates;
            this.platesArray = new ArrayList<PlatesStructure>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            platesArray.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                platesArray.addAll(plates);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure plate : plates) {
                    if (plate.getPlate_name().toLowerCase().trim().contains(filterPattern)) {
                        platesArray.add(plate);
                    }if(String.valueOf(plate.getPlate_price()).trim().contains(filterPattern)){
                        platesArray.add(plate);
                    }
                }
            }
            results.values = platesArray;
            results.count = platesArray.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                adapterPlates.platesArray.clear();
                adapterPlates.platesArray.addAll((ArrayList<PlatesStructure>) results.values);
                adapterPlates.notifyDataSetChanged();
            }catch (Exception e){

            }
        }
    }

}