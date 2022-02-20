package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ReservedRoomsAdapter extends RecyclerView.Adapter<ReservedRoomsAdapter.ViewHolder> {

    private ArrayList<Reservation> list;
    private Context context;

    public ReservedRoomsAdapter(ArrayList<Reservation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView roomType = (TextView) cardView.findViewById(R.id.roomType);
        TextView roomDesc = (TextView) cardView.findViewById(R.id.roomDesc);
        Button checkInBtn = (Button) cardView.findViewById(R.id.checkInBtn);
        Button checkOutBtn = (Button) cardView.findViewById(R.id.checkOutBtn);

        Reservation reservation = list.get(position);
        roomType.setText(reservation.getRoomType()+" #"+reservation.getRoomId());
        String descTxt = "Reservation #"+reservation.getReservationId()+"\nStart Date: "+reservation.getStartDate().substring(0,10)+"\nEnd Date: "+reservation.getEndDate().substring(0,10);

        if (reservation.getCheckInTime().equals(PublicData.emptyDate)) {
            checkOutBtn.setEnabled(false);
        } else if (reservation.getCheckOutTime().equals(PublicData.emptyDate)) {
            checkInBtn.setEnabled(false);
            descTxt+="\nCheck In: "+reservation.getCheckInTime().substring(11);
        } else {
            checkInBtn.setEnabled(false);
            checkOutBtn.setEnabled(false);
            descTxt+="\nChecked In: "+reservation.getCheckInTime().substring(11);
            descTxt+="\nChecked Out: "+reservation.getCheckOutTime().substring(11);
        }
        if (PublicData.loggedType.equals(PublicData.userTypeReceptionist)) {
            descTxt+="\nUser: "+reservation.getUserEmail();
            checkInBtn.setVisibility(View.INVISIBLE);
            checkOutBtn.setVisibility(View.INVISIBLE);
        }
        roomDesc.setText(descTxt);

        HashMap<String, String> map = new HashMap<>();
        map.put("reservationId", reservation.getReservationId());
        checkInBtn.setOnClickListener(e-> {
            BackendRequests.postRequest("checkIn.php", context, map, (response, success) -> {
                if (success) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    reservation.setCheckInTime(formatter.format(date));
                    notifyItemChanged(position);
                }
            });
        });
        checkOutBtn.setOnClickListener(e->{
            BackendRequests.postRequest("checkOut.php", context, map, (response, success) -> {
                if (success) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    reservation.setCheckOutTime(formatter.format(date));
                    notifyItemChanged(position);
                }
            });
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