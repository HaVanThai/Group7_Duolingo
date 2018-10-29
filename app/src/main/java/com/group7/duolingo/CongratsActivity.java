package com.group7.duolingo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CongratsActivity extends AppCompatActivity {

    MediaPlayer congratsSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);

        congratsSound = MediaPlayer.create(this, R.raw.congrats);
        congratsSound.start();
    }

    public void goToHome(View view) {
        congratsSound.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
