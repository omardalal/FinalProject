package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        String loggedType = preferences.getString("loggedType", "");
        String loggedFirstName = preferences.getString("loggedFirstName", "");
        String loggedLastName = preferences.getString("loggedLastName", "");
        PublicData.loggedEmail = loggedEmail.trim();
        PublicData.loggedType = loggedType.trim();
        PublicData.loggedFirstName = loggedFirstName.trim();
        PublicData.loggedLastName = loggedLastName.trim();
        boolean loggedIn = !loggedEmail.isEmpty()&&!loggedType.isEmpty()&&!loggedFirstName.isEmpty()&&!loggedLastName.isEmpty();
        if (loggedIn) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
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