package model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private String[] data;
    private onRoomListener MonroomListener;


    public RoomAdapter(String[] data, onRoomListener MonroomListener) {
        this.data = data;
        this.MonroomListener = MonroomListener;
    }

    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view, MonroomListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView txt = (TextView) holder.getTextView();
        txt.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        onRoomListener onroomListener;

        public ViewHolder(View view, onRoomListener onroomListener) {
            super(view);
            this.onroomListener = onroomListener;
            itemView.setOnClickListener(this);
            textView = (TextView) view.findViewById(R.id.recText);
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View view) {
            onroomListener.onRoomClick(getAdapterPosition());
        }
    }

    public interface onRoomListener {
        void onRoomClick(int position);
    }
}
