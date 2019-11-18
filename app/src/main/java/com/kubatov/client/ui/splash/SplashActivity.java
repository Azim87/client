package com.kubatov.client.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.kubatov.client.ui.auth.PhoneAuthActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSplashScreenTime();
    }

    private void setSplashScreenTime() {
        new CountDownTimer(1000, 1100) {
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
        PhoneAuthActivity.start(this);
        finish();
    }
}
