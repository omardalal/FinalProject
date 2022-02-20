package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity {

    private ImageView previewImage1;
    private ImageView previewImage2;
    private ImageView previewImage3;
    private TextView numberFloorLbl;
    private TextView roomDescriptionLbl;
    private TextView roomStatusLbl;
    private TextView numberTypeLbl;
    private MenuItem reserveRoomBtn;

    private String roomType;
    private String roomDescription;
    private String roomStatus;
    private String roomFloor;

    private int roomNumber = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        previewImage1 = findViewById(R.id.imageView1);
        previewImage2 = findViewById(R.id.imageView2);
        previewImage3 = findViewById(R.id.imageView3);

        numberFloorLbl = findViewById(R.id.numberFloorLbl);
        roomDescriptionLbl = findViewById(R.id.roomDescLbl);
        roomStatusLbl = findViewById(R.id.roomStatusLbl);
        numberTypeLbl = findViewById(R.id.roomTypeLbl);

        Intent srcIntent = getIntent();
        roomNumber = srcIntent.getIntExtra("roomNumber", 1);

        getRoomInformation();
    }

    public void getRoomInformation() {
        BackendRequests.getRequest("roomInfo.php?roomNumber=" + roomNumber, this, (response, success) -> {
            try {
                roomType = response.get(0).getString("typeName");
                roomDescription = response.get(0).getString("typeDescription");
                roomFloor = response.get(0).getString("floor");
                updateViewsInformation();
            } catch (JSONException e) {
                Toast.makeText(RoomActivity.this, getResources().getString(R.string.failedToGetRoomInformation), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    public void setupRoomStatus() {
        BackendRequests.getRequest("checkAvailability.php?roomNumber=" + roomNumber, this, (response, success) -> {
            try {
                boolean available = response.get(0).getBoolean("available");
                roomStatus = available?getResources().getString(R.string.roomStatusAvailable):getResources().getString(R.string.roomStatusBooked);
                roomStatusLbl.setText(roomStatus);
                if (!available) {
                    roomStatusLbl.setTextColor(PublicData.BOOKED_COLOR);
                } else {
                    roomStatusLbl.setTextColor(PublicData.AVAILABLE_COLOR);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateViewsInformation() {
        getSupportActionBar().setTitle(roomType);
        numberFloorLbl.setText(getResources().getString(R.string.roomNumberPlaceholder)+" "+roomNumber+getResources().getString(R.string.roomFloorPlaceholder)+" "+roomFloor);
        roomDescriptionLbl.setText(roomDescription);
        numberTypeLbl.setText(roomType);
        loadImages();
    }

    public void loadImages() {
        int img1 = 0;
        int img2 = 0;
        int img3 = 0;
        switch (roomType) {
            case PublicData.ONE_BED:
                img1 = R.drawable.onebed1;
                img2 = R.drawable.onebed2;
                img3 = R.drawable.onebed3;
                break;
            case PublicData.TWO_BED:
                img1 = R.drawable.twobed1;
                img2 = R.drawable.twobed2;
                img3 = R.drawable.twobed3;
                break;
            case PublicData.STANDARD:
                img1 = R.drawable.standard1;
                img2 = R.drawable.standard2;
                img3 = R.drawable.standard3;
                break;
            case PublicData.SUITE:
                img1 = R.drawable.suite1;
                img2 = R.drawable.suite2;
                img3 = R.drawable.suite3;
                break;
            default:
                break;
        }
        previewImage1.setImageResource(img1);
        previewImage2.setImageResource(img2);
        previewImage3.setImageResource(img3);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, ReserveActivity.class);
        intent.putExtra("roomNumber", roomNumber);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.room_menu, menu);
        reserveRoomBtn = menu.findItem(R.id.reserveRoomBtn);
        if (PublicData.loggedType.equals(PublicData.userTypeReceptionist)) {
            reserveRoomBtn.setEnabled(false);
        }
        setupRoomStatus();
        return super.onCreateOptionsMenu(menu);
    }
}