package com.kubatov.client.data.RemoteDriversDataSource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.model.Trip;
import com.kubatov.client.ui.chat.model.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DriversRemoteData implements IDriversRemoteData {
    private final static String TRIP = "trip";
    private final static String CHAT = "chat";
    private final static String CLIENT = "clients";
    private List<Trip> tripList = new ArrayList<>();

    //region Read trip data from fireBase dataBase
    @Override
    public void getDriversTrips(IClientRepository.onClientCallback tripCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TRIP)
                .get()
                .addOnSuccessListener((QuerySnapshot snapshots) -> {
                    List<Trip> trips = snapshots.toObjects(Trip.class);
                    tripList.clear();
                    tripList.addAll(trips);
                    tripCallback.onSuccess(tripList);
                    for (int i = 0; i < snapshots.getDocuments().size(); i++) {
                        tripList.get(i).setPhoneNumber(snapshots.getDocuments().get(i).getId());
                    }
                }).addOnFailureListener(e -> {
        });
    }
    //endregion

    //region ClientInfo
    @Override
    public void getClientsInfo(IClientRepository.clientCallback clientCallback) {
        String clientNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        FirebaseFirestore data = FirebaseFirestore.getInstance();
        data.collection(CLIENT)
                .document(clientNumber)
                .get()
                .addOnSuccessListener(snapshot -> {
                    ClientUpload clientUpload = snapshot.toObject(ClientUpload.class);
                    clientCallback.onSuccess(clientUpload);
                });
    }
    //endregion

    //region Chat
    @Override
    public void sendChatMessage(Map<String, Object> chatMap) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(CHAT)
                .add(chatMap)
                .addOnSuccessListener(documentReference -> {
                }).addOnFailureListener(e -> {
                });
    }

    @Override
    public void getChatMessage(IClientRepository.chatCallback chatCallback) {
        FirebaseFirestore chatData = FirebaseFirestore.getInstance();
        chatData.collection(CHAT)
                .get()
                .addOnSuccessListener(snapshots -> {
                    List<Chat> chat = snapshots.toObjects(Chat.class);
                    chatCallback.onSuccess(chat);
                }).addOnFailureListener(e -> chatCallback.onFailure(e));
    }
    //endregion
}
