package com.group7.duolingo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


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
        textViewEmail.setText(email);
        textViewPersonName.setText(personName);
        return view;
    }

}
