package com.example.entreprenuershipproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ShopFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ShopFragment extends Fragment {

    ArrayList<searchShop> searching;
    DatabaseReference databaseReference;
    RecyclerView recycleView;
    SearchView searchView;


//    @Override
    public void onCreate(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        recycleView = root.findViewById(R.id.recycleView);
        searchView = root.findViewById(R.id.searchView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shop");

    }

    @Override
    public void onStart() {
        super.onStart();
        if(databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        searching = new ArrayList<>();
                        for(DataSnapshot shops : snapshot.getChildren()) {
                            searching.add(shops.getValue(searchShop.class));
                        }
                        searchShopAdapter shopAdapter = new searchShopAdapter(searching);
                        recycleView.setAdapter(shopAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str) {
        ArrayList<searchShop> shopSearching = new ArrayList<>();
        for(searchShop object : searching) {
            if(object.getShopStatus().toLowerCase().contains(str.toLowerCase())) {
                shopSearching.add(object);
            }
        }
        searchShopAdapter shopAdapter = new searchShopAdapter(shopSearching);
        recycleView.setAdapter(shopAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);



    }
}