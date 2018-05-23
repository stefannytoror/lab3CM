package co.edu.udea.compumovil.gr02_20181.lab2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.List;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link co.edu.udea.compumovil.gr02_20181.lab2.PlatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link co.edu.udea.compumovil.gr02_20181.lab2.PlatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlatesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RecyclerView recyclerView = null;
    public AdapterPlates adapterPlates;
    public SearchView searchView;


    private OnFragmentInteractionListener mListener;

    public PlatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static co.edu.udea.compumovil.gr02_20181.lab2.PlatesFragment newInstance(String param1, String param2) {
        co.edu.udea.compumovil.gr02_20181.lab2.PlatesFragment fragment = new co.edu.udea.compumovil.gr02_20181.lab2.PlatesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plates,
                container, false);
        ((co.edu.udea.compumovil.gr02_20181.lab2.NDRestaurant)getActivity()).setActionBarTitle("Platos");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_plates);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DbHelper db = new DbHelper(getContext());
        List<PlatesStructure> plates = db.platesList();
        adapterPlates= new AdapterPlates(plates);
        recyclerView.setAdapter(adapterPlates);

        searchView = (SearchView) getActivity().findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterPlates.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
