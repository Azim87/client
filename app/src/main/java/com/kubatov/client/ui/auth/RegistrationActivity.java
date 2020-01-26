package com.kubatov.client.ui.auth;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
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
import com.kubatov.client.util.DateHelper;
import com.yalantis.ucrop.UCrop;

import org.angmarch.views.NiceSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String AGE = "age";
    private static final String NAME = "name";
    private static final String FAMILY_NAME = "familyName";
    private static final String GENDER = "sex";
    private static final String PROFILE = "profileImage";
    private static final String IMAGE_TYPE = "image/*";
    private static final String LOCATION = "avatar/";
    private static final int PICK_CLIENT_IMAGE_CODE = 1;
    private static final String CLIENTS = "clients";
    private static final String TIME = "registrationTime";

    private Uri clientImageUri;
    private String gender;
    private String profileImageUri;
    private StorageReference mStorageReference;
    private SharedPreferences.Editor sharedPref;

    private Map<String, Object> clients = new HashMap<>();
    @BindView(R.id.client_profile_image)ImageView clientImageView;
    @BindView(R.id.radio_sex) RadioGroup radioGroup;
    @BindView(R.id.edit_text_age) NiceSpinner ageSpinner;
    @BindView(R.id.edit_text_name) EditText editTextName;
    @BindView(R.id.edit_text_family_name) EditText editTextFamilyName;
    @BindView(R.id.button_save_client) Button saveClientInfoButton;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Регистрация");
        ButterKnife.bind(this);
        clientImageView.setOnClickListener(this);
        saveClientInfoButton.setOnClickListener(this);
        initSpinner();
        getDataFromShared();
    }

    private void getDataFromShared(){
        SharedPreferences prefs = getSharedPreferences(CLIENTS, MODE_PRIVATE);
        String age = prefs.getString(AGE, null);
        String name = prefs.getString(NAME, null);
        String familyName = prefs.getString(FAMILY_NAME, null);
        ageSpinner.setText(age);
        editTextName.setText(name);
        editTextFamilyName.setText(familyName);
    }

    private void initSpinner() {
        List<String> dataset = new LinkedList<>(Arrays.asList("год рождения"));
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) {
            dataset.add(Integer.toString(i));
        }
        ageSpinner.attachDataSource(dataset);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_profile_image:
                getClientImageFromStorage();
                break;

            case R.id.button_save_client:
                new CountDownTimer(4000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        saveClientInfoButton.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        initFireBase();
                        saveToFireBase();
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }.start();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void saveToFireBase() {
        if (editTextName.getText().toString().equals("") || editTextFamilyName.getText().toString().equals("")) {
            editTextName.setError("вы не ввели имя");
            editTextFamilyName.setError("вы не ввели фамилию");
        } else {
            MainActivity.start(RegistrationActivity.this);
            finish();
        }
    }

    private void initFireBase() {
        getClientSex();
        String age = ageSpinner.getSelectedItem().toString().trim();
        String name = editTextName.getText().toString().trim();
        String familyName = editTextFamilyName.getText().toString().trim();
        saverToShared(
                age,
                name,
                familyName);
        clients.put(AGE, age);
        clients.put(NAME, name);
        clients.put(GENDER, gender);
        clients.put(FAMILY_NAME, familyName);
        clients.put(PROFILE, profileImageUri);
        long time = System.currentTimeMillis();
        clients.put(TIME, DateHelper.convertToDate(String.valueOf(time)));
        String phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        dataBase.collection(CLIENTS)
                .document(phoneNumber)
                .set(clients)
                .addOnSuccessListener(documentReference -> {
                })
                .addOnFailureListener(e -> {
                });
    }

    private void saverToShared(String age, String name, String familyName){
        sharedPref = getSharedPreferences(CLIENTS, MODE_PRIVATE).edit();
        sharedPref.putString(AGE, age);
        sharedPref.putString(NAME, name);
        sharedPref.putString(FAMILY_NAME, familyName);
        sharedPref.apply();
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
        if (requestCode == PICK_CLIENT_IMAGE_CODE && resultCode == RESULT_OK && data.getData() != null && data != null) {
            clientImageUri = data.getData();
            startCrop(clientImageUri);
        } if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
         clientImageUri = UCrop.getOutput(data);
            if (clientImageUri != null) {
                Glide.with(this)
                        .load(clientImageUri)
                        .centerCrop()
                        .into(clientImageView);
                compressImageToSaveDataBase();
            }
        }
    }

    private void startCrop(Uri clientImageUri) {
        String fileName = "sampleCroping";
        fileName += ".jpg";
        fileName += ".png";

        UCrop uCrop = UCrop.of(clientImageUri, Uri.fromFile(new File(getCacheDir(), fileName)));
        uCrop.withAspectRatio(1, 1);
        uCrop.withAspectRatio(3, 4);
        uCrop.withAspectRatio(16, 9);
        uCrop.withAspectRatio(2, 3);
        uCrop.withMaxResultSize(900, 900);
        uCrop.withOptions(getOptions());
        uCrop.start(RegistrationActivity.this);
    }

    private UCrop.Options getOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);
        options.setCompressionQuality(70);
        return options;
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

    private void compressImageToSaveDataBase() {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(profileImageUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), clientImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, bao);
                bitmap.recycle();
                byte[] byteArray = bao.toByteArray();
                uploadClientImageToStorage(byteArray);
    }

    private void uploadClientImageToStorage(byte[] byteArray) {
        mStorageReference = FirebaseStorage.getInstance().getReference(LOCATION);
        if (clientImageUri != null) {
            String number = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            StorageReference storageReference = mStorageReference.child(number + "." + clientImageExtension(clientImageUri));
            storageReference.putBytes(byteArray)
                    .addOnSuccessListener(taskSnapshot -> {
                        mProgressBar.setVisibility(View.GONE);
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            if (uri != null) {
                                profileImageUri = uri.toString();
                            } else {
                                Log.d("ololo", "uploadClientImageToStorage: " + uri.toString());
                            }
                        });
                    }).addOnFailureListener(e -> {
                e.getLocalizedMessage();
                Toast.makeText(this, "Не удалось сохранить фото!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}