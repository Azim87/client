package com.kubatov.client.ui.profile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class profileFragment extends CoreFragment {
    @BindView(R.id.image_view_log_off) ImageView logOffImageView;
    @BindView(R.id.profile_image) ImageView clientsProfileImageView;
    @BindView(R.id.profile_name) TextView clientsName;
    @BindView(R.id.profile_age) TextView clientsAge;
    @BindView(R.id.profile_registration) TextView clientsRegistrationDate;

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
                clientsAge.setText(clientUploads.getAge());
                clientsRegistrationDate.setText(clientUploads.getRegistrationTime());

            }

            @Override
            public void onFailure(Exception e) {
                ShowToast.me(e.getMessage());
            }
        });
    }


    @OnClick(R.id.image_view_log_off)
    void logOff(View view) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            PhoneAuthActivity.start(getContext());
        }
    }

    @OnClick(R.id.image_view_edit)
    void editProfile(View view) {
        RegistrationActivity.start(getContext());
        getActivity().finish();
    }
}
