package com.kubatov.client.ui.TripDetailsActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.kubatov.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity {
    @BindView(R.id.trip_text)
    TextView textView;

    public static void start(
            Context context,
            String tripDate,
            String tripTo,
            String tripFrom,
            String tripPrice,
            String tripSeats) {
        Intent intent = new Intent(context, TripDetailsActivity.class);
        intent.putExtra("date", tripDate);
        intent.putExtra("to", tripTo);
        intent.putExtra("from", tripFrom);
        intent.putExtra("price", tripPrice);
        intent.putExtra("seats", tripSeats);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        String tripDate = getIntent().getStringExtra("date");
        String tripTo = getIntent().getStringExtra("to");
        String tripFrom = getIntent().getStringExtra("from");
        String tripPrice = getIntent().getStringExtra("price");
        String tripSeats = getIntent().getStringExtra("seats");
        textView.setText("Откуда: -> " + tripFrom + " Куда -> "  + tripTo);

    }
}
