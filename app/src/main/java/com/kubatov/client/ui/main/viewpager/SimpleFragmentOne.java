package com.kubatov.client.ui.main.viewpager;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.ui.fragmentOne.DriversRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleFragmentOne extends CoreFragment {
    private boolean isClicked = true;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void setUpView(View view) {
        ButterKnife.bind(this, view);
        initRecyclerView();
    }

    private void initRecyclerView() {

        List<ClientUpload> client = new ArrayList<>();
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        client.add(new ClientUpload("oeopoeir", "akdjkladf"));
        DriversRecyclerAdapter adapter = new DriversRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        adapter.setDrivers(client);
    }


    /*@OnClick(R.id.bottom_sheet_search)
    void onOpenBottomSheetClick() {
        if (isClicked) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        } else {
            isClicked = false;
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        isClicked = !isClicked;
    }*/
}
