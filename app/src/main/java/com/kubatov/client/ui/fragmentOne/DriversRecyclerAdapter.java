package com.kubatov.client.ui.fragmentOne;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.client.R;
import com.kubatov.client.model.ClientUpload;

import java.util.ArrayList;
import java.util.List;

public class DriversRecyclerAdapter extends RecyclerView.Adapter<DriversRecyclerAdapter.DriversViewHolder> {

    private List<ClientUpload> mClient = new ArrayList<>();

    @NonNull
    @Override
    public DriversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drivers_order, parent, false);
        return new DriversViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriversViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mClient.size();
    }

    public void setDrivers(List<ClientUpload> upload){
        mClient.clear();
        mClient.addAll(upload);
        notifyDataSetChanged();

    }

    public class DriversViewHolder extends RecyclerView.ViewHolder{

        public DriversViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
