package com.group7.duolingo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import DAL.OnGetDataListener;
import DAL.UserManager;
import entities.User;

public class MainActivity extends AppCompatActivity {

    // Initial all fragments
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    FragmentManager fm;
    Fragment active;

    User user;
    UserManager userManager;

    final static int SIGN_IN_OK = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_study:
                            fm.beginTransaction().hide(active).show(fragment1).commit();
                            active = fragment1;
                            return true;
                        case R.id.navigation_profile:
                            fm.beginTransaction().hide(active).show(fragment2).commit();
                            active = fragment2;
                            return true;
                        case R.id.navigation_settings:
                            fm.beginTransaction().hide(active).show(fragment3).commit();
                            active = fragment3;
                            return true;
                    }
                    return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();
        userManager = new UserManager();

        fragment1 = new StudyFragment();
        fragment2 = new ProfileFragment();
        fragment3 = new SettingsFragment();
        fm = getSupportFragmentManager();
        active = fragment1;

        // Open sign in activity
        Intent intent = new Intent(this, SignInActivity.class);
        startActivityForResult(intent, SIGN_IN_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_OK) {
            if(resultCode == Activity.RESULT_OK){

                final Bundle bundleUser = new Bundle();
                final String email = data.getStringExtra("email");
                final String personName = data.getStringExtra("personName");
                bundleUser.putString("email", email);
                bundleUser.putString("personName", personName);

                final Bundle bundleStudy = new Bundle();

                // Get user's data
                userManager.getUser(data.getStringExtra("email"), new OnGetDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(QuerySnapshot data, DocumentReference docRef) {
                        if (data.getDocuments().size() == 0) {
                            // Handle new user
                            userManager.addNewUser(new User(email, personName, ""), new OnGetDataListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(QuerySnapshot data, DocumentReference docRef) {
                                    bundleStudy.putString("userDocId", docRef.getId());
                                    bundleStudy.putString("progress", "");

                                    startFragments(bundleStudy, bundleUser);
                                }

                                @Override
                                public void onFailed(Exception error) {

                                }
                            });

                        } else {
                            // Load user progress information
                            DocumentSnapshot document = data.getDocuments().get(0);
                            bundleStudy.putString("userDocId", document.getId());
                            bundleStudy.putString("progress", document.getString("progress"));

                            startFragments(bundleStudy, bundleUser);
                        }


                    }

                    @Override
                    public void onFailed(Exception error) {

                    }
                });

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    protected void startFragments(Bundle bundleStudy, Bundle bundleUser) {
        fragment1.setArguments(bundleStudy);
        fragment2.setArguments(bundleUser);
        fragment3.setArguments(bundleStudy);

        fm.beginTransaction().add(R.id.main_content, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_content, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_content,fragment1, "1").commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
