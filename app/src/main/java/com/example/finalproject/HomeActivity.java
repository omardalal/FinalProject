package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private Button reservedRoomsBtn;
    private Button roomServiceBtn;
    private Button roomServiceMenuBtn;
    private TextView nameLbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle(getResources().getString(R.string.home));

        reservedRoomsBtn = findViewById(R.id.reservedRoomsBtn);
        roomServiceBtn = findViewById(R.id.roomServiceBtn);
        roomServiceMenuBtn = findViewById(R.id.roomServiceMenuBtn);
        nameLbl = findViewById(R.id.nameLbl);
        nameLbl.setText(PublicData.loggedFirstName+" "+PublicData.loggedLastName);

        if (PublicData.loggedType.equals(PublicData.userTypeReceptionist)) {
            Resources rcs = getResources();
            reservedRoomsBtn.setText(rcs.getString(R.string.homeALlReservedRooms));
            roomServiceBtn.setText(rcs.getString(R.string.homeAllRoomService));
        }

        if (PublicData.loggedType.equals(PublicData.userTypeReceptionist)) {
            roomServiceMenuBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void searchRoomsClicked(View view) {
        Intent intent = new Intent(this, RoomSearchActivity.class);
        startActivity(intent);
    }

    public void reservedRoomsClicked(View view) {
        Intent intent = new Intent(this, MyReservationsActivity.class);
        startActivity(intent);
    }

    public void roomServiceClicked(View view) {
        Intent intent = new Intent();
        if (PublicData.loggedType.equals(PublicData.userTypeReceptionist)) {
            intent = new Intent(this, ReceptionistOrdersActivity.class);
        } else {
            intent = new Intent(this, CustomerOrdersActivity.class);
        }
        startActivity(intent);
    }

    public void roomServiceMenuClicked(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void logoutClicked(View view) {
        SaveUserInfo.writeData("", "", "", "", this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}