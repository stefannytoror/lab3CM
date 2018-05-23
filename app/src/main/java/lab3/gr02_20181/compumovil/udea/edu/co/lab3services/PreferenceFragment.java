package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferenceFragment extends PreferenceFragmentCompat {


    public PreferenceFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preference, rootKey);
        ((NDActivity)getActivity()).setActionBarTitle("Configuraciones");


    }

}
