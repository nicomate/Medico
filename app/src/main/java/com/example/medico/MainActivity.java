package com.example.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //splash screen set at 4 seconds
    private static int SPLASH_SCREEN = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate Main");

        super.onCreate(savedInstanceState);

        //hides status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //handler that automatically goes to next activity based on timer
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LandingPage.class);
            startActivity(intent);
            finish();
        },SPLASH_SCREEN);
        Log.d(TAG, "end of MainActivity");
    }
}
