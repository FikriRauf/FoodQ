package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
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
import com.example.entreprenuershipproject.classShop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class shopFragmentChild extends Fragment implements AdapterShopSearch.OnItemClickListener {

    private static final String TAG = "shopFragmentParent";
    RecyclerView recycleView;
    DatabaseReference shopDatabaseReference;
    private ArrayList<classShop> searchingShopList;
    private AdapterShopSearch AdapterShopSearch;
    String
            databaseShopImage,
            databaseShopName,
            databaseShopStatus,
            databaseShopAddress,
            databaseShopOperateHour,
            databaseShopPhoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_shop_child, container, false);
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

            classShop shopValue = new classShop();
            setDatabaseDataToDatabaseVariables(snapshot1);
            bindDatabaseVariableToObject(shopValue);
            bindObjectToArrayList(shopValue);
        }
    }

    private void setDatabaseDataToDatabaseVariables(DataSnapshot snapshot1) {
        databaseShopAddress = snapshot1.child("shopAddress").getValue().toString();
        databaseShopImage = snapshot1.child("shopImage").getValue().toString();
        databaseShopName = snapshot1.child("shopName").getValue().toString();
        databaseShopOperateHour = snapshot1.child("shopOperateHour").getValue().toString();
        databaseShopPhoneNumber = snapshot1.child("shopPhoneNumber").getValue().toString();
        databaseShopStatus = snapshot1.child("shopStatus").getValue().toString();
    }

    private void bindDatabaseVariableToObject(classShop shopValue) {
        shopValue.setShopAddress(databaseShopAddress);
        shopValue.setShopImage(databaseShopImage);
        shopValue.setShopName(databaseShopName);
        shopValue.setShopOperateHour(databaseShopOperateHour);
        shopValue.setShopPhoneNumber(databaseShopPhoneNumber);
        shopValue.setShopStatus(databaseShopStatus);
    }

    private void bindObjectToArrayList(classShop shopValue) {
        searchingShopList.add(shopValue);

    }

    private void bindArrayListToAdapter() {
        AdapterShopSearch = new AdapterShopSearch(getContext(), searchingShopList);
        recycleView.setAdapter(AdapterShopSearch);
//        AdapterShopSearch.notifyDataSetChanged();
        AdapterShopSearch.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        classShop clickItem = searchingShopList.get(position);

        Bundle sentBundle = getClickItemData(clickItem);

        Fragment shopDetailFragmentParent = new shopDetailsFragmentParent();
        shopDetailFragmentParent.setArguments(sentBundle);

        FragmentChangeListener fc= (FragmentChangeListener) getContext();
        if (fc != null) {
            fc.replaceFragment(shopDetailFragmentParent);
        }
    }

    private Bundle getClickItemData(classShop shopValue) {
        Bundle bundle = new Bundle();
        bundle.putString("shop_Name", shopValue.getShopName());
        bundle.putString("shop_Status", shopValue.getShopStatus());
        bundle.putString("shop_Image", shopValue.getShopImage());
        bundle.putString("shop_Address", shopValue.getShopAddress());
        bundle.putString("shop_Operating_Hours", shopValue.getShopOperateHour());
        bundle.putString("shop_Phone_Number", shopValue.getShopPhoneNumber());

        return bundle;
    }
}
