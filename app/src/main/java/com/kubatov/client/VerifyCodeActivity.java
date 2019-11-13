package com.kubatov.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyCodeActivity extends AppCompatActivity {
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks changedCallbacks;
    private String mVerification;
    private PhoneAuthProvider.ForceResendingToken mResendingToken;

    @BindView(R.id.edit_text_phone_number)
    EditText mEditTextCode;

    @BindView(R.id.main_info_text_view)
    TextView mainTextView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, VerifyCodeActivity.class);
        intent.putExtra("phonenumber", phoneNumber);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        getPhoneNumber();
    }

    private void getPhoneNumber() {
        String phoneNumber = getIntent().getStringExtra("phonenumber");
        Log.d("ololo", "getPhoneNumber: " + phoneNumber);
        sendVerificationCode(phoneNumber);
    }


    @OnClick(R.id.button_send)
    void signIn(View v) {
        String code = mEditTextCode.getText().toString().trim();

        if (code.isEmpty() || code.length() < 6) {
            mEditTextCode.setError("Enter valid code");
            mEditTextCode.requestFocus();
            return;
        }
        verifyVerificationCode(code);
    }

    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerification, code);
        newUserSignIn(credential);
    }

    private void newUserSignIn(final PhoneAuthCredential authCredential) {
        FirebaseAuth.getInstance().signInWithCredential(authCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        RegistrationActivity.start(this);
                        finish();
                        Toast.makeText(VerifyCodeActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VerifyCodeActivity.this, "Не успешно", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendVerificationCode(String phoneNumber) {
        mProgressBar.setVisibility(View.VISIBLE);
        verificationUser();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                VerifyCodeActivity.this,
                changedCallbacks
        );
    }

    private void verificationUser() {
        changedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mEditTextCode.setText(code);
                }
                newUserSignIn(phoneAuthCredential);
            }

            @Override
            public void onCodeSent(String verification, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verification, forceResendingToken);
                mVerification = verification;
                mResendingToken = forceResendingToken;
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(VerifyCodeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
