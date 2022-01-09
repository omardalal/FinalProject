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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import model.RoomAdapter;

public class RoomSearchActivity extends AppCompatActivity implements RoomAdapter.onRoomListener {
    public Spinner spin;
    public Button button;
    public RecyclerView recyclerView;
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomsearch);
        spin = findViewById(R.id.spinner);
        button = findViewById(R.id.button2);
        recyclerView = findViewById(R.id.recycler);
        button.setOnClickListener(this::btn_onClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BackendRequests.getRequest("RoomType.php", RoomSearchActivity.this, new RequestCallback() {

            @Override
            public void onResponse(ArrayList<JSONObject> response, boolean success) {
                try {
                    ArrayList<String> results = new ArrayList<>();

                    for (JSONObject o : response) {
                        results.add(o.getString("typeName"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoomSearchActivity.this, android.R.layout.simple_spinner_dropdown_item, results);
                    spin.setAdapter(adapter);
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        });
    }

    public void btn_onClick(View view) {
        String roomType = spin.getSelectedItem().toString();
        BackendRequests.getRequest("Rooms.php?typeName=" + roomType, RoomSearchActivity.this, new RequestCallback() {

            @Override
            public void onResponse(ArrayList<JSONObject> response, boolean success) {
                try {

                    for (JSONObject o : response) {
                        items.add(o.getString("number"));
                    }
                    Log.d(TAG, "onResponse: " + items.toString());
                    String[] arr = new String[items.size()];
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = items.get(i);
                    }
                    Log.d(TAG, "onResponse: " + Arrays.toString(arr));
                    RoomAdapter adapter = new RoomAdapter(arr, RoomSearchActivity.this);

                    recyclerView.setAdapter(adapter);
                } catch (Exception x) {

                }
            }
        });

    }

    @Override
    public void onRoomClick(int position) {
        String str = items.get(position);
        Log.d(TAG, "onRoomClick: " + str);
        Intent intent = new Intent(this, RoomInfoActivity.class);
        intent.putExtra("number", str);
        startActivity(intent);
    }
}