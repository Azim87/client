package com.kubatov.client.ui.tripdetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kubatov.client.R;
import com.kubatov.client.ui.chat.ChatActivity;
import com.kubatov.client.ui.tripdetails.adapter.TripAdapter;
import com.kubatov.client.util.SharedHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kubatov.client.util.Constants.DRIVER_NUMBERS;
import static com.kubatov.client.util.Constants.SHARED_KEY;

public class TripDetailsActivity extends AppCompatActivity {
    private final static String IMG = "img";
    private final static String TRIP_DATE = "date";
    private final static String TRIP_TO = "to";
    private final static String TRIP_FROM = "from";
    private final static String TRIP_PRICE = "price";
    private final static String TRIP_ALL_SEATS = "seats";
    private final static String TRIP_AVAILABLE_SEATS = "all_seats";
    private final static String TRIP_CAR_MODEL = "model";
    private final static String TRIP_CAR_MARK = "mark";
    private final static String TRIP_DRIVERS_NAME = "name";
    public final static String TRIP_DRIVERS_NUMBER = "number";
    private TripAdapter adapter;
    private String tripDriversNumber;

    @BindView(R.id.trip_date)
    TextView textViewDate;
    @BindView(R.id.date)
    TextView textDate;
    @BindView(R.id.trip_from)
    TextView textViewFrom;
    @BindView(R.id.from)
    TextView textFrom;
    @BindView(R.id.trip_to)
    TextView textViewTo;
    @BindView(R.id.to)
    TextView textTo;
    @BindView(R.id.trip_price)
    TextView textViewPrice;
    @BindView(R.id.price)
    TextView textPrice;
    @BindView(R.id.trip_seats)
    TextView textViewSeats;
    @BindView(R.id.seats)
    TextView textSeats;
    @BindView(R.id.trip_car_model)
    TextView textViewCarModel;
    @BindView(R.id.car_model)
    TextView textCarModel;
    @BindView(R.id.trip_drivers_name)
    TextView textViewDriversName;
    @BindView(R.id.drivers_name)
    TextView textDriversName;
    @BindView(R.id.trip_view_pager)
    ViewPager tripImgViewPager;
    @BindView(R.id.call_to_driver)
    FloatingActionButton callToDriverButton;

    public static void start(
            Context context, String tripDate, String tripTo, String tripFrom,
            String tripPrice, String tripAllSeats, String tripAvailSeats, String tripCarModel,
            String tripCarMark, String tripDriversName, String tripDriversNumber, List<String> imageList) {
        Intent intent = new Intent(context, TripDetailsActivity.class);
        intent.putExtra(TRIP_DATE, tripDate);
        intent.putExtra(TRIP_TO, tripTo);
        intent.putExtra(TRIP_FROM, tripFrom);
        intent.putExtra(TRIP_PRICE, tripPrice);
        intent.putExtra(TRIP_ALL_SEATS, tripAllSeats);
        intent.putExtra(TRIP_AVAILABLE_SEATS, tripAvailSeats);
        intent.putExtra(TRIP_CAR_MODEL, tripCarModel);
        intent.putExtra(TRIP_CAR_MARK, tripCarMark);
        intent.putExtra(TRIP_DRIVERS_NAME, tripDriversName);
        intent.putExtra(TRIP_DRIVERS_NUMBER, tripDriversNumber);
        intent.putStringArrayListExtra(IMG, (ArrayList<String>) imageList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        adapter = new TripAdapter();
        getDetailedTripInfo();
        initViewPager();
    }

    private void initViewPager() {
        tripImgViewPager.setAdapter(adapter);
    }

    private void getDetailedTripInfo() {
        Intent intent = getIntent();
        String tripDate = intent.getStringExtra(TRIP_DATE);
        String tripTo = intent.getStringExtra(TRIP_TO);
        String tripFrom = intent.getStringExtra(TRIP_FROM);
        String tripPrice = intent.getStringExtra(TRIP_PRICE);
        String tripAllSeats = intent.getStringExtra(TRIP_ALL_SEATS);
        String tripAvailSeats = intent.getStringExtra(TRIP_AVAILABLE_SEATS);
        String tripCarModel = intent.getStringExtra(TRIP_CAR_MODEL);
        String tripCarMark = intent.getStringExtra(TRIP_CAR_MARK);
        String tripDriversName = intent.getStringExtra(TRIP_DRIVERS_NAME);
        tripDriversNumber = intent.getStringExtra(TRIP_DRIVERS_NUMBER);
        ArrayList<String> img = intent.getStringArrayListExtra(IMG);

        SharedHelper.setShared(TripDetailsActivity.this, SHARED_KEY, DRIVER_NUMBERS, tripDriversNumber);

        adapter.setImageList(img);
        DisplayTripDetailsInfo(tripDate, tripTo, tripFrom, tripPrice, tripAllSeats, tripAvailSeats,
                tripCarModel, tripCarMark, tripDriversName);
    }

    private void DisplayTripDetailsInfo(String tripDate, String tripTo, String tripFrom,
                                        String tripPrice, String tripSeats, String tripAvailSeats,
                                        String tripCarModel, String tripCarMark, String tripDriversName) {

        textViewDate.setText("День поездки:");
        textDate.setText(tripDate);
        textViewFrom.setText("из города -> ");
        textFrom.setText(tripFrom);
        textViewTo.setText("в город ->");
        textTo.setText(tripTo);
        textViewPrice.setText("цена поездки: ");
        textPrice.setText(tripPrice);
        textViewSeats.setText("свободных мест: ");
        textSeats.setText(tripAvailSeats + " из " + tripSeats);
        textViewCarModel.setText("марка машины: ");
        textCarModel.setText(tripCarMark + " " + tripCarModel);
        textViewDriversName.setText("имя водителья: ");
        textDriversName.setText(tripDriversName);
    }

    @SuppressLint("MissingPermission")
    @OnClick(R.id.call_to_driver)
    void callToDriver(View view) {
        Dexter.withActivity(TripDetailsActivity.this).withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        makeACall();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                        showSettingsDialog();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TripDetailsActivity.this);
        builder.setTitle("Требуется разрешеня");
        builder.setMessage("Для корректной работы требуется разрещения. Разрешите доступ в Настройках.");
        builder.setPositiveButton("В НАСТРОЙКИ", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @SuppressLint("MissingPermission")
    private void makeACall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setPackage("com.android.server.telecom");
        intent.setData(Uri.parse("tel:" + tripDriversNumber));
        startActivity(intent);
    }

    @OnClick(R.id.text_view_chat)
    void openChatActivity(View view) {
        ChatActivity.start(this);
    }
}
