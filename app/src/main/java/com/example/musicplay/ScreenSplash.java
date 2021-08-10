package com.example.musicplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ScreenSplash extends AppCompatActivity {

    private static int timeOut = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(ScreenSplash.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, timeOut);
    }
}