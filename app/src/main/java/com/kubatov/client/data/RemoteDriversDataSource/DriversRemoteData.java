package com.kubatov.client.data.RemoteDriversDataSource;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.model.BookTrip;
import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.model.Trip;
import com.kubatov.client.ui.chat.model.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DriversRemoteData implements IDriversRemoteData {
    private final static String TRIP = "trip";
    private final static String BOOK = "book";
    private final static String CHAT = "chat";
    private final static String CLIENT = "clients";
    private final static String CHAT_TIME = "chatTime";
    private List<Trip> tripList = new ArrayList<>();
    Context context;
    private String myNumber = FirebaseAuth
            .getInstance()
            .getCurrentUser()
            .getPhoneNumber();

    //region Read trip data from fireBase dataBase
    @Override
    public void getDriversTrips(IClientRepository.onClientCallback tripCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TRIP)
                .addSnapshotListener((snapshots, e) -> {
                    List<Trip> trips = snapshots.toObjects(Trip.class);
                    tripList.clear();
                    tripList.addAll(trips);
                    tripCallback.onSuccess(tripList);
                    for (int i = 0; i < snapshots.getDocuments().size(); i++) {
                        tripList.get(i).setPhoneNumber(snapshots.getDocuments().get(i).getId());
                    }
                });
    }
    //endregion

    //region get trips details data
    @Override
    public void getTripDetails(String phoneNumber, IClientRepository.onTripDetails onTripDetails) {
        FirebaseFirestore tripData = FirebaseFirestore.getInstance();
        tripData.collection(TRIP)
                .document(phoneNumber)
                .addSnapshotListener((documentSnapshot, e) -> {
                    Trip trip = documentSnapshot.toObject(Trip.class);
                    onTripDetails.onTripSuccess(trip);
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
        chatData
                .collection(CHAT)
                .orderBy(CHAT_TIME, Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    List<Chat> chat = snapshots.toObjects(Chat.class);
                    chatCallback.onSuccess(chat);
                });
    }
//endregion

    //region Book trip
    @Override
    public void setTripBookData(Map<String, Object> tripBook) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database
                .collection(BOOK)
                .document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .set(tripBook)
                .addOnSuccessListener(documentReference -> {
                }).addOnFailureListener(e -> {
        });
    }
    //endregion

    //region Booking Detail
    @Override
    public void getBookedTrip(IClientRepository.onBookedCallback bookedCallback) {
        FirebaseFirestore bookedTrip = FirebaseFirestore.getInstance();
        bookedTrip
                .collection(BOOK)
                .document(myNumber)
                .addSnapshotListener((documentSnapshot, e) -> {
                    BookTrip bookTrips = documentSnapshot.toObject(BookTrip.class);
                    bookedCallback.onSuccess(bookTrips);
                });
    }
    //endregion
}
