package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.entreprenuershipproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TesterFragment extends Fragment {

    DatabaseReference
            baseDatabaseReference,
            kfcDatabaseReference,
            queueNumberDatabaseReference,
            queueNumberChildDatabaseReference;

    TextView
            shopName,
            shopStatus,
            shopAddress;

    String
            queueNumber,
            queueStatus,
            currentUserIdDetail,
            shopNameFromDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_tester, container, false);

        shopAddress = root.findViewById(R.id.shopAddressTester);
        shopName = root.findViewById(R.id.shopTitleTester);
        shopStatus = root.findViewById(R.id.shopStatusTester);

        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        kfcDatabaseReference = baseDatabaseReference.child("shop").child("-MIi9tVB7OBmVYtj2VMn");
        queueNumberDatabaseReference = baseDatabaseReference.child("queue");

//        getShopDetailFromDatabase();
        getQueueNumberFromDatabase();
        getShopNameFromDatabase();
        getIdOfCurrentlyLoggedUser();

        return root;
    }

    private void getIdOfCurrentlyLoggedUser() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            currentUserIdDetail = currentFirebaseUser.getUid();
            Log.d("user Detail", "current logged user id:  " + currentUserIdDetail);
        }
    }

    private void getShopNameFromDatabase() {
        kfcDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopNameFromDatabase = snapshot.child("shopName").getValue(String.class);
                Log.d("shop", "shop name: " + shopNameFromDatabase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getQueueNumberFromDatabase() {
        queueNumberDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String queueID = "-MJGwPg2telALWmoMjC3";
                shopName.setText(snapshot.child(queueID).child("queueNumber").getValue(String.class));
                shopAddress.setText(snapshot.child(queueID).child("queueStatus").getValue(String.class));

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String qId = dataSnapshot.getKey();
                    Log.d("Q Id", "queueId: " + qId);

                    queueNumber = (String) dataSnapshot.child("queueNumber").getValue();
                    queueStatus = (String) dataSnapshot.child("queueStatus").getValue();
                    Log.d("Q Detail: ", " "+ "\nqueueNumber: " + queueNumber + "\nqueueStatus: " + queueStatus + "\n ");

                    if (queueStatus != null) {
                        if (queueStatus.equals("unavailable")) {
                            Log.d("qStatus", "qStatus: " + queueNumber);

                            String queueNumberId = dataSnapshot.getKey();
                            Log.d("qStatus", "queue number with status unavailable' ID: " + queueNumberId);

                            if (queueNumberId != null) {
                                queueNumberChildDatabaseReference = queueNumberDatabaseReference.child(queueNumberId);
                            }

                            queueNumberChildDatabaseReference.child("queueStatus").setValue("available");
                            queueNumberChildDatabaseReference.child("userId").setValue(currentUserIdDetail);
                            queueNumberChildDatabaseReference.child("shopName").setValue(shopNameFromDatabase);

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getShopDetailFromDatabase() {
        kfcDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String shopID = snapshot.getKey();
                Log.d("title", "shopID: " + shopID);
                shopName.setText(snapshot.child("shopName").getValue(String.class));
                shopAddress.setText(snapshot.child("shopAddress").getValue(String.class));
                shopStatus.setText(snapshot.child("shopStatus").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
