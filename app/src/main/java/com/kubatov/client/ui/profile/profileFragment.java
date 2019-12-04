package com.kubatov.client.ui.profile;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.ui.auth.PhoneAuthActivity;
import com.kubatov.client.ui.auth.RegistrationActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class profileFragment extends CoreFragment {
    @BindView(R.id.image_view_log_off)
    ImageView logOffImageView;
    @BindView(R.id.profile_image)
    ImageView clientsProfileImageView;
    @BindView(R.id.profile_name_text_view)
    TextView clientsName;

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
            public void onSuccess(List<ClientUpload> clientUploads) {
                Glide.with(clientsProfileImageView.getContext()).load(clientUploads.get(1).getProfileImage()).into(clientsProfileImageView);
                clientsName.setText(clientUploads.get(1).getName());

            }

            @Override
            public void onFailure(Exception e) {
                Log.d("ololo", "onFailure: " + e.getLocalizedMessage());
            }
        });
    }

    @OnClick(R.id.image_view_log_off)
    void logOff(View view) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            PhoneAuthActivity.start(getContext());
            getActivity().finish();
        }

    }

    @OnClick(R.id.image_view_edit)
    void editProfile(View view) {
        RegistrationActivity.start(getContext());
        getActivity().finish();
    }
}
