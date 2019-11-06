package com.kubatov.client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_CLIENT_IMAGE_CODE = 1;
    private Uri clientImageUri;
    private String age;
    private String name;
    private String sex;
    private RadioButton radioButton;

    @BindView(R.id.client_profile_image)
    ImageView clientImageView;
    @BindView(R.id.radio_sex)
    RadioGroup radioGroup;
    @BindView(R.id.edit_text_age)
    EditText editTextAge;
    @BindView(R.id.edit_text_name)
    EditText editTextName;
    @BindView(R.id.button_save_client)
    Button saveClientInfoButton;

    public static void start(Context context) {
        context.startActivity(new Intent(context, RegistrationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Регистрация");
        ButterKnife.bind(this);
        initViewClicks();
    }

    private void initViewClicks() {
        clientImageView.setOnClickListener(this);
        saveClientInfoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_profile_image:
                getClientImageFromStorage();
                break;
            case R.id.button_save_client:
                saveClientInfoToFireBase();
                getClientAge();
                getClientName();
                getClientSex();
                break;
            default:
        }
    }

    private void getClientImageFromStorage() {
        Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getImageIntent.setType("image/*");
        startActivityForResult(getImageIntent, PICK_CLIENT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CLIENT_IMAGE_CODE &&
                resultCode == RESULT_OK &&
                data.getData() != null &&
                data != null) {
            clientImageUri = data.getData();
            Glide.with(this).load(clientImageUri).into(clientImageView);
        }
    }

    private void getClientSex() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "radioButton: " +  radioButton.getText().toString() + clientImageUri + " " + name +" " + age, Toast.LENGTH_SHORT).show();

    }

    private void getClientAge() {
        age = editTextAge.getText().toString();
    }

    private void getClientName() {
        name = editTextName.getText().toString().trim();
    }

    private void saveClientInfoToFireBase() {
    }
}
