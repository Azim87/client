package com.kubatov.client.ui.trip;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.Trip;
import com.kubatov.client.ui.trip.recycler.DriversRecyclerAdapter;
import com.kubatov.client.ui.trip.recycler.OnTripItemClickListener;
import com.kubatov.client.ui.tripdetails.TripDetailsActivity;
import com.kubatov.client.util.ShowToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class tripFragment extends CoreFragment implements OnTripItemClickListener {
    private DriversRecyclerAdapter adapter;
    private List<Trip> mTripList;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
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
        mTripList = new ArrayList<>();
        refreshTrips();
        getTripData();
        progressBar.setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
    }

    private void initRecyclerView() {
        adapter = new DriversRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    private void refreshTrips() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            tripFragment.this.getTripData();
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
                ShowToast.me(e.getMessage());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

   /* private void searchData() {
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }*/

    //region On Click
    @Override
    public void onTripClick(int position) {
        String tripDate = mTripList.get(position).getDate();
        String tripTo = mTripList.get(position).getTo();
        String tripFrom = mTripList.get(position).getFrom();
        String tripPrice = mTripList.get(position).getPrice();
        String tripAllSeats = mTripList.get(position).getSeats();
        String tripAvailSeats = mTripList.get(position).getCarSeats();
        String tripCarModel = mTripList.get(position).getCarModel();
        String tripCarMark = mTripList.get(position).getCarMark();
        String tripDriversName = mTripList.get(position).getName();
        String tripDriverNumber = mTripList.get(position).getPhoneNumber();
        List<String> imageList = new ArrayList<>();
        imageList.add(mTripList.get(position).getCarImage());
        imageList.add(mTripList.get(position).getCarImage1());
        imageList.add(mTripList.get(position).getCarImage2());

        TripDetailsActivity.start(getContext(), tripDate, tripTo, tripFrom,
                tripPrice, tripAllSeats, tripAvailSeats, tripCarModel, tripCarMark,
                tripDriversName, tripDriverNumber, imageList);
    }
    //endregion
}
