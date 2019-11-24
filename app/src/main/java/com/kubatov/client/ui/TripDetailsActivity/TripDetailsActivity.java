package com.kubatov.client.ui.TripDetailsActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kubatov.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity {
    private final static String TRIP_DATE = "date";
    private final static String TRIP_TO = "to";
    private final static String TRIP_FROM = "from";
    private final static String TRIP_PRICE = "price";
    private final static String TRIP_SEATS = "seats";
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
        intent.putExtra(TRIP_DATE, tripDate);
        intent.putExtra(TRIP_TO, tripTo);
        intent.putExtra(TRIP_FROM, tripFrom);
        intent.putExtra(TRIP_PRICE, tripPrice);
        intent.putExtra(TRIP_SEATS, tripSeats);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        showDetailedTripInfo();
    }
    private void showDetailedTripInfo(){
        String tripDate = getIntent().getStringExtra("date");
        String tripTo = getIntent().getStringExtra("to");
        String tripFrom = getIntent().getStringExtra("from");
        String tripPrice = getIntent().getStringExtra("price");
        String tripSeats = getIntent().getStringExtra("seats");
        textView.setText("Откуда: -> " + tripFrom + " Куда -> "  + tripTo + " " + tripSeats);
    }
}
