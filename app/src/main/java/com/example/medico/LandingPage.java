package com.example.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LandingPage extends AppCompatActivity {

    private static final String TAG = "LandingPage";

    //Widgets
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "landingPage - onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        //Initialising Widgets
        registerBtn = findViewById(R.id.buttonRegister);

       registerBtn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               Intent intent = new Intent(LandingPage.this, RegisterActivity.class);
               startActivity(intent);
           }
        });

        Log.d(TAG, "landingPage - work");

    }
}
