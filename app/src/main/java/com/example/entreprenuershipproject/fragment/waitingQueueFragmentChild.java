package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
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
import com.example.entreprenuershipproject.FragmentChangeListener;
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
import java.util.LinkedHashMap;
import java.util.Map;

public class waitingQueueFragmentChild extends Fragment implements AdapterRetrieveCustomerQueueNumber.OnItemClickListener{
    final String TAG = "waitingQueueFragmentChild";
    RecyclerView recyclerView;
    ArrayList<classRetrieveQueueNumber> queueWaitingList;
    ArrayList<LinkedHashMap<String,String>> list;
    AdapterRetrieveCustomerQueueNumber adapterRetrieveCustomerQueueNumber;

    String
            currentUserId,
            assignedQueueNumberFromDatabase,
            queueNumberShopName,
            queueNumberState,
            queueReservation,
            databaseShopImage,
            databaseUserId,
            databaseShopName;

    DatabaseReference
            baseDatabaseReference,
            queueDatabaseReference,
            shopDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_waiting_queue_child, container, false);
        recyclerView = root.findViewById(R.id.queueRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        queueWaitingList = new ArrayList<>();
        list = new ArrayList<>();

        setDatabaseReference();
        getCurrentlyLoggedUserId();
        getShopImage();
        getQueueDataFromDatabase();

        return root;
    }

    private void setDatabaseReference() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        queueDatabaseReference = baseDatabaseReference.child("queue");
        shopDatabaseReference = baseDatabaseReference.child("shop");
    }

    private void getCurrentlyLoggedUserId() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            currentUserId = currentFirebaseUser.getUid();
//            Log.d("waitingQueueFragmentChild", "current logged user id Detail:  " + currentUserId);
        }
    }

    private void getShopImage() {
        shopDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    databaseShopName = dataSnapshot.child("shopName").getValue(String.class);
                    databaseShopImage = dataSnapshot.child("shopImage").getValue(String.class);

//                    Log.d("waitingQueueFragmentChild", "shop database reference \n"
//                            + databaseShopImage + "\n"
//                            + databaseShopName + "\n");

                    LinkedHashMap<String, String> test = new LinkedHashMap<>();
                    test.put(databaseShopName, databaseShopImage);
                    list.add(test);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getQueueDataFromDatabase() {
        queueDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                bindDatabaseDataToObject(snapshot);
                bindArrayListToAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if (queueWaitingList != null) {
            queueWaitingList.clear();
            if (adapterRetrieveCustomerQueueNumber != null) {
                adapterRetrieveCustomerQueueNumber.notifyDataSetChanged();
            }
        }
        queueWaitingList = new ArrayList<>();
    }

    private void bindDatabaseDataToObject(DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            classRetrieveQueueNumber retrieveQueue = new classRetrieveQueueNumber();
            getUserIdAssignedToQueueNumber(dataSnapshot);
            getQueueNumberWithCurrentUser(dataSnapshot, retrieveQueue);
        }
    }

    private void getUserIdAssignedToQueueNumber(DataSnapshot dataSnapshot) {
        databaseUserId = dataSnapshot.child("userId").getValue(String.class);
    }

    private void getQueueNumberWithCurrentUser(DataSnapshot dataSnapshot, classRetrieveQueueNumber retrieveQueue) {
        if (databaseUserId != null) {
            if (databaseUserId.equals(currentUserId)) {
                setDatabaseDataToLocalVariables(dataSnapshot);
                bindDatabaseVariableToObject(retrieveQueue);
                bindObjectToArrayList(retrieveQueue);
            }
        }
    }

    private void setDatabaseDataToLocalVariables(DataSnapshot dataSnapshot) {
        assignedQueueNumberFromDatabase = dataSnapshot.child("queueNumber").getValue(String.class);
        queueNumberState = dataSnapshot.child("queueState").getValue(String.class);
        queueNumberShopName = dataSnapshot.child("shopName").getValue(String.class);
        queueReservation = dataSnapshot.child("reservation").getValue(String.class);
    }

    private void bindDatabaseVariableToObject(classRetrieveQueueNumber retrieveQueue) {
        retrieveQueue.setQueuedShopName(queueNumberShopName);
        retrieveQueue.setQueueNumber(assignedQueueNumberFromDatabase);
        retrieveQueue.setQueueState(queueNumberState);
        retrieveQueue.setReservation(queueReservation);

        for(Map<String, String> map : list){
            for(String key : map.keySet()){
                if (key.equals(queueNumberShopName)) {
                    retrieveQueue.setQueueShopImage(map.get(key));
                } else {
                    Log.d(TAG, "bindDatabaseDataToObject error: ");
                }

            }
        }
    }

    private void bindObjectToArrayList(classRetrieveQueueNumber retrieveQueue) {
        queueWaitingList.add(retrieveQueue);
    }

    private void bindArrayListToAdapter() {
        adapterRetrieveCustomerQueueNumber = new AdapterRetrieveCustomerQueueNumber(getContext(), queueWaitingList);
        recyclerView.setAdapter(adapterRetrieveCustomerQueueNumber);
        adapterRetrieveCustomerQueueNumber.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        classRetrieveQueueNumber clickItem = queueWaitingList.get(position);

        Bundle sentBundle = getClickItemData(clickItem);

        Fragment foodCartFragment = new foodCartFragment();
        foodCartFragment.setArguments(sentBundle);

        FragmentChangeListener fc= (FragmentChangeListener) getContext();
        if (fc != null) {
            fc.replaceFragment(foodCartFragment);
        }
    }

    private Bundle getClickItemData(classRetrieveQueueNumber clickItem) {
        Bundle bundle1 = new Bundle();
        bundle1.putString("queue_Number", clickItem.getQueueNumber());

        return bundle1;
    }
}
