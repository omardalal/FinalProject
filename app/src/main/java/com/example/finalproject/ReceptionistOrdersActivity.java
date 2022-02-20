package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReceptionistOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.orders));

        ordersRecycler = findViewById(R.id.ordersRecyclerView);
        setupOrderItems();
    }

    public void setupOrderItems() {
        BackendRequests.getRequest("customerOrders.php", this, (response, success) -> {
            ArrayList<Order> orders = new ArrayList<>();
            for (int i=0; i<response.size(); i++) {
                JSONObject obj = response.get(i);
                try {
                    Order order = new Order(obj.getString("orderId"), obj.getString("itemName"), obj.getString("status"), obj.getString("roomNumber"));
                    orders.add(order);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ordersRecycler.setLayoutManager(new LinearLayoutManager(this));
            ReceptionistOrdersRecyclerAdapter adapter = new ReceptionistOrdersRecyclerAdapter(orders, this);
            ordersRecycler.setAdapter(adapter);
        });
    }
}