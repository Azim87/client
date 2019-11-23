package com.kubatov.client.ui.fragmentOne;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.model.ClientUpload;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleFragmentOne extends CoreFragment {
    private boolean isClicked = true;
    private BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottom_sheet)
    View bottomSheet;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void setUpView(View view) {
        ButterKnife.bind(this, view);
        initRecyclerView();
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    private void initRecyclerView() {

        List<ClientUpload> client = new ArrayList<>();
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        DriversRecyclerAdapter adapter = new DriversRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        adapter.setDrivers(client);
    }


    @OnClick(R.id.bottom_sheet_search)
    void onOpenBottomSheetClick() {
        if (isClicked) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            Log.d("ololo", "onOpenBottomSheetClick: " + "open");

        } else {
            isClicked = false;
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            Log.d("ololo", "onOpenBottomSheetClick: " + "close");
        }
        isClicked = !isClicked;
    }
}
