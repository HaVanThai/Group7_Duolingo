package com.group7.duolingo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment{


    private GoogleApiClient mGoogleApiClient;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        String email = getArguments().getString("email");
        String personName = getArguments().getString("personName");
        TextView textViewEmail = view.findViewById(R.id.email);
        TextView textViewPersonName = view.findViewById(R.id.personName);
        TextView uAlone = view.findViewById(R.id.u_alone);
        textViewEmail.setText(email);
        textViewPersonName.setText(personName);
        uAlone.setText("You are alone, " + personName + "!");


        return view;
    }

}
