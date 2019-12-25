package com.kubatov.client.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.kubatov.client.R;
import com.kubatov.client.ui.main.MainActivity;
import com.kubatov.client.util.Constants;
import com.kubatov.client.util.ShowToast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kubatov.client.util.Constants.CLIENT;

public class VerifyCodeActivity extends AppCompatActivity {
    private static final String PHONE_NUMBER = "phonenumber";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks changedCallbacks;
    private String mVerification;
    private PhoneAuthProvider.ForceResendingToken mResendingToken;
    private String code;
    private FirebaseFirestore db;

    @BindView(R.id.edit_text_phone_number)
    EditText mEditTextCode;
    @BindView(R.id.main_info_text_view)
    TextView mainTextView;
    @BindView(R.id.button_back)
    TextView backTextView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, VerifyCodeActivity.class);
        intent.putExtra(PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        getPhoneNumber();
        db = FirebaseFirestore.getInstance();
    }

    private void getPhoneNumber() {
        String phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
        sendVerificationCode(phoneNumber);
    }

    @OnClick(R.id.button_send)
    void signIn(View v) {
        String code = mEditTextCode.getText().toString().trim();
        if (code.isEmpty() || code.length() < 6) {
            mEditTextCode.setError("Смс код должен быть больше 6ти симфолов.");
            mEditTextCode.requestFocus();
            return;
        }
        verifyVerificationCode(code);
    }

    @OnClick(R.id.button_back)
    void onBackPressed(View view) {
        PhoneAuthActivity.start(this);
        finish();
    }

    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerification, code);
        newUserSignIn(credential);
    }

    private void newUserSignIn(final PhoneAuthCredential authCredential) {
        String clientNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        FirebaseAuth.getInstance().signInWithCredential(authCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        db.collection(CLIENT)
                                .document(clientNumber)
                                .addSnapshotListener((documentSnapshot, e) -> {
                                    if (documentSnapshot != null &&
                                            documentSnapshot.exists() &&
                                            documentSnapshot.getData() != null) {
                                        MainActivity.start(VerifyCodeActivity.this);

                                    } else {
                                        RegistrationActivity.start(VerifyCodeActivity.this);
                                    }
                                });
                    } else {
                        ShowToast.me("Не успешно" + task.getException().getLocalizedMessage());
                    }
                });
    }

    private void sendVerificationCode(String phoneNumber) {
        verificationUser();
        mProgressBar.setVisibility(View.VISIBLE);
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
                code = phoneAuthCredential.getSmsCode();
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
