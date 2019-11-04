package com.kubatov.client;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSplashScreenTime();
    }

    private void setSplashScreenTime() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                getRegistrationActivity();
            }
        }.start();

    }

    private void getRegistrationActivity() {
        AuthActivity.start(this);
        finish();
    }
}
