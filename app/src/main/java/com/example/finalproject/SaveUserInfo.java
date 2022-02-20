package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveUserInfo {

    public static void writeData(String email, String type, String firstName, String lastName, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("loggedAccount", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("loggedEmail", email);
        PublicData.loggedEmail = email;

        editor.putString("loggedType", type);
        PublicData.loggedType = type;

        editor.putString("loggedFirstName", firstName);
        PublicData.loggedFirstName = firstName;

        editor.putString("loggedLastName", lastName);
        PublicData.loggedLastName = lastName;
        editor.commit();
    }

}
