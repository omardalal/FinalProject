package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private ArrayList<Room> list;
    private Context context;

    public RoomAdapter(ArrayList<Room> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView previewImage = (ImageView) cardView.findViewById(R.id.previewImage);
        TextView roomType = (TextView) cardView.findViewById(R.id.roomType);
        TextView roomDesc = (TextView) cardView.findViewById(R.id.roomDesc);

        Room room = list.get(position);
        roomType.setText(room.getRoomType()+" #"+room.getRoomNumber());
        roomDesc.setText(room.getRoomDescription());
        previewImage.setImageResource(room.getDrawableRcs());

        cardView.setOnClickListener(e->{
            Intent intent = new Intent(context, RoomActivity.class);
            intent.putExtra("roomNumber", room.getRoomNumber());
            context.startActivity(intent);
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