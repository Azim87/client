package com.kubatov.client.ui.TripDetailsActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kubatov.client.R;

public class TripDetailsActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TripDetailsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
    }
}
