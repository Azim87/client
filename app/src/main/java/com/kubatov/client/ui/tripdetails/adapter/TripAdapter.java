package com.kubatov.client.ui.tripdetails.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kubatov.client.R;
import com.kubatov.client.model.TripImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripAdapter extends PagerAdapter {
    private ArrayList<String> stringList = new ArrayList<>();

    @BindView(R.id.trip_image_views)
    ImageView imageView;

    public void setImageList(ArrayList<String> imageList) {
        stringList.clear();
        stringList.addAll(imageList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater
                .from(container.getContext())
                .inflate(R.layout.trip_image_layout, null);

        ButterKnife.bind(this, v);
        Glide.with(imageView.getContext())
                .load(stringList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .optionalCenterCrop()
                .into(imageView);

        container.addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }
}
