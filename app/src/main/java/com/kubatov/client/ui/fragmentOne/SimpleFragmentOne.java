package com.kubatov.client.ui.fragmentOne;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.Trip;
import com.kubatov.client.ui.TripDetailsActivity.TripDetailsActivity;
import com.kubatov.client.ui.TripDetailsActivity.adapter.TripAdapter;
import com.kubatov.client.ui.fragmentOne.recycler.DriversRecyclerAdapter;
import com.kubatov.client.ui.fragmentOne.recycler.OnTripItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleFragmentOne extends CoreFragment implements OnTripItemClickListener {
    private boolean isClicked = true;
    private BottomSheetBehavior bottomSheetBehavior;
    private DriversRecyclerAdapter adapter;
    private List<Trip> mTripList;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottom_sheet)
    View bottomSheet;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void setUpView(View view) {
        ButterKnife.bind(this, view);
        initRecyclerView();
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mTripList = new ArrayList<>();
        refreshTrips();
        getTripData();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        adapter = new DriversRecyclerAdapter(this);
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
                progressBar.setVisibility(View.INVISIBLE);
                adapter.setTrip(tripList);
                mTripList.addAll(tripList);
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    //region On Click
    @OnClick(R.id.bottom_sheet_search)
    void onOpenBottomSheetClick() {
        if (isClicked) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            isClicked = false;
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        isClicked = !isClicked;
    }

    @Override
    public void onTripClick(int position) {
        String tripDate = mTripList.get(position).getDate();
        String tripTo = mTripList.get(position).getTo();
        String tripFrom = mTripList.get(position).getFrom();
        String tripPrice = mTripList.get(position).getPrice();
        String tripSeats = mTripList.get(position).getSeats();
        String tripImg = mTripList.get(position).getCarImage();
        String tripCarModel = mTripList.get(position).getCarModel();
        String tripCarMark = mTripList.get(position).getCarMark();
        String tripDriversName = mTripList.get(position).getName();

        List<String> imageList = new ArrayList<>();
        imageList.add(mTripList.get(position).getCarImage());
        imageList.add(mTripList.get(position).getCarImage2());
        imageList.add(mTripList.get(position).getCarImage3());

        TripDetailsActivity.start(
                getContext(),
                tripImg,
                tripDate,
                tripTo,
                tripFrom,
                tripPrice,
                tripSeats,
                tripCarModel,
                tripCarMark,
                tripDriversName,
                imageList);
    }
    //endregion
}
