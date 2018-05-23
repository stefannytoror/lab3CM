package co.edu.udea.compumovil.gr02_20181.lab2;


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


    }

}
