package co.edu.udea.compumovil.gr02_20181.lab2;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowCompleteInfoPlate extends Fragment {


    public ShowCompleteInfoPlate() {
        // Required empty public constructor
    }

    boolean favorite = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_show_complete_info_plate,
                container, false);
        ((co.edu.udea.compumovil.gr02_20181.lab2.NDRestaurant)getActivity()).setActionBarTitle("Plato");

        TextView t;
        ImageView i;

        final FloatingActionButton fb = (FloatingActionButton) view.findViewById(R.id.addFavortires);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                if(favorite){
                    Toast.makeText(getContext(),"Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                    fb.setImageResource(R.drawable.ic_star_border_black_24dp);
                    favorite = false;
                }else{
                    Toast.makeText(getContext(),"Agregado a favoritos", Toast.LENGTH_SHORT).show();
                    fb.setImageResource(R.drawable.ic_star_black_24dp);
                    favorite = true;
                }
            }
        });

        i = (ImageView)view.findViewById(R.id.imgPlateComplete);
        byte[] photo = Base64.decode(getArguments().getString("picturePlate"),Base64.DEFAULT);
        i.setImageBitmap(BitmapFactory.decodeByteArray(photo,0,photo.length));

        t = (TextView)view.findViewById(R.id.txtNamePlateComplete);
        t.setText(getArguments().getString("namePlate"));

        t = (TextView)view.findViewById(R.id.txtSchedulePlateComplete);
        t.setText("Horario: " + getArguments().getString("schedulePlate"));

        t = (TextView)view.findViewById(R.id.txtTypePlateComplete);
        t.setText( getArguments().getString("typePlate"));

        t = (TextView)view.findViewById(R.id.txtPricePlateComplete);
        t.setText("$" + getArguments().getInt("pricePlate"));

        t = (TextView)view.findViewById(R.id.txtTimePlateComplete);
        t.setText("Tiempo de preparacion" + getArguments().getString("timePlate"));

        t = (TextView)view.findViewById(R.id.txtIngredientsPlateComplete);
        t.setText("Ingredientes" + getArguments().getString("ingredientsPlate"));

        return view;
    }

}
