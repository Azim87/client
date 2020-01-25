package com.kubatov.client.ui.tripdetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.BookTrip;
import com.kubatov.client.model.Trip;
import com.kubatov.client.ui.chat.ChatActivity;
import com.kubatov.client.ui.tripdetails.adapter.TripAdapter;
import com.kubatov.client.util.FirebaseNotificationMessageSender;
import com.kubatov.client.util.SharedHelper;
import com.kubatov.client.util.ShowToast;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kubatov.client.util.Constants.DRIVER_NUMBERS;
import static com.kubatov.client.util.Constants.SHARED_KEY;

public class TripDetailsActivity extends AppCompatActivity {
    public final static String TRIP = "trip";

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
    @BindView(R.id.worm_dots_indicator)
    WormDotsIndicator wormDotsIndicator;
    @BindView(R.id.trip_book_button)
    Button bookingButton;
    @BindView(R.id.trip_progress)
    ProgressBar progressBar;
    private Button buttonDecrement;
    private Button buttonIncrement;
    private TextView textView;

    private TripAdapter adapter;
    private Map<String, Object> tripMap = new HashMap<>();
    private int mCount = 1;
    private String tripDriversNumber;
    private String tripAvailableSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        new FirebaseNotificationMessageSender(Volley.newRequestQueue(this));
        adapter = new TripAdapter();
        getDetailedTripInfo();
        initViewPager();


        App.clientRepository.getTripBookData(new IClientRepository.onBookedCallback() {
            @Override
            public void onSuccess(BookTrip bookList) {
                Log.e("ololo", tripDriversNumber+"/"+bookList.getDriversNumber());
               if (bookList.isAccept() == true && tripDriversNumber.equals(bookList.getDriversNumber())) {
                   bookingButton.setEnabled(false);
               }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void initViewPager() {
        tripImgViewPager.setAdapter(adapter);
        wormDotsIndicator.setViewPager(tripImgViewPager);
    }

    private void getDetailedTripInfo() {
        Trip trip = (Trip) getIntent().getSerializableExtra(TRIP);
        tripAvailableSeats = trip.getSeats();
        tripDriversNumber = trip.getPhoneNumber();

        textViewDate.setText("День поездки:");
        textDate.setText(trip.getDate());
        textViewFrom.setText("из города -> ");
        textFrom.setText(trip.getFrom());
        textViewTo.setText("в город ->");
        textTo.setText(trip.getTo());
        textViewPrice.setText("цена поездки: ");
        textPrice.setText(trip.getPrice());
        textViewSeats.setText("свободных мест: ");
        textSeats.setText(trip.getSeats() + " из " + trip.getCarSeats());
        textViewCarModel.setText("марка машины: ");
        textCarModel.setText(trip.getCarMark() + " " + trip.getCarModel());
        textViewDriversName.setText("имя водителья: ");
        textDriversName.setText(trip.getName());

        tripMap.put("driversNumber", trip.getPhoneNumber());
        ArrayList<String> images = new ArrayList<>();
        images.add(trip.getCarImage());
        images.add(trip.getCarImage1());
        images.add(trip.getCarImage2());
        adapter.setImageList(images);

        SharedHelper.setShared(TripDetailsActivity.this,
                SHARED_KEY,
                DRIVER_NUMBERS,
                trip.getPhoneNumber());
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
        finish();
    }

    @OnClick(R.id.trip_book_button)
    void bookTripClick(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.custom_dialog_layout, null);

        textView = promptView.findViewById(R.id.dialog_count);
        buttonIncrement = promptView.findViewById(R.id.dialog_plus);
        buttonDecrement = promptView.findViewById(R.id.dialog_minus);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setMessage("*нужно указать количество мест");
        alertDialogBuilder.setIcon(R.drawable.ic_send_black_24dp);
        alertDialogBuilder
                .setTitle(tripDriversNumber)
                .setCancelable(false);
        alertDialogBuilder.setPositiveButton("Отправить", (dialog, which) -> {

            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    FirebaseNotificationMessageSender.sendMessage(tripDriversNumber, "df", "fdf");
                    int number = Integer.parseInt(tripAvailableSeats);
                    Log.d("ololo", "onFinish: " + number);

                    if (mCount > number) {
                        ShowToast.me("свободных мест " + number);
                        progressBar.setVisibility(View.INVISIBLE);
                        return;
                    } else {
                        tripMap.put("seats", String.valueOf(mCount));
                        App.clientRepository.getTripBookData(tripMap);
                        progressBar.setVisibility(View.INVISIBLE);
                        ShowToast.me("вы забронировали " + mCount + " местo");
                    }
                }
            }.start();
        });
        alertDialogBuilder.setNegativeButton("Отмена", (dialog, which) -> ShowToast.me("Отмена"));

        buttonIncrement.setOnClickListener(view1 -> increment());
        buttonDecrement.setOnClickListener(view12 -> decrement());

        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    private void increment() {
        mCount++;
        textView.setText(String.valueOf(mCount));
        if (mCount >= 1) {
            buttonDecrement.setVisibility(View.VISIBLE);
        } if (mCount >= Integer.parseInt(tripAvailableSeats)) {
            buttonIncrement.setVisibility(View.INVISIBLE);
        }
    }

    private void decrement() {
        mCount--;
        textView.setText(String.valueOf(mCount));
        if (mCount > 0) {
            buttonDecrement.setVisibility(View.INVISIBLE);
        } if (mCount <= Integer.parseInt(tripAvailableSeats)){
            buttonIncrement.setVisibility(View.VISIBLE);
        }
    }
}
