package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView menuRecycler;
    private ArrayList<FoodMenuItem> selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle(getResources().getString(R.string.menuHeader));

        menuRecycler = findViewById(R.id.menuRecyclerView);
        selectedItem = new ArrayList<>();

        setupMenuItems();
    }

    public void setupMenuItems() {
        BackendRequests.getRequest("menuItems.php", this, (response, success) -> {
            ArrayList<FoodMenuItem> foodMenuItems = new ArrayList<>();
            for (int i=0; i<response.size(); i++) {
                JSONObject obj = response.get(i);
                try {
                    int rcsId = getResources().getIdentifier(obj.getString("itemName").toLowerCase().replace(" ","_"), "drawable", MenuActivity.this.getPackageName());
                    FoodMenuItem item = new FoodMenuItem(obj.getString("itemName"), obj.getString("itemDescription"), rcsId, obj.getInt("itemId"));
                    foodMenuItems.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            menuRecycler.setLayoutManager(new LinearLayoutManager(this));
            MenuRecyclerAdapter adapter = new MenuRecyclerAdapter(foodMenuItems, selectedItem);
            menuRecycler.setAdapter(adapter);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        if (selectedItem.get(0)==null) {
            Toast.makeText(this, getResources().getString(R.string.selectItems), Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("menuItemId", ""+selectedItem.get(0).getItemId());
            map.put("userEmail", PublicData.loggedEmail);
            BackendRequests.postRequest("createMenuOrder.php", this, map, (response, success) -> {
                if (success) {
                    Intent intent = new Intent(this, CustomerOrdersActivity.class);
                    startActivity(intent);
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.foodmenu_menu, menu);
        MenuItem orderBtn = (MenuItem) menu.findItem(R.id.orderBtn);
        return super.onCreateOptionsMenu(menu);
    }
}