package com.kubatov.client.ui.tripdetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.ui.chat.ChatActivity;
import com.kubatov.client.ui.dialog.Dialog;
import com.kubatov.client.ui.tripdetails.adapter.TripAdapter;
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
    private TextView textView;

    private TripAdapter adapter;
    private String tripDriversNumber;
    private Map<String, Object> tripMap = new HashMap<>();
    private int mCount;
    private  String tripAvailableSeats;


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
        wormDotsIndicator.setViewPager(tripImgViewPager);
    }

    private void getDetailedTripInfo() {
        Intent intent = getIntent();
        String tripDate = intent.getStringExtra(TRIP_DATE);
        String tripTo = intent.getStringExtra(TRIP_TO);
        String tripFrom = intent.getStringExtra(TRIP_FROM);
        String tripPrice = intent.getStringExtra(TRIP_PRICE);
        tripAvailableSeats = intent.getStringExtra(TRIP_ALL_SEATS);
        Log.d("ololo", "getDetailedTripInfo: " + tripAvailableSeats);
        String tripAllSeats = intent.getStringExtra(TRIP_AVAILABLE_SEATS);
        String tripCarModel = intent.getStringExtra(TRIP_CAR_MODEL);
        String tripCarMark = intent.getStringExtra(TRIP_CAR_MARK);
        String tripDriversName = intent.getStringExtra(TRIP_DRIVERS_NAME);
        tripDriversNumber = intent.getStringExtra(TRIP_DRIVERS_NUMBER);
        tripMap.put("driversNumber", tripDriversNumber);
        ArrayList<String> img = intent.getStringArrayListExtra(IMG);
        adapter.setImageList(img);

        SharedHelper.setShared(TripDetailsActivity.this,
                SHARED_KEY,
                DRIVER_NUMBERS,
                tripDriversNumber);


        DisplayTripDetailsInfo(tripDate, tripTo, tripFrom, tripPrice, tripAllSeats, tripAvailableSeats,
                tripCarModel, tripCarMark, tripDriversName);
    }

    private void DisplayTripDetailsInfo(String tripDate, String tripTo, String tripFrom,
                                        String tripPrice, String tripAllSeats, String tripAvailSeats,
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
        textSeats.setText(tripAvailSeats + " из " + tripAllSeats);
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
        finish();
    }

    @OnClick(R.id.trip_book_button)
    void bookTripClick(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.custom_dialog_layout, null);

        textView = promptView.findViewById(R.id.dialog_count);
        Button buttonIncrement = promptView.findViewById(R.id.dialog_plus);
        buttonDecrement = promptView.findViewById(R.id.dialog_minus);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setMessage("*нужно указать количество мест");
        alertDialogBuilder.setIcon(R.drawable.ic_send_black_24dp);
        alertDialogBuilder
                .setTitle(tripDriversNumber)
                .setCancelable(false);
        alertDialogBuilder.setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        int number = Integer.parseInt(tripAvailableSeats);
                        Log.d("ololo", "onFinish: " + number);

                        if (mCount > number) {
                            ShowToast.me("свободно только " + number + " мест");
                            progressBar.setVisibility(View.INVISIBLE);
                            return;
                        } else {
                            tripMap.put("seats", mCount);
                            App.clientRepository.getTripBookData(tripMap);
                            progressBar.setVisibility(View.INVISIBLE);
                            ShowToast.me("вы забронировали " + mCount + " мест");
                        }
                    }
                }.start();

            }
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
        if (mCount == 1) {
            buttonDecrement.setVisibility(View.VISIBLE);
        }
    }

    private void decrement() {
        mCount--;
        if (mCount == 1) {
            textView.setText(String.valueOf(mCount));
        } else {
            textView.setText(String.valueOf(mCount));
            if (mCount == 0) {
                buttonDecrement.setVisibility(View.INVISIBLE);
            }

        }
    }
}
