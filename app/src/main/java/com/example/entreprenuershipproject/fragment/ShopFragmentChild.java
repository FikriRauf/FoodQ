package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entreprenuershipproject.AdapterShopSearch;
import com.example.entreprenuershipproject.FragmentChangeListener;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.searchShop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ShopFragmentChild extends Fragment implements AdapterShopSearch.OnItemClickListener {

    private static final String TAG = "ShopFragmentParent";
    RecyclerView recycleView;
    DatabaseReference shopDatabaseReference;
    private ArrayList<searchShop> searchingShopList;
    private AdapterShopSearch AdapterShopSearch;

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
//        searchShop = new searchShop();

        clearAll();
        getShopDataFromDatabase();

        return root;
    }

    private void getShopDataFromDatabase() {
        shopDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                bindDatabaseDataToObject(snapshot);
                bindArrayListToAdapter();
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

    private void bindDatabaseDataToObject(DataSnapshot snapshot) {
        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
            searchShop searchShop = new searchShop();

            searchShop.setShopImage(snapshot1.child("shopImage").getValue().toString());
            searchShop.setShopName(snapshot1.child("shopName").getValue().toString());
            searchShop.setShopStatus(snapshot1.child("shopStatus").getValue().toString());
            searchShop.setShopAddress(snapshot1.child("shopAddress").getValue().toString());

            bindObjectToArrayList(searchShop);
        }
    }

    private void bindObjectToArrayList(searchShop searchShop) {
        searchingShopList.add(searchShop);

    }

    private void bindArrayListToAdapter() {
        AdapterShopSearch = new AdapterShopSearch(getContext(), searchingShopList);
        recycleView.setAdapter(AdapterShopSearch);
        AdapterShopSearch.notifyDataSetChanged();
        AdapterShopSearch.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        searchShop clickItem = searchingShopList.get(position);

        Bundle bundle1 = getClickItemData(clickItem);

        Fragment shopDetailFragmentParent = new shopDetailsFragmentParent();
        shopDetailFragmentParent.setArguments(bundle1);
        FragmentChangeListener fc= (FragmentChangeListener) getContext();
        if (fc != null) {
            fc.replaceFragment(shopDetailFragmentParent);
        }
    }

    private Bundle getClickItemData(searchShop searchShop1) {
        Bundle bundle = new Bundle();
        bundle.putString("shop_Name", searchShop1.getShopName());
        bundle.putString("shop_Status", searchShop1.getShopStatus());
        bundle.putString("shop_Image", searchShop1.getShopImage());
        bundle.putString("shop_Address", searchShop1.getShopAddress());

        return bundle;
    }
}
