package com.kubatov.client.ui.auth;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kubatov.client.R;
import com.kubatov.client.ui.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String AGE = "age";
    private static final String NAME = "name";
    private static final String GENDER = "sex";
    private static final String PROFILE = "profileImage";
    private static final String IMAGE_TYPE = "image/*";
    private static final String LOCATION = "avatar/";
    private static final int PICK_CLIENT_IMAGE_CODE = 1;
    private static final String MY_PREFS_NAME = "name";

    private Uri clientImageUri;
    private String gender;
    private String profileImageUri;

    private Map<String, Object> clients = new HashMap<>();
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
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

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
        saveClientInfoButton.setOnClickListener(this);
        getStateFromShared();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_profile_image:
                getClientImageFromStorage();
                break;
            case R.id.button_save_client:
                saveToFireBase();

                break;
            default:
        }
    }

    private void saveToFireBase() {
        if (editTextAge.getText().toString().equals("")) {
            editTextAge.setError("вы не ввели возраст");
        } else if (editTextName.getText().toString().equals("")) {
            editTextName.setError("вы не ввели имя");
            return;
        } else {
            initFireBase();
            MainActivity.start(this);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        saveToFireBase();
    }

    private void initFireBase() {
        getClientSex();
        String age = editTextAge.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        saveStateToShared(name, age);
        clients.put(AGE, age);
        clients.put(NAME, name);
        clients.put(GENDER, gender);
        clients.put(PROFILE, profileImageUri);
        String phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        dataBase
                .collection("clients")
                .document(phoneNumber)
                .set(clients)
                .addOnSuccessListener(documentReference -> {
                })
                .addOnFailureListener(e -> {
                });
    }

    private void saveStateToShared(String name, String age) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("age", age);
        editor.apply();
        Log.d("ololo", "saveStateToShared: " + age + " " + name);
    }

    private void getStateFromShared() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String age = prefs.getString("age", null);
        editTextAge.setText(age);
        editTextName.setText(name);

        Log.d("ololo", "getStateFromShared: " + age + " " + name);
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
        if (requestCode == PICK_CLIENT_IMAGE_CODE && resultCode == RESULT_OK &&
                data.getData() != null && data != null) {
            clientImageUri = data.getData();
            Glide.with(this).load(clientImageUri).into(clientImageView);
            uploadClientImageToStorage();
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
        mProgressBar.setVisibility(View.VISIBLE);
        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference(LOCATION);
        if (clientImageUri != null) {
            StorageReference storageReference = mStorageReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()+ "." + clientImageExtension(clientImageUri));

            storageReference.putFile(clientImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        mProgressBar.setVisibility(View.GONE);
                        saveClientInfoButton.setVisibility(View.GONE);
                        storageReference.getDownloadUrl().addOnSuccessListener(uri ->{
                            if (uri != null){
                            profileImageUri = uri.toString();
                        }
                        });

                        saveClientInfoButton.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Фотография успешно сохранен!", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                e.getLocalizedMessage();
                Toast.makeText(this, "Не удалось сохранить фото!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
