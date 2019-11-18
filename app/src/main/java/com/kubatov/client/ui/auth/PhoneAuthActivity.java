package com.kubatov.client.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.kubatov.client.R;
import com.kubatov.client.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneAuthActivity extends AppCompatActivity {

    @BindView(R.id.editTextPhone)
    EditText mPhoneEditText;


    public static void start(Context context) {
        context.startActivity(new Intent(context, PhoneAuthActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonRegistration)
    void sendNumber(View view) {
        getUserPhoneNumber();
    }

    private void getUserPhoneNumber() {
        String phoneNumber = mPhoneEditText.getText().toString().trim();
        if (phoneNumber.isEmpty() || phoneNumber.length() < 13) {
            mPhoneEditText.setError("Valid number is required");
            return;
        }
        VerifyCodeActivity.start(this, phoneNumber);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCurrentUser();
    }

    private void checkCurrentUser() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            MainActivity.start(this);
            finish();
        }
    }
}