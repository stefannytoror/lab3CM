package lab3.gr02_20181.compumovil.udea.edu.co.lab3services;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileFragment extends Fragment implements FirebaseAuth.AuthStateListener{

    private FirebaseAuth SWAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = SWAuth.getInstance().getCurrentUser();
    private String TAG = "UserProfileFragment";
    private TextView userName, userEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile,
                container, false);
        ((NDActivity)getActivity()).setActionBarTitle("Perfil");

        userName = (TextView) view.findViewById(R.id.txtUserNameProfile);
        userEmail = (TextView) view.findViewById(R.id.txtUserEmailProfile);

        String string = user.getEmail();
        String[] parts = string.split("@");
        String part1 = parts[0];
        String part2 = parts[1];
        userName.setText(part1);
        userEmail.setText(user.getEmail());


        return view;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            Log.d(TAG, "onAuthStateChanged:PhotoUrl:" + user.getPhotoUrl());
            Log.d(TAG, "onAuthStateChanged:Name:" + user.getDisplayName());
            Log.d(TAG, "onAuthStateChanged:Email:" + user.getEmail());


        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
    }


}

