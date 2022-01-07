package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    public EditText emailField;
    public EditText passwordField;
    public EditText firstNameField;
    public EditText lastNameField;
    public Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.signUp));

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);
        typeSpinner = findViewById(R.id.typeSpinner);

    }

    public void signUp(View view) {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String firstName = firstNameField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();
        String type = typeSpinner.getSelectedItem().toString().trim();
        if (email.isEmpty()||password.isEmpty()||firstName.isEmpty()||lastName.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.fillAllFields), Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> postMap = new HashMap();
        postMap.put("email", email);
        postMap.put("password", password);
        postMap.put("firstName", firstName);
        postMap.put("lastName", lastName);
        postMap.put("type", type);
        BackendRequests.postRequest("signup.php", SignUpActivity.this, postMap, new RequestCallback() {
            @Override
            public void onResponse(ArrayList<JSONObject> response, boolean success) {
                if (success) {
                    SharedPreferences preferences = getSharedPreferences("loggedAccount", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("loggedEmail", email);
                    SharedData.loggedEmail = email;
                    editor.commit();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}