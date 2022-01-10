package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyReservationsActivity extends AppCompatActivity {

    private RecyclerView reservationsRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        getSupportActionBar().setTitle(R.string.reservedRooms);
        reservationsRecycler = findViewById(R.id.reservationsRecycler);

        reservationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        loadRecycler();
    }

    public void loadRecycler() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        BackendRequests.getRequest("userReservedRooms.php?userEmail="+PublicData.loggedEmail, this, (response, success) -> {
            for (int i=0; i<response.size(); i++) {
                JSONObject obj = response.get(i);
                try {
                    final String roomType = obj.getString("typeName");
                    final String roomId = obj.getString("roomId");
                    final String reservationId = obj.getString("reservationId");
                    final String startDate = obj.getString("startDate");
                    final String endDate = obj.getString("endDate");
                    final String checkInTime = obj.getString("checkInTime");
                    final String checkOutTime = obj.getString("checkOutTime");
                    final String userEmail = obj.getString("userEmail");
                    Reservation reservation = new Reservation(roomType, roomId, reservationId, startDate, endDate, checkInTime, checkOutTime, userEmail);
                    reservations.add(reservation);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            ReservedRoomsAdapter adapter = new ReservedRoomsAdapter(reservations, this);
            reservationsRecycler.setAdapter(adapter);
        });
    }
}