package com.kubatov.client.ui.fragmentOne;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kubatov.client.R;
import com.kubatov.client.core.CoreFragment;
import com.kubatov.client.model.Trip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleFragmentOne extends CoreFragment {
    private final static String TRIP = "trip";
    private boolean isClicked = true;
    private BottomSheetBehavior bottomSheetBehavior;
    private List<Trip> tripList;
    private DriversRecyclerAdapter adapter;


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
        tripList = new ArrayList<>();
        adapter = new DriversRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TRIP)
                .get()
                .addOnSuccessListener(snapshots -> {
                    List<Trip> types = snapshots.toObjects(Trip.class);
                    tripList.addAll(types);
                    adapter.setTrip(tripList);
                    Log.d("ololo", "onSuccess: " + tripList.get(0).getPrice());
                }).addOnFailureListener(e -> Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show());
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
