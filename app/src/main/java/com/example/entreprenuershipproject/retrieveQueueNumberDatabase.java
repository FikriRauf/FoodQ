package com.example.entreprenuershipproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class retrieveQueueNumberDatabase extends Fragment {

    TextView queueNumberDisplay;
    DatabaseReference
            baseDatabaseReference,
            queueNumberDatabaseReference,
            queueNumberChildDatabaseReference;

    TextView
            shopName,
            shopStatus,
            shopAddress,
            randomQueueNumber;

    String
            queueNumber,
            queueStatus,
            queueId,
            currentUserIdDetail,
            firstArrayElement,
            assignedQueueNumberId,
            bundleShopName;

    ArrayList<String> availableQueueNumbers;
    int counter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.queue_number_page, container, false);

        availableQueueNumbers = new ArrayList<>();
        counter = 0;

        getBundleData();
        setLayoutViewsToLocalVariables(root);
        setDatabaseReferences();
        getIdOfCurrentlyLoggedUser();
        getQueueNumberFromDatabase();

        return root;
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleShopName = bundle.getString("shop_Name");
        }
    }
    private void setLayoutViewsToLocalVariables(View root) {
        queueNumberDisplay = root.findViewById(R.id.displayQueueNumberPage);
    }

    private void setDatabaseReferences() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        queueNumberDatabaseReference = baseDatabaseReference.child("queue");
    }


    private void getIdOfCurrentlyLoggedUser() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            currentUserIdDetail = currentFirebaseUser.getUid();
//            Log.d("user Detail", "current logged user id:  " + currentUserIdDetail);
        }
    }

    private void getQueueNumberFromDatabase() {
        queueNumberDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    getAllQueueNumbers(dataSnapshot);
                    setAvailableQueueNumbersToArray();
                }
                queueNumberAssignment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllQueueNumbers(DataSnapshot dataSnapshot) {
        queueId = dataSnapshot.getKey();
        queueNumber = (String) dataSnapshot.child("queueNumber").getValue();
        queueStatus = (String) dataSnapshot.child("queueStatus").getValue();

        Log.d("Q Detail: ", " "
                + "\nqueueId: " + queueId
                + "\nqueueNumber: " + queueNumber
                + "\nqueueStatus: " + queueStatus
                + "\n ");
    }

    private void setAvailableQueueNumbersToArray() {
        if (queueStatus.equals("available")) {
            availableQueueNumbers.add(counter, queueNumber);
            counter++;
        }
        Log.d("Queue Array", " "
                + "\nqArray: " + availableQueueNumbers
                + "\nCounter: " + counter);
    }

    private void queueNumberAssignment() {
        getFirstArrayElementAsAssignedQueueNumber();
        getFirstArrayElementFromDatabase();
    }

    private void getFirstArrayElementAsAssignedQueueNumber() {
        int firstElement = 0;
        firstArrayElement = availableQueueNumbers.get(firstElement);
        queueNumberDisplay.setText(firstArrayElement);
    }

    private void getFirstArrayElementFromDatabase() {
        queueNumberDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    searchAssignedQueueNumberInDatabase(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchAssignedQueueNumberInDatabase(DataSnapshot dataSnapshot) {
        String queueNumberFromDatabase = (String) dataSnapshot.child("queueNumber").getValue();

        if (queueNumberFromDatabase != null) {
            if (queueNumberFromDatabase.equals(firstArrayElement)) {
                getAssignedQueueNumberId(dataSnapshot);
                setUserAndShopDetailsToAssignedQueuNumber();
            }
        }
    }

    private void getAssignedQueueNumberId(DataSnapshot dataSnapshot) {
        assignedQueueNumberId = dataSnapshot.getKey();
        Log.d("assigned qNumber", " "
                + "\nID of queue number from  first array element " + assignedQueueNumberId);
    }

    private void setUserAndShopDetailsToAssignedQueuNumber() {
        if (assignedQueueNumberId != null) {
            queueNumberChildDatabaseReference = queueNumberDatabaseReference.child(assignedQueueNumberId);
        } else {
            Log.d("HELLLLOOO", "U FCK UP");
        }

        queueNumberChildDatabaseReference.child("queueStatus").setValue("unavailable");
        queueNumberChildDatabaseReference.child("queueState").setValue("Waiting");
        queueNumberChildDatabaseReference.child("userId").setValue(currentUserIdDetail);
        queueNumberChildDatabaseReference.child("shopName").setValue(bundleShopName);
    }

}
