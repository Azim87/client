package com.kubatov.client.data.RemoteDriversDataSource;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class DriversRemoteData implements IDriversRemoteData {
    private final static String TRIP = "trip";
    private final static String CLIENT = "clients";
    private List<Trip> tripList = new ArrayList<>();
    private List<ClientUpload> clientUploads = new ArrayList<>();


    //region Read trip data from fireBase dataBase
    @Override
    public void getDriversTrips(IClientRepository.onClientCallback tripCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TRIP)
                .get()
                .addOnSuccessListener(snapshots -> {
                    List<Trip> trips = snapshots.toObjects(Trip.class);
                    tripList.clear();
                    tripList.addAll(trips);
                    tripCallback.onSuccess(tripList);
                }).addOnFailureListener(e -> {
        });
    }
    //endregion


    @Override
    public void getClientsInfo(IClientRepository.clientCallback clientCallback) {
        FirebaseFirestore data = FirebaseFirestore.getInstance();
        data.collection(CLIENT)
                .get()
                .addOnSuccessListener(snapshots -> {
                    List<ClientUpload> clients = snapshots.toObjects(ClientUpload.class);
                    clientUploads.clear();
                    clientUploads.addAll(clients);
                    clientCallback.onSuccess(clientUploads);
                }).addOnFailureListener(e -> {
        });
    }
}
