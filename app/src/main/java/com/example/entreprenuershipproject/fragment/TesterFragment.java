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

import java.util.ArrayList;

public class TesterFragment extends Fragment {

    DatabaseReference
            baseDatabaseReference,
            kfcDatabaseReference,
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
            shopNameFromDatabase,
            firstArrayElement,
            assignedQueueNumberId;

    ArrayList<String> availableQueueNumbers;
    int counter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_tester, container, false);

        availableQueueNumbers = new ArrayList<>();
        counter = 0;

        setLayoutViewsToLocalVariables(root);
        setDatabaseReferences();
        getShopNameFromDatabase();
        getIdOfCurrentlyLoggedUser();
        getQueueNumberFromDatabase();


        return root;
    }

    private void setLayoutViewsToLocalVariables(View root) {
        shopAddress = root.findViewById(R.id.shopAddressTester);
        shopName = root.findViewById(R.id.shopTitleTester);
        shopStatus = root.findViewById(R.id.shopStatusTester);
        randomQueueNumber = root.findViewById(R.id.randomAssignQueueNumber);
    }

    private void setDatabaseReferences() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        kfcDatabaseReference = baseDatabaseReference.child("shop").child("-MIi9tVB7OBmVYtj2VMn");
        queueNumberDatabaseReference = baseDatabaseReference.child("queue");
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

//                getKfcData(snapshot);

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

    private void getKfcData(DataSnapshot snapshot) {
        String queueID = "-MJGwPg2telALWmoMjC3";
        shopName.setText(snapshot.child(queueID).child("queueNumber").getValue(String.class));
        shopAddress.setText(snapshot.child(queueID).child("queueStatus").getValue(String.class));
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
        randomQueueNumber.setText(firstArrayElement);
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
        queueNumberChildDatabaseReference.child("shopName").setValue(shopNameFromDatabase);
    }

}
