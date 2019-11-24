package com.kubatov.client.ui.fragmentOne.recycler;

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
    private OnTripItemClickListener mItemClickListener;

    public DriversRecyclerAdapter(OnTripItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DriversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trips, parent, false);
        return new DriversViewHolder(view, mItemClickListener);
    }

    public void setTrip(List<Trip> trip) {
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

    public class DriversViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnTripItemClickListener mListener;

        @BindView(R.id.text_view_to) TextView mTextTo;
        @BindView(R.id.text_view_from) TextView mTextFrom;
        @BindView(R.id.text_view_price) TextView mTextPrice;
        @BindView(R.id.text_view_date) TextView mTextDate;
        @BindView(R.id.text_view_id) TextView mTextId;

        public DriversViewHolder(@NonNull View itemView, OnTripItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onTripClick(position);
        }

        private void onBind(Trip trip) {
            mTextTo.setText("To -> " + trip.getTo());
            mTextFrom.setText("From -> " + trip.getFrom());
            mTextPrice.setText("Price -> " + trip.getPrice());
            mTextDate.setText("Date -> " + trip.getDate());
            mTextId.setText(trip.getId());
        }
    }
}
