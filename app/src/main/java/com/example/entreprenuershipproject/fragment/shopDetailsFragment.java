package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.classShop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shopDetailsFragment extends Fragment {

    DatabaseReference shopDetailDatabaseReference;
    TextView
            shopName,
            shopStatus,
            shopAddress;

    ImageView shopImage;

    String
            s,
            q,
            r,
            w;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_shop_details, container, false);

        shopDetailDatabaseReference = FirebaseDatabase.getInstance().getReference().child("shop");

        shopName = root.findViewById(R.id.databaseShopName);
        shopStatus = root.findViewById(R.id.databaseShopStatus);
        shopAddress = root.findViewById(R.id.databaseShopAddress);
        shopImage = root.findViewById(R.id.databaseShopImage);


        Bundle b = this.getArguments();
        if (b != null) {
            s = b.getString("shop_Name");
            q = b.getString("shop_Status");
            r = b.getString("shop_Image");
            w = b.getString("shop_Address");
        }

        shopName.setText(s);
        shopStatus.setText(q);
        shopAddress.setText(w);
        Glide.with(getContext()).load(r).into(shopImage);

//        getShopDetailFromDatabase();

        return root;
    }

    private void getShopDetailFromDatabase() {
        shopDetailDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopName.setText(snapshot.child("shopName").getValue(String.class));
                shopStatus.setText(snapshot.child("shopStatus").getValue(String.class));
                shopAddress.setText(snapshot.child("shopAddress").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
