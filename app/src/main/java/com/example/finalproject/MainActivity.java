package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getResources().getString(R.string.logo));
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkIfLoggedIn();
    }

    public boolean checkIfLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("loggedAccount", MODE_PRIVATE);
        String loggedEmail = preferences.getString("loggedEmail", "");
        SharedData.loggedEmail = loggedEmail;
        if (!loggedEmail.isEmpty()) {
            //To Do - Remove next line and navigate to logged in main page
            Toast.makeText(this, "Logged Account = "+loggedEmail, Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(this, /**/.class);
            //startActivity(intent);
        }
        return false;
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}