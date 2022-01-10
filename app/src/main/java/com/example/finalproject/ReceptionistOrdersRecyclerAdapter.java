package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReceptionistOrdersRecyclerAdapter extends RecyclerView.Adapter<ReceptionistOrdersRecyclerAdapter.ViewHolder> {

    private ArrayList<Order> list;
    private Context context;

    public ReceptionistOrdersRecyclerAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.receptionist_order_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceptionistOrdersRecyclerAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView numberLbl = (TextView) cardView.findViewById(R.id.orderNumber);
        TextView descriptionLbl = (TextView) cardView.findViewById(R.id.orderDesc);
        RadioButton deliveredRb = (RadioButton) cardView.findViewById(R.id.deliveredRb);
        RadioButton pendingRb = (RadioButton) cardView.findViewById(R.id.pendingRb);
        Button saveBtn = (Button) cardView.findViewById(R.id.saveBtn);

        String numberLblPrefix = context.getResources().getString(R.string.orderNumber);
        numberLbl.setText(numberLblPrefix+list.get(position).getNumber());
        descriptionLbl.setText(list.get(position).getFoodItem()+"\nRoom Number: "+list.get(position).getRoomNumber());

        boolean delivered = list.get(position).getStatus().equals(PublicData.orderStatusDelivered);
        pendingRb.setChecked(!delivered);
        deliveredRb.setChecked(delivered);

        saveBtn.setOnClickListener(e->{
            Map<String, String> map = new HashMap<>();
            String status = pendingRb.isChecked()?PublicData.orderStatusPending:PublicData.orderStatusDelivered;
            map.put("status", status);
            map.put("orderId", list.get(position).getNumber());
            BackendRequests.postRequest("updateOrderStatus.php", context, map, (response, success) -> {});
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}