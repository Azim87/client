package com.kubatov.client.ui.trip.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.model.Trip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriversRecyclerAdapter
        extends RecyclerView.Adapter<DriversRecyclerAdapter.DriversViewHolder>
        implements Filterable {
    private List<Trip> mClient = new ArrayList<>();
    private List<Trip> mClientSearch;


    private OnTripItemClickListener mItemClickListener;

    public DriversRecyclerAdapter(OnTripItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mClientSearch = mClient;
        notifyDataSetChanged();
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

    @Override
    public Filter getFilter() {
        return tripFilter;
    }

    private Filter tripFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<Trip> filterList = new ArrayList<>();
                for (int i = 0; i < mClientSearch.size(); i++) {
                    if ((mClientSearch.get(i).getTo().toLowerCase()).contains(constraint.toString().toLowerCase())) {
                        filterList.add(mClientSearch.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mClientSearch.size();
                results.values = mClientSearch;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mClient = (List<Trip>) results.values;
            notifyDataSetChanged();
        }
    };

    public class DriversViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnTripItemClickListener mListener;

        @BindView(R.id.trip_image) ImageView carImageView;
        @BindView(R.id.text_view_to) TextView mTextTo;
        @BindView(R.id.text_view_from) TextView mTextFrom;
        @BindView(R.id.text_view_price) TextView mTextPrice;
        @BindView(R.id.text_view_date) TextView mTextDate;

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
            mTextTo.setText("Куда -> " + trip.getTo());
            mTextFrom.setText("Откуда -> " + trip.getFrom());
            mTextPrice.setText("Цена -> " + trip.getPrice());
            mTextDate.setText("Дата поездки -> " + trip.getDate());
            Glide.with(App.instance)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(trip.getCarImage())
                    .into(carImageView);
        }
    }
}
