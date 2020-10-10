package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.dataQueue;
import com.example.entreprenuershipproject.dataShop;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderListFragment extends Fragment {

    private static final String TAG = "NewPostActivity";
    private DatabaseReference mDatabase;

    dataShop shopValue;
    dataQueue queueValue;

    String shop_Name, shop_Status, shop_Image, shop_Address;
    int queue_Number;
    String queue_Status;

    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);
        Button submitShopDetail = root.findViewById(R.id.submitShopDetail);
        Button submitQueueNumber = root.findViewById(R.id.submitQueueNumberDetail);

        shopValue = new dataShop();
        queueValue = new dataQueue();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("queue");

        submitShopDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop_Name = "wendy's";
                shop_Status = "open";
                shop_Image = "null";
                shop_Address = "road is something";

                shopValue.setShopName(shop_Name);
                shopValue.setShopStatus(shop_Status);
                shopValue.setShopImage(shop_Image);
                shopValue.setShopAddress(shop_Address);

                mDatabase.push().setValue(shopValue)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        submitQueueNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue_Number = 3;
                queue_Status = "available";

                queueValue.setQueueNumber(queue_Number);
                queueValue.setQueueStatus(queue_Status);

                mDatabase.push().setValue(queueValue)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "queue success", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return root;
    }
}

