package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.entreprenuershipproject.R;

public class calendarFragment extends Fragment {
    String bundleShopName;
    Button proceedBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        setLayoutViewsToLocalVariables(root);
        getBundleData();

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendBundle = new Bundle();
                sendBundle.putString("shop_Name", bundleShopName);
                sendBundle.putString("booked", "Booked");

                Fragment retrieveQueueNumberFragment = new retrieveQueueNumberDatabase();
                retrieveQueueNumberFragment.setArguments(sendBundle);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.shopDetailFragmentChanger, retrieveQueueNumberFragment);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    private void setLayoutViewsToLocalVariables(View root) {
        proceedBtn = root.findViewById(R.id.proceedBtn);
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleShopName = bundle.getString("shop_Name");
        }
    }
}
