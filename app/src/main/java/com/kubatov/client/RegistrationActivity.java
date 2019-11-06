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

import com.google.firebase.auth.FirebaseAuth;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_CLIENT_IMAGE_CODE = 1;
    private Uri clientImageUri;

    @BindView(R.id.client_profile_image)
    ImageView clientImageView;
    @BindView(R.id.radio_sex)
    RadioGroup radioGroup;
    @BindView(R.id.radio_button_female)
    RadioButton femaleRadioButton;
    @BindView(R.id.radio_button_male)
    RadioButton maleRadioButton;
    @BindView(R.id.edit_text_age)
    EditText editTextAge;
    @BindView(R.id.edit_text_name)
    EditText editTextName;

    public static void start(Context context) {
        context.startActivity(new Intent(context, RegistrationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Регистрация");
        ButterKnife.bind(this);
        clientImageView.setOnClickListener(this);
    }

    private void getClientImageFromStorage() {
        Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getImageIntent.setType("image/*");
        startActivityForResult(getImageIntent, PICK_CLIENT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CLIENT_IMAGE_CODE && resultCode == RESULT_OK && data.getData() !=null && data !=null){
            clientImageUri = data.getData();
            clientImageView.setImageURI(clientImageUri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.client_profile_image:
                getClientImageFromStorage();
        }
    }
}
