package com.example.entreprenuershipproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class retrieveQueueNumberDatabase extends Fragment {

    private DatabaseReference queueNumberDBReference;
    ArrayList<classRetrieveQueueNumber> queueNumberList;
    AdapterAssignCustomerQueueNumber queueNumberAdapter;
    TextView queueNumberDisplay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.queue_number_page, container, false);

        queueNumberDisplay = root.findViewById(R.id.displayQueueNumberPage);

        queueNumberDBReference = FirebaseDatabase.getInstance().getReference().child("queue");
        queueNumberList = new ArrayList<>();

        getDataFromDatabase();

        return root;
    }

    private void getDataFromDatabase() {
        queueNumberDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getQueueNumber(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getQueueNumber(DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()) {
            classRetrieveQueueNumber qNumber = new classRetrieveQueueNumber();

            qNumber.setQueueNumber(ds.child("queueNumber").getValue().toString());

            queueNumberList.add(qNumber);
        }
        queueNumberAdapter = new AdapterAssignCustomerQueueNumber(getContext(), queueNumberList);

    }


}
