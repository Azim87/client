package com.kubatov.client.ui.fragmentOne;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.Trip;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleFragmentOne extends CoreFragment {
    private boolean isClicked = true;
    private BottomSheetBehavior bottomSheetBehavior;
    private DriversRecyclerAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottom_sheet)
    View bottomSheet;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void setUpView(View view) {
        ButterKnife.bind(this, view);
        initRecyclerView();
        refreshTrips();
        getTripData();
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    private void initRecyclerView() {
        adapter = new DriversRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }


    private void refreshTrips() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            SimpleFragmentOne.this.getTripData();
            swipeRefreshLayout.setRefreshing(true);
        });
    }


    private void getTripData() {
        App.clientRepository.getTripsInfo(new IClientRepository.onClientCallback() {
            @Override
            public void onSuccess(List<Trip> tripList) {
                swipeRefreshLayout.setRefreshing(false);
                adapter.setTrip(tripList);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("ololo", "Ошибка: -> " + e.getLocalizedMessage());
            }
        });
    }

    //region On Click
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
    //endregion
}
