package com.kubatov.client.ui.main.viewpager;

import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleFragmentOne extends CoreFragment {
    private BottomSheetBehavior bottomSheetBehavior;


    @BindView(R.id.bottom_sheet)
    View bottom;
    @BindView(R.id.bottom_sheet_search)
    ImageView searchImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void setUpView(View view) {
        ButterKnife.bind(this, view);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);


        searchImageView.setOnClickListener(new View.OnClickListener() {
            boolean isClicked;

            @Override
            public void onClick(View v) {
                if (isClicked) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {
                    isClicked = false;
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                isClicked = !isClicked;
            }
        });
    }
}
