package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerOrdersRecyclerAdapter extends RecyclerView.Adapter<CustomerOrdersRecyclerAdapter.ViewHolder> {

    private ArrayList<Order> list;
    private Context context;

    public CustomerOrdersRecyclerAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrdersRecyclerAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView numberLbl = (TextView) cardView.findViewById(R.id.orderNumber);
        TextView descriptionLbl = (TextView) cardView.findViewById(R.id.orderDesc);
        ImageView statusImg = (ImageView) cardView.findViewById(R.id.statusImg);
        TextView statusLbl = (TextView) cardView.findViewById(R.id.statusLbl);

        String numberLblPrefix = context.getResources().getString(R.string.orderNumber);
        numberLbl.setText(numberLblPrefix+list.get(position).getNumber());
        descriptionLbl.setText(list.get(position).getFoodItem());
        statusLbl.setText(list.get(position).getStatus());

        boolean delivered = list.get(position).getStatus().equals(PublicData.orderStatusDelivered);
        int statusDrawableId = delivered ? R.drawable.delivered : R.drawable.pending;
        statusImg.setImageResource(statusDrawableId);
        int color = delivered?PublicData.AVAILABLE_COLOR:PublicData.PENDING_COLOR;
        statusImg.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
        statusLbl.setTextColor(color);
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