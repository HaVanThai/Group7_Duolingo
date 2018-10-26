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

import entities.User;

public class MainActivity extends AppCompatActivity {

    // Initial all fragments
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    FragmentManager fm;
    Fragment active;

    User user;

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

        fragment1 = new StudyFragment();
        fragment2 = new ProfileFragment();
        fragment3 = new SettingsFragment();
        fm = getSupportFragmentManager();
        active = fragment1;

        Intent intent = new Intent(this, SignInActivity.class);
        startActivityForResult(intent, SIGN_IN_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_OK) {
            if(resultCode == Activity.RESULT_OK){
                Bundle bundleUser = new Bundle();
                bundleUser.putString("email", data.getStringExtra("email"));
                bundleUser.putString("personName", data.getStringExtra("personName"));
                fragment2.setArguments(bundleUser);

                fm.beginTransaction().add(R.id.main_content, fragment3, "3").hide(fragment3).commit();
                fm.beginTransaction().add(R.id.main_content, fragment2, "2").hide(fragment2).commit();
                fm.beginTransaction().add(R.id.main_content,fragment1, "1").commit();

                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
