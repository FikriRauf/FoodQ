package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.dataShop;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderListFragment extends Fragment {

    private static final String TAG = "NewPostActivity";
    private DatabaseReference mDatabase;
    dataShop shopValue;
    String shop_Name, shop_Status, shop_Image;
    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);
        Button submitted = root.findViewById(R.id.submitDetail);

        shopValue = new dataShop();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("shop");

        submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop_Name = "tealive";
                shop_Status = "open";
                shop_Image = "null";

                shopValue.setShopName(shop_Name);
                shopValue.setShopStatus(shop_Status);
                shopValue.setShopImage(shop_Image);

                mDatabase.push().setValue(shopValue)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return root;
    }
}

