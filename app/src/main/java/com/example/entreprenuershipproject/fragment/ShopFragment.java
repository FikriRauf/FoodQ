package com.example.entreprenuershipproject.fragment;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.searchShop;
import com.example.entreprenuershipproject.searchShopAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ShopFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ShopFragment extends Fragment {

    RecyclerView recycleView;
    DatabaseReference databaseReference;
    private ArrayList<searchShop> searchingShopList;
    private searchShopAdapter searchShopAdapter;
    private Context mContext;

    private void getDataFromDatabase() {
        Query query = databaseReference;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Log.d("team","child is " + snapshot.getChildren());
                    searchShop searchShop = new searchShop();

                    searchShop.setShopImage(snapshot1.child("shopImage").getValue().toString());
                    searchShop.setShopName(snapshot1.child("shopName").getValue().toString());
                    searchShop.setShopStatus(snapshot1.child("shopStatus").getValue().toString());

                    searchingShopList.add(searchShop);
                }

                searchShopAdapter = new searchShopAdapter(getContext(), searchingShopList);
                recycleView.setAdapter(searchShopAdapter);
                searchShopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearAll() {
        if (searchingShopList != null) {
            searchingShopList.clear();

            if (searchShopAdapter != null) {
                searchShopAdapter.notifyDataSetChanged();
            }
        }

        searchingShopList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        recycleView = root.findViewById(R.id.shopRecycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setHasFixedSize(true);
//        searchView = root.findViewById(R.id.searchView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shop");

        searchingShopList = new ArrayList<>();

        clearAll();
        getDataFromDatabase();

        return root;
    }


}