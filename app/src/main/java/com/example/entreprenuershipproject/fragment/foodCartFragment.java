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

import com.example.entreprenuershipproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class foodCartFragment extends Fragment {
    String
            bundleQueueNumber,
            foodInstruction,
            foodName,
            foodPrice,
            foodQuantity,
            queueNumber;

    DatabaseReference
            baseDatabaseReference,
            cartDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_food_cart, container, false);

        getBundleData();
        setDatabaseReference();
        getCartValues();

        return root;
    }

    private void setDatabaseReference() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        cartDatabaseReference = baseDatabaseReference.child("cart");
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleQueueNumber = bundle.getString("queue_Number");
        }
        Log.d("~~~~~~", " " + bundleQueueNumber);
    }

    private void getCartValues() {
        cartDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    queueNumber = dataSnapshot.child("queueNumber").getValue(String.class);

                    if (queueNumber.equals(bundleQueueNumber)) {
                        foodInstruction = dataSnapshot.child("foodInstruction").getValue(String.class);
                        Log.d("foodCartFragment", "CHKER " + foodInstruction + " " + queueNumber);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
