package com.kubatov.client.ui.main.viewpager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleFragmentOne extends CoreFragment {
    private BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.bottom_sheet)
    View bottom;
    @BindView(R.id.bottom_sheet_search)
    TextView searchImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void setUpView(View view) {
        ButterKnife.bind(this, view);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);

    }
    private boolean isClicked = true;
    @OnClick(R.id.bottom_sheet_search)
    void onOpenBottomSheetClick(){

        Log.d("ololo", "onClick: first click");
        if (isClicked) {
            Log.d("ololo", "onClick:  true");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        } else {
            isClicked = false;
            Log.d("ololo", "onClick:  false");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        isClicked = !isClicked;
    }
}
