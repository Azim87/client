package com.kubatov.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String AGE = "age";
    private static final String NAME = "name";
    private static final String GENDER = "sex";
    private static final String IMAGE = "image";
    private static final String IMAGE_TYPE = "image/*";
    private static final int PICK_CLIENT_IMAGE_CODE = 1;
    private Uri clientImageUri;
    private String gender;

    private StorageReference mStorageReference;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_profile_image:
                getClientImageFromStorage();
                break;
            case R.id.button_save_client:
                initFireBase();
                saveToFireBase();
                break;
            default:
        }
    }

    private void saveToFireBase() {
        if (editTextAge.getText().toString().equals("")) {
            editTextAge.setError("вы ее ввели возраст");
        } else if (editTextName.getText().toString().equals("")) {
            editTextName.setError("вы не ввели имя");
            return;
        } else {
            MainActivity.startMainActivity(this);
            finish();
        }
    }

    private void initFireBase() {
        getClientSex();
        String age = editTextAge.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        Map<String, Object> clients = new HashMap<>();
        clients.put(AGE, age);
        clients.put(NAME, name);
        clients.put(GENDER, gender);
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        dataBase.collection("clients")
                .add(clients)
                .addOnSuccessListener(documentReference -> {
                })
                .addOnFailureListener(e -> {
                });
        uploadClientImageToStorage();
    }

    private void initViewClicks() {
        clientImageView.setOnClickListener(this);
        saveClientInfoButton.setOnClickListener(this);
    }

    private void getClientImageFromStorage() {
        Intent getImageIntent = new Intent();
        getImageIntent.setType(IMAGE_TYPE);
        getImageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(getImageIntent, PICK_CLIENT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CLIENT_IMAGE_CODE &&
                resultCode == RESULT_OK &&
                data.getData() != null && data != null) {
            clientImageUri = data.getData();
            Glide.with(this).load(clientImageUri).into(clientImageView);
        }
    }

    private void getClientSex() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        gender = radioButton.getText().toString();
    }

    private String clientImageExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap typeMap = MimeTypeMap.getSingleton();
        return typeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadClientImageToStorage() {
        mStorageReference = FirebaseStorage.getInstance().getReference("avatar/");
        if (clientImageUri != null) {
            StorageReference storageReference = mStorageReference.child(System.currentTimeMillis()
                    + "." + clientImageExtension(clientImageUri));

            storageReference.putFile(clientImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(this, "Фотография успешно сохранен!", Toast.LENGTH_SHORT).show();

                    }).addOnFailureListener(e -> {
                e.getLocalizedMessage();
                Toast.makeText(this, "Не удалось сохранить фото!", Toast.LENGTH_SHORT).show();
            });
        } else {
        }
    }
}
