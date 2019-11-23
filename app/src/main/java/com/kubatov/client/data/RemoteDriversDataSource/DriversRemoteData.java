package com.kubatov.client.data.RemoteDriversDataSource;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class DriversRemoteData implements IDriversRemoteData {
    private final static String TRIP = "trip";
    private List<Trip> tripList = new ArrayList<>();

    @Override
    public void getDriversTrips(IClientRepository.onClientCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TRIP)
                .get()
                .addOnSuccessListener(snapshots -> {
                    List<Trip> types = snapshots.toObjects(Trip.class);
                    tripList.clear();
                    tripList.addAll(types);
                    callback.onSuccess(tripList);
                }).addOnFailureListener(e -> {
        });
    }
}
