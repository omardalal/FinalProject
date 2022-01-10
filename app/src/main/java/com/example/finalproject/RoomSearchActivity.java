package com.example.finalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomSearchActivity extends AppCompatActivity {
    private Spinner spin;
    private Button button;
    private RecyclerView recyclerView;

    private ArrayList<Room> rooms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomsearch);
        getSupportActionBar().setTitle(R.string.search);
        spin = findViewById(R.id.spinner);
        button = findViewById(R.id.searchBtn);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadRecycler("");
    }

    public void loadRecycler(String roomType) {
        rooms = new ArrayList<>();
        BackendRequests.getRequest("roomsByType.php?roomType="+roomType, this, (response, success) -> {
            for (int i=0; i<response.size(); i++) {
                JSONObject obj = response.get(i);
                try {
                    String type = obj.getString("typeName");
                    Room room = new Room(type, obj.getString("typeDescription"), getImg(type), Integer.parseInt(obj.getString("number")));
                    rooms.add(room);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            RoomAdapter adapter = new RoomAdapter(rooms, this);
            recyclerView.setAdapter(adapter);
        });
    }

    public void search(View view) {
        loadRecycler(spin.getSelectedItem()+"");
    }

    public int getImg(String roomType) {
        switch (roomType) {
            case PublicData.ONE_BED:
                return R.drawable.onebed1;
            case PublicData.TWO_BED:
                return R.drawable.twobed1;
            case PublicData.STANDARD:
                return R.drawable.standard1;
            case PublicData.SUITE:
                return R.drawable.suite1;
            default:
                return R.drawable.onebed1;
        }
    }
}