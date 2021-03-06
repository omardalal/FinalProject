package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public EditText emailField;
    public EditText passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.login));

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
    }

    public void login(View view) {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (email.isEmpty()||password.isEmpty()) {
            Toast.makeText(this, R.string.fillAllFields, Toast.LENGTH_SHORT).show();
            return;
        }
        BackendRequests.getRequest("login.php?email=" + email + "&password=" + password, this, (response, success) -> {
            if (response.size()==0) {
                Toast.makeText(LoginActivity.this, R.string.loginFail, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                final String responseEmail = response.get(0).getString("email");
                final String type = response.get(0).getString("type");
                final String firstName = response.get(0).getString("firstName");
                final String lastName = response.get(0).getString("lastName");
                SaveUserInfo.writeData(responseEmail, type, firstName, lastName, this);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } catch (JSONException e) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.failedToLogin), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }
}