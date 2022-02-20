package com.example.finalproject;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    private ArrayList<FoodMenuItem> list;
    private ArrayList<FoodMenuItem> selectedItem;

    public MenuRecyclerAdapter(ArrayList<FoodMenuItem> list, ArrayList<FoodMenuItem> selectedItem) {
        this.list = list;
        this.selectedItem = selectedItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(v);
    }

    private int checkPos = -1;
    @Override
    public void onBindViewHolder(@NonNull MenuRecyclerAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView previewImage = (ImageView) cardView.findViewById(R.id.previewImage);
        TextView title = (TextView) cardView.findViewById(R.id.itemTitle);
        TextView description = (TextView) cardView.findViewById(R.id.itemDesc);
        RadioButton itemRB = (RadioButton) cardView.findViewById(R.id.itemCheckbox);

        previewImage.setImageResource((list.get(position).getDrawableId()));
        title.setText(list.get(position).getTitle());
        description.setText(list.get(position).getDescription());

        cardView.setOnClickListener(v -> {
            itemRB.setChecked(!itemRB.isChecked());
            setItem(itemRB, position);
        });

        itemRB.setChecked(position == checkPos);
        itemRB.setOnClickListener(v -> {
            setItem(itemRB, position);
        });
        itemRB.setOnClickListener(v-> {
            setItem(itemRB, position);
        });
    }

    private void setItem(RadioButton itemRB, int position) {
        if (position == checkPos) {
            itemRB.setChecked(false);
            checkPos = -1;
            if (selectedItem.size()==0) {
                selectedItem.add(null);
            } else {
                selectedItem.set(0, null);
            }
        }
        else {
            checkPos = position;
            notifyDataSetChanged();
            if (selectedItem.size()==0) {
                selectedItem.add(list.get(position));
            } else {
                selectedItem.set(0, list.get(position));
            }
        }
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