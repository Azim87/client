package com.kubatov.client.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.ui.auth.PhoneAuthActivity;
import com.kubatov.client.ui.auth.RegistrationActivity;
import com.kubatov.client.util.ShowToast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class profileFragment extends CoreFragment {
    @BindView(R.id.profile_image)
    ImageView clientsProfileImageView;
    @BindView(R.id.profile_name)
    TextView clientsName;
    @BindView(R.id.profile_age)
    TextView clientsAge;
    @BindView(R.id.profile_registration)
    TextView clientsRegistrationDate;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void setUpView(View view) {
        ButterKnife.bind(this, view);
        getProfileData();
    }

    private void getProfileData() {
        App.clientRepository.getClientInfo(new IClientRepository.clientCallback() {
            @Override
            public void onSuccess(ClientUpload clientUploads) {
                Glide.with(clientsProfileImageView.getContext())
                        .load(clientUploads.getProfileImage())
                        .apply(RequestOptions.circleCropTransform())
                        .into(clientsProfileImageView);
                clientsName.setText(clientUploads.getName() + " " + clientUploads.getFamilyName());
                clientsAge.setText(String.valueOf(clientUploads.getAge()));
                clientsRegistrationDate.setText(clientUploads.getRegistrationTime());
            }

            @Override
            public void onFailure(Exception e) {
                ShowToast.me(e.getMessage());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        if (item != null)
            item.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                RegistrationActivity.start(Objects.requireNonNull(getContext()));
                break;
            case R.id.action_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Программадан чыгуу!")
                        .setPositiveButton("Ооба", (dialog, which) -> signOut())
                        .setNegativeButton("Жок", (dialog, which) -> {
                        });
                builder.create();
                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            PhoneAuthActivity.start(Objects.requireNonNull(getContext()));
        }
    }
}
