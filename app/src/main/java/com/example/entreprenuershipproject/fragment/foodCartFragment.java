package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entreprenuershipproject.AdapterCart;
import com.example.entreprenuershipproject.FragmentChangeListener;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.classCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class foodCartFragment extends Fragment {
    final String TAG = "foodCartFragment";
    ArrayList<LinkedHashMap<String,String>> shopMenuWithNameAndImage;
    ArrayList<classCart> cartValue;
    AdapterCart adapterCart;
    RecyclerView recycleView;
    String
            bundleQueueNumber,
            shopName,
            foodInstruction,
            foodName,
            foodPrice,
            foodQuantity,
            cartQueueNumber,
            databaseMenuName,
            databaseMenuImage;

    TextView
            shopNameTitle,
            queueNumberValue,
            backBtn;


    DatabaseReference
            baseDatabaseReference,
            queueDatabaseReference,
            cartDatabaseReference,
            menuDatabaseReference,
            shopMenuDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_food_cart, container, false);

        setLayoutViewsToLocalVariables(root);

        shopMenuWithNameAndImage = new ArrayList<>();
        cartValue = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setHasFixedSize(true);

        goBack();
        getBundleData();
        setDatabaseReference();
        getShopName();
        getCartValues();




        return root;
    }
    private void goBack() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Fragment shopMenuFragmentChild = new waitingQueueFragmentChild();
                    FragmentChangeListener fc= (FragmentChangeListener) getContext();
                    if (fc != null) {
                        fc.replaceFragment(shopMenuFragmentChild);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "goBack: heelllloo ");
                }
            }
        });
    }
    private void setLayoutViewsToLocalVariables(View root) {
        recycleView = root.findViewById(R.id.cartFoodRecycler);
        queueNumberValue = root.findViewById(R.id.queueNumberValue);
        shopNameTitle = root.findViewById(R.id.shopNameTitle);
        backBtn = root.findViewById(R.id.backBtn);
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleQueueNumber = bundle.getString("queue_Number");
        }
        Log.d(TAG, "getBundleData " + bundleQueueNumber);
    }

    private void setDatabaseReference() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        cartDatabaseReference = baseDatabaseReference.child("cart");
        queueDatabaseReference = baseDatabaseReference.child("queue");
        menuDatabaseReference = baseDatabaseReference.child("menu");
    }

    private void getShopName() {
        queueDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String queueNumber = dataSnapshot.child("queueNumber").getValue(String.class);
                    if (queueNumber != null) {
                        Log.d(TAG, "getShopName: queueNumber" + queueNumber);
                        if (queueNumber.equals(bundleQueueNumber)) {
                            Log.d(TAG, "getShopName: " + queueNumber);
                            String queueShopName = dataSnapshot.child("shopName").getValue(String.class);
                            setPageDetail(queueShopName);
                            getMenuImage(queueShopName);
                        } else {
                            Log.d(TAG, "onDataChange: error" + queueNumber + " " + bundleQueueNumber);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setPageDetail(String queueShopName) {
        shopNameTitle.setText(queueShopName);
        String queueNumberFormat = "#" + bundleQueueNumber;
        queueNumberValue.setText(queueNumberFormat);
    }

    private void getMenuImage(String queueShopName) {
        shopMenuDatabaseReference = menuDatabaseReference.child(queueShopName);
        shopMenuDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    databaseMenuName = dataSnapshot.child("foodName").getValue(String.class);
                    databaseMenuImage = dataSnapshot.child("foodImage").getValue(String.class);

//                    Log.d(TAG, "getMenuImage with MenuName \n"
//                            + databaseMenuName + "\n"
//                            + databaseMenuImage + "\n");

                    LinkedHashMap<String, String> test = new LinkedHashMap<>();
                    test.put(databaseMenuName, databaseMenuImage);
                    shopMenuWithNameAndImage.add(test);

//                    Log.d(TAG, "getMenuImage " + shopMenuWithNameAndImage);
//                    for(Map<String, String> map : shopMenuWithNameAndImage){
//                        for(String key : map.keySet()){
//                            Log.d(TAG, "getCartValue map" + "key: " + key + " value " + map.get(key));
//                        }
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCartValues() {
        cartDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    cartQueueNumber = dataSnapshot.child("queueNumber").getValue(String.class);

                    if (cartQueueNumber != null) {
                        if (cartQueueNumber.equals(bundleQueueNumber)) {
                            classCart cartClass = new classCart();

                            bindDatabaseDataToObject(dataSnapshot, cartClass);
                            bindObjectToArrayList(cartClass);
//                            for(Map<String, String> map : shopMenuWithNameAndImage){
//                                for(String key : map.keySet()){
//                                    Log.d(TAG, "getCartValue map" + "key: " + key + " value " + map.get(key));
//                                    if (key.equals(foodName)) {
//                                        Log.d(TAG, "getCartValue map" + map.get(key));
//                                    } else {
//                                        Log.d(TAG, "getCartValue map error: ");
//                                    }
//                                }
//                            }

//                            Log.d("foodCartFragment", "\ngetCartValue "
//                                    + foodName + "\n"
//                                    + cartQueueNumber + "\n"
//                                    + foodPrice + "\n"
//                                    + foodQuantity + "\n");
                        }
                    }
                }
                bindArrayListToAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if (cartValue != null) {
            cartValue.clear();
            if (adapterCart != null) {
                adapterCart.notifyDataSetChanged();
            }
        }
        cartValue = new ArrayList<>();
    }

    private void bindDatabaseDataToObject(DataSnapshot dataSnapshot, classCart cartClass) {
        foodName = dataSnapshot.child("foodName").getValue(String.class);
        foodPrice = dataSnapshot.child("foodPrice").getValue(String.class);
        foodQuantity = dataSnapshot.child("foodQuantity").getValue(String.class);

        cartClass.setFoodName(foodName);
        cartClass.setFoodPrice(foodPrice);
        cartClass.setFoodQuantity(foodQuantity);
    }

    private void bindObjectToArrayList(classCart cartClass) {
        cartValue.add(cartClass);

    }

    private void bindArrayListToAdapter() {
        adapterCart = new AdapterCart(getContext(), cartValue);
        recycleView.setAdapter(adapterCart);
    }
}
