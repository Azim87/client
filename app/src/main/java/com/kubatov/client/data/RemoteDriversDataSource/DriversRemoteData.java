package com.kubatov.client.data.RemoteDriversDataSource;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriversRemoteData {
    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private DatabaseReference mDataBaseReference;


    public void getDriversData(){
        mDataBaseReference = mDataBase.getReference();

        mDataBaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
