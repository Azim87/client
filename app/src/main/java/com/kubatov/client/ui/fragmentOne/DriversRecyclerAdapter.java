package com.kubatov.client.ui.fragmentOne;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.client.R;
import com.kubatov.client.model.Trip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriversRecyclerAdapter extends RecyclerView.Adapter<DriversRecyclerAdapter.DriversViewHolder> {

    private List<Trip> mClient = new ArrayList<>();

    @NonNull
    @Override
    public DriversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drivers_order, parent, false);
        return new DriversViewHolder(view);
    }

    void setTrip(List<Trip> trip) {
        mClient.clear();
        mClient.addAll(trip);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DriversViewHolder holder, int position) {
        holder.onBind(mClient.get(position));
    }

    @Override
    public int getItemCount() {
        return mClient.size();
    }

    public class DriversViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hello)
        TextView view;
        @BindView(R.id.hello2)
        TextView view2;
        @BindView(R.id.hello3)
        TextView view3;
        @BindView(R.id.hello4)
        TextView view4;

        public DriversViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void onBind(Trip trip) {
            view.setText("Date: " + trip.getDate());
            view2.setText("From: " + trip.getFrom());
            view3.setText("To: " + trip.getTo());
            view4.setText("Price: " + trip.getPrice());
        }
    }
}
