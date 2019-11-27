package com.kubatov.client.ui.TripDetailsActivity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.kubatov.client.R;

import java.util.ArrayList;
import java.util.List;

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
        Log.d("ololo", "setImageList: " + stringList);
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

        @SuppressLint("InflateParams") View v = LayoutInflater.from(container.getContext()).inflate(R.layout.trip_image_layout, null);
        ButterKnife.bind(this, v);

        Glide.with(imageView.getContext()).load(stringList.get(position)).into(imageView);

        container.addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }
}
