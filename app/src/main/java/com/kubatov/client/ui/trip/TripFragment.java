package com.kubatov.client.ui.trip;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
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

import com.google.firebase.auth.FirebaseAuth;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.Trip;
import com.kubatov.client.ui.auth.PhoneAuthActivity;
import com.kubatov.client.ui.auth.RegistrationActivity;
import com.kubatov.client.ui.trip.recycler.DriversRecyclerAdapter;
import com.kubatov.client.ui.trip.recycler.OnTripItemClickListener;
import com.kubatov.client.ui.tripdetails.TripDetailsActivity;
import com.kubatov.client.util.SharedHelper;
import com.kubatov.client.util.ShowToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kubatov.client.ui.tripdetails.TripDetailsActivity.TRIP;
import static com.kubatov.client.util.Constants.DRIVER_NUMBERS;
import static com.kubatov.client.util.Constants.SHARED_KEY;

public class TripFragment extends CoreFragment implements OnTripItemClickListener {
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
    }

    private void initRecyclerView() {
        adapter = new DriversRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    private void refreshTrips() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            TripFragment.this.getTripData();
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
        searchView.setQueryHint("каяка жылайлы???");
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                RegistrationActivity.start(Objects.requireNonNull(getContext()));
                break;
            case R.id.action_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Вы хотитие выйти?")
                        .setPositiveButton("Да", (dialog, which) -> signOut())
                        .setNegativeButton("Нет", (dialog, which) -> {
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

    //region On Click
    @Override
    public void onTripClick(int position) {
        Intent intent = new Intent(getContext(), TripDetailsActivity.class);
        intent.putExtra(TRIP, mTripList.get(position).getPhoneNumber());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SharedHelper.setShared(getContext(),
                SHARED_KEY,
                DRIVER_NUMBERS,
                mTripList.get(position).getPhoneNumber());
        startActivity(intent);
    }
    //endregion
}
