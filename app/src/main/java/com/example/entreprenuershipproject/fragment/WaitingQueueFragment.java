package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entreprenuershipproject.AdapterRetrieveCustomerQueueNumber;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.classRetrieveQueueNumber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WaitingQueueFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<classRetrieveQueueNumber> queueWaitingList;
    AdapterRetrieveCustomerQueueNumber adapterRetrieveCustomerQueueNumber;
    String currentUserId;

    DatabaseReference
            queueDatabaseReference,
            baseDatabaseReference,
            firstQueueDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_waiting_queue, container, false);
        recyclerView = root.findViewById(R.id.queueRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        queueDatabaseReference = baseDatabaseReference.child("queue");
        firstQueueDatabaseReference = queueDatabaseReference.child("-MJGwPg2telALWmoMjC3");

        queueWaitingList = new ArrayList<>();

        getIdOfCurrentlyLoggedUser();
        getQueueDataFromDatabase();

        return root;
    }

    private void getQueueDataFromDatabase() {
        queueDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    classRetrieveQueueNumber retrieveQueue = new classRetrieveQueueNumber();
                    String getQueuesAssignedUser = dataSnapshot.child("userId").getValue(String.class);

                    generateQueueListThatUserWasAssignedTo(getQueuesAssignedUser, dataSnapshot, retrieveQueue);
                }
                adapterRetrieveCustomerQueueNumber = new AdapterRetrieveCustomerQueueNumber(getContext(), queueWaitingList);
                recyclerView.setAdapter(adapterRetrieveCustomerQueueNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void generateQueueListThatUserWasAssignedTo(String getQueuesAssignedUser, DataSnapshot dataSnapshot, classRetrieveQueueNumber retrieveQueue) {
        if (getQueuesAssignedUser != null) {
            if (getQueuesAssignedUser.equals(currentUserId)) {

                retrieveQueue.setQueuedShopName(dataSnapshot.child("shopName").getValue(String.class));
                retrieveQueue.setQueueNumber(dataSnapshot.child("queueNumber").getValue(String.class));
                retrieveQueue.setQueueState(dataSnapshot.child("queueState").getValue(String.class));

                queueWaitingList.add(retrieveQueue);
            }
        }
    }

    private void getIdOfCurrentlyLoggedUser() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {

            currentUserId = currentFirebaseUser.getUid();
            Log.d("user Detail", "current logged user id:  " + currentUserId);
        }
    }
}
