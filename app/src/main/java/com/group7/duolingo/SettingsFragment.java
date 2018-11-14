package com.group7.duolingo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import DAL.OnGetDataListener;
import DAL.UserManager;
import entities.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    TextView totalLeanedLessons;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        totalLeanedLessons = view.findViewById(R.id.total_learned_lessons);

        String progress = getArguments().getString("progress");
        int totalDoneLessons = progress.split("\\|").length - 1;
        totalLeanedLessons.setText("You have learned " + totalDoneLessons + " lessons");

        return view;
    }

}
