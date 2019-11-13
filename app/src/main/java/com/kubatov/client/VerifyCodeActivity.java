package com.kubatov.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class VerifyCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks changedCallbacks;
    private String mVerification;
    private PhoneAuthProvider.ForceResendingToken mResendingToken;

    @BindView(R.id.edit_country_code)
    EditText mEditTextCode;
    @BindView(R.id.edit_text_phone_number)
    EditText mEditTextPhoneNumber;
    @BindView(R.id.edit_text_verify_sms_code)
    EditText mEditTextVerificationSmsCode;
    @BindView(R.id.main_info_text_view)
    TextView mainTextView;
    @BindView(R.id.button_send)
    Button buttonSendSms;
    @BindView(R.id.button_verify_sms_code)
    Button buttonVerifySmsCode;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static void start(Context context) {
        context.startActivity(new Intent(context, VerifyCodeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setTitle("Авторизация");
        verification();
        buttonSendSms.setOnClickListener(this);
        buttonVerifySmsCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send:
                sendVerificationCode();
                break;
            case R.id.button_verify_sms_code:

                mProgressBar.setVisibility(View.VISIBLE);

                String code = mEditTextVerificationSmsCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    mEditTextVerificationSmsCode.setError("Enter valid code");
                    mEditTextVerificationSmsCode.requestFocus();
                    return;
                }
                verifyVerificationCode(code);
                break;
        }
    }

    private void sendVerificationCode() {
        mProgressBar.setVisibility(View.VISIBLE);
        String phoneNumber = mEditTextCode.getText().toString().trim() +
                mEditTextPhoneNumber.getText().toString().trim();
        hideViewsOnVerificationCode();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS
                , this,
                changedCallbacks
        );
    }

    private void verification() {
        changedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                mVerification = phoneAuthCredential.getSmsCode();
                if (mVerification != null) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mEditTextVerificationSmsCode.setText(mVerification);
                }
                newUserSignIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                e.fillInStackTrace();
            }

            @Override
            public void onCodeSent(String verification, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verification, forceResendingToken);
                mVerification = verification;
                mResendingToken = forceResendingToken;
            }
        };
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

    private void hideViewsOnVerificationCode() {
        if (mEditTextCode.getText() != null && mEditTextPhoneNumber.getText() != null) {
            mainTextView.setText("Введите код который вы получили");
            mEditTextVerificationSmsCode.setVisibility(View.VISIBLE);
            buttonVerifySmsCode.setVisibility(View.VISIBLE);
            hideViewsOnSmsCodeSend();
        }
    }

    private void hideViewsOnSmsCodeSend() {
        mProgressBar.setVisibility(View.GONE);
        mEditTextCode.setVisibility(View.GONE);
        mEditTextPhoneNumber.setVisibility(View.GONE);
        buttonSendSms.setVisibility(View.GONE);
    }
}
