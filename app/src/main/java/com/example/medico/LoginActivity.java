package com.example.medico;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userETLogin, passETLogin;
    Button loginBtn, registerBtn;

    // Firebase
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            Intent i = new Intent(LoginActivity.this, LandingPageActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userETLogin = findViewById(R.id.editTextLogin);
        passETLogin = findViewById(R.id.passwordLogin);
        loginBtn = findViewById(R.id.loginButton);
        registerBtn = findViewById(R.id.registerButton);

        // Firebase Auth;
        auth = FirebaseAuth.getInstance();


        // Register Button
        registerBtn.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });


        // Login Button:
        loginBtn.setOnClickListener(v -> {
            String email_text = userETLogin.getText().toString();
            String pass_text = passETLogin.getText().toString();

            // Check if fields are empty;
            if (TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text)){
                Toast.makeText(LoginActivity.this, "Please fill the Fields", Toast.LENGTH_SHORT).show();
            }
            else{
                auth.signInWithEmailAndPassword(email_text, pass_text)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Intent i = new Intent(LoginActivity.this, LandingPageActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });


    }
}
