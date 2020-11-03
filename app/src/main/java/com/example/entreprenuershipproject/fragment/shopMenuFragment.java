package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entreprenuershipproject.AdapterMenuFood;
import com.example.entreprenuershipproject.FragmentChangeListener;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.classFood;
import com.example.entreprenuershipproject.fragment.tester.TesterShopMenuDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class shopMenuFragment extends Fragment implements AdapterMenuFood.OnItemClickListener {
    String
            bundleShopName,
            bundleAssignedQueueNumber;

    RecyclerView menuRecycleView;
    DatabaseReference shopMenuDatabaseReference;
    ArrayList<classFood> menuList;
    AdapterMenuFood adapterMenuFood;
    classFood foodClass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        menuRecycleView = root.findViewById(R.id.menuRecycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        menuRecycleView.setLayoutManager(linearLayoutManager);
        menuRecycleView.setHasFixedSize(true);

        menuList = new ArrayList<>();
        foodClass = new classFood();

        getBundleData();
        shopMenuDatabaseReference = FirebaseDatabase.getInstance().getReference().child("menu").child(bundleShopName);

        clearAll();
        getMenuDataFromDatabase();


        return root;
    }

    private void clearAll() {
        if (menuList != null) {
            menuList.clear();

            if (adapterMenuFood != null) {
                adapterMenuFood.notifyDataSetChanged();
            }
        }

        menuList = new ArrayList<>();
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleShopName = bundle.getString("shop_Name");
            bundleAssignedQueueNumber = bundle.getString("queue_Number");
        }
    }

    private void getMenuDataFromDatabase() {
        shopMenuDatabaseReference.addValueEventListener(new ValueEventListener() {
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

    private void bindArrayListToAdapter() {
        adapterMenuFood = new AdapterMenuFood(getContext(), menuList);
        menuRecycleView.setAdapter(adapterMenuFood);
        adapterMenuFood.notifyDataSetChanged();
        adapterMenuFood.setOnItemClickListener(this);

    }

    private void bindObjectToArrayList(classFood foodClass) {
        menuList.add(foodClass);

    }

    private void bindDatabaseDataToObject(DataSnapshot snapshot) {
        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
            Log.d("team", "child is " + snapshot.getChildren());
            classFood food = new classFood();

            food.setFoodDescription(snapshot1.child("foodDescription").getValue().toString());
            food.setFoodImage(snapshot1.child("foodImage").getValue().toString());
            food.setFoodName(snapshot1.child("foodName").getValue().toString());
            food.setFoodPrice(snapshot1.child("foodPrice").getValue().toString());

            bindObjectToArrayList(food);
        }
    }

    @Override
    public void onItemClick(int position) {
        classFood clickItem = menuList.get(position);

        Bundle sendBundle = getClickItemData(clickItem);

        Fragment testerShopMenuDetails = new TesterShopMenuDetails();
        testerShopMenuDetails.setArguments(sendBundle);
        FragmentChangeListener fc= (FragmentChangeListener) getContext();
        if (fc != null) {
            fc.replaceFragment(testerShopMenuDetails);
        }
    }

    private Bundle getClickItemData(classFood foodValue) {
        Bundle bundle = new Bundle();
        bundle.putString("food_Name", foodValue.getFoodName());
        bundle.putString("food_Price", foodValue.getFoodPrice());
        bundle.putString("food_Image", foodValue.getFoodImage());
        bundle.putString("food_Description", foodValue.getFoodDescription());
        bundle.putString("queue_Number", bundleAssignedQueueNumber);

        return bundle;
    }
}
