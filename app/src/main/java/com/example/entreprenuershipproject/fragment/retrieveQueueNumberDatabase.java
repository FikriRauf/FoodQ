package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.entreprenuershipproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class retrieveQueueNumberDatabase extends Fragment {
    private static final String TAG = "retrieveQueueNumberData";
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
            bundleShopName,
            bundleBooked,
            assignedQueueNumber;

    Button proceedBtn;
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
        getCurrentlyLoggedUserId();
        getQueueNumberFromDatabase();
        proceedToShopMenu();

        return root;
    }


    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleShopName = bundle.getString("shop_Name");
            bundleBooked = bundle.getString("booked");
        }
    }

    private void setLayoutViewsToLocalVariables(View root) {
        queueNumberDisplay = root.findViewById(R.id.displayQueueNumberPage);
        proceedBtn = root.findViewById(R.id.proceedBtn);
    }

    private void setDatabaseReferences() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        queueNumberDatabaseReference = baseDatabaseReference.child("queue");
    }

    private void getCurrentlyLoggedUserId() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            currentUserIdDetail = currentFirebaseUser.getUid();
//            Log.d(TAG, "current logged user id:  " + currentUserIdDetail);
        }
    }

    private void getQueueNumberFromDatabase() {
        queueNumberDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    getAllQueueNumbers(dataSnapshot);
                    setAvailableQueueNumbersToArray();
                }
                queueNumberAssignment(snapshot);
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

//        Log.d(TAG, "getAllQueueNumbers: Q Detail: "
//                + "\nqueueId: " + queueId
//                + "\nqueueNumber: " + queueNumber
//                + "\nqueueStatus: " + queueStatus
//                + "\n ");
    }

    private void setAvailableQueueNumbersToArray() {
        if (queueStatus.equals("available")) {
            availableQueueNumbers.add(counter, queueNumber);
            counter++;
        }
//        Log.d(TAG, "setAvailableQueueNumbersToArray: Queue Array"
//                + "\nqArray: " + availableQueueNumbers
//                + "\nCounter: " + counter);
    }

    private void queueNumberAssignment(DataSnapshot snapshot) {
        getFirstArrayElementAsAssignedQueueNumber();
        getFirstArrayElementFromDatabase(snapshot);
    }

    private void getFirstArrayElementAsAssignedQueueNumber() {
        int firstElement = 0;
        firstArrayElement = availableQueueNumbers.get(firstElement);
        queueNumberDisplay.setText(firstArrayElement);
    }

    private void getFirstArrayElementFromDatabase(DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            searchAssignedQueueNumberInDatabase(dataSnapshot);
        }
    }

    private void searchAssignedQueueNumberInDatabase(DataSnapshot dataSnapshot) {
        String queueNumberFromDatabase = (String) dataSnapshot.child("queueNumber").getValue();

        if (queueNumberFromDatabase != null) {
            if (queueNumberFromDatabase.equals(firstArrayElement)) {
                assignedQueueNumber = queueNumberFromDatabase;
//                Log.d(TAG, "searchAssignedQueueNumberInDatabase: " + assignedQueueNumber);
                getAssignedQueueNumberId(dataSnapshot);
                setUserAndShopDetailsToAssignedQueuNumber();
            }
        }
    }

    private void getAssignedQueueNumberId(DataSnapshot dataSnapshot) {
        assignedQueueNumberId = dataSnapshot.getKey();
//        Log.d(TAG, "assigned qNumber"
//                + "\nID of queue number from  first array element " + assignedQueueNumberId);
    }

    private void setUserAndShopDetailsToAssignedQueuNumber() {
        if (assignedQueueNumberId != null) {
            queueNumberChildDatabaseReference = queueNumberDatabaseReference.child(assignedQueueNumberId);
        } else {
            Log.d(TAG,"setUserAndShopDetailsToAssignedQueuNumber: HELLLLOOO");
        }

        queueNumberChildDatabaseReference.child("queueStatus").setValue("unavailable");
        queueNumberChildDatabaseReference.child("queueState").setValue("Waiting");
        queueNumberChildDatabaseReference.child("userId").setValue(currentUserIdDetail);
        queueNumberChildDatabaseReference.child("shopName").setValue(bundleShopName);
        if (bundleBooked != null) {
            queueNumberChildDatabaseReference.child("reservation").setValue(bundleBooked);
        }
    }

    private void proceedToShopMenu() {
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendBundle = new Bundle();
                setBundleDataToFragment(sendBundle);

                Fragment shopMenuFragmentParent = new shopMenuFragmentParent();
                shopMenuFragmentParent.setArguments(sendBundle);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.shopDetailFragmentChanger, shopMenuFragmentParent);
                fragmentTransaction.commit();

            }
        });
    }

    private void setBundleDataToFragment(Bundle sendBundle) {
        sendBundle.putString("shop_Name", bundleShopName);
        sendBundle.putString("queue_Number", assignedQueueNumber);
    }
}
