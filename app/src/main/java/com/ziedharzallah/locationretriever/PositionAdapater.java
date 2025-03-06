package com.ziedharzallah.locationretriever;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PositionAdapater extends RecyclerView.Adapter<PositionAdapater.ViewHolder> {
    private List<Position> data;

    public PositionAdapater(List<Position> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PositionAdapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.position_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PositionAdapater.ViewHolder holder, int index) {
        Position item = data.get(index);


        holder.latitude.setText(String.valueOf(item.getLatitude()));
        holder.altitude.setText(String.valueOf(item.getAltitude()));
        holder.longtitude.setText(String.valueOf(item.getLongitude()));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView longtitude;
        TextView altitude;
        TextView latitude;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            longtitude = itemView.findViewById(R.id.longtitude);
            altitude = itemView.findViewById(R.id.altitude);
            latitude = itemView.findViewById(R.id.latitude);
        }
    }
}

