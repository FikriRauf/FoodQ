package com.example.entreprenuershipproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entreprenuershipproject.AdapterShopSearch;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.retrieveQueueNumberDatabase;
import com.example.entreprenuershipproject.searchShop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopFragment2 extends Fragment implements AdapterShopSearch.OnItemListener {

    private static final String TAG = "ShopFragment";
    RecyclerView recycleView;
    DatabaseReference shopDatabaseReference;
    private ArrayList<searchShop> searchingShopList;
    private AdapterShopSearch AdapterShopSearch;
    private Context mContext;
    ShopFragment2 shopFragment2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_shop_2, container, false);
        recycleView = root.findViewById(R.id.shopRecycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setHasFixedSize(true);
//        searchView = root.findViewById(R.id.searchView);

        shopDatabaseReference = FirebaseDatabase.getInstance().getReference().child("shop");

        searchingShopList = new ArrayList<>();
        shopFragment2 = new ShopFragment2();

        clearAll();
        getShopDataFromDatabase();

        return root;
    }

    private void getShopDataFromDatabase() {
        Query query = shopDatabaseReference;

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

                AdapterShopSearch = new AdapterShopSearch(getContext(), searchingShopList, shopFragment2.getContext());
                recycleView.setAdapter(AdapterShopSearch);
                AdapterShopSearch.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearAll() {
        if (searchingShopList != null) {
            searchingShopList.clear();

            if (AdapterShopSearch != null) {
                AdapterShopSearch.notifyDataSetChanged();
            }
        }

        searchingShopList = new ArrayList<>();
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: ");
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.shopFragmentChanger, new shopDetailsFragment());
        fragmentTransaction.commit();
    }
}
