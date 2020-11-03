package com.example.entreprenuershipproject.fragment.tester;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entreprenuershipproject.AdapterMenuFood;
import com.example.entreprenuershipproject.FragmentChangeListener;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.classFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TesterShopMenu extends Fragment implements AdapterMenuFood.OnItemClickListener {

    DatabaseReference shopMenuDatabaseReference;

    RecyclerView menuRecycleView;
    ArrayList<classFood> menuList;
    AdapterMenuFood adapterMenuFood;
    classFood foodClass;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        menuRecycleView = root.findViewById(R.id.menuRecycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        menuRecycleView.setLayoutManager(linearLayoutManager);
        menuRecycleView.setHasFixedSize(true);

        shopMenuDatabaseReference = FirebaseDatabase.getInstance().getReference().child("menu").child("mcd");

        clearAll();
        getMenuDataFromDatabase();
        menuList = new ArrayList<>();
        foodClass = new classFood();

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
        Fragment fr = new TesterShopMenuDetails();

        Bundle bundle1 = getClickItemData(clickItem);

        fr.setArguments(bundle1);

        FragmentChangeListener fc= (FragmentChangeListener) getContext();
        if (fc != null) {
            fc.replaceFragment(fr);
        }

    }

    private Bundle getClickItemData(classFood searchShop1) {
        Bundle bundle = new Bundle();
        bundle.putString("food_Name", searchShop1.getFoodName());
        bundle.putString("food_Price", searchShop1.getFoodPrice());
        bundle.putString("food_Image", searchShop1.getFoodImage());
        bundle.putString("food_Description", searchShop1.getFoodDescription());

        return bundle;
    }

}
