package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.entreprenuershipproject.R;

public class shopMenuFragmentParent extends Fragment {
    String
            bundleShopName,
            bundleQueueNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop_menu_parent, container, false);

        Bundle sendBundle = new Bundle();
        getReceivedBundle();
        setReceivedBundleDataToSendBundle(sendBundle);

        Fragment shopMenuFragmentChild = new shopMenuFragmentChild();
        shopMenuFragmentChild.setArguments(sendBundle);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.shopMenuChanger, shopMenuFragmentChild);
        fragmentTransaction.commit();

        return root;
    }

    private void setReceivedBundleDataToSendBundle(Bundle sendBundle) {
        sendBundle.putString("shop_Name", bundleShopName);
        sendBundle.putString("queue_Number", bundleQueueNumber);
    }

    private void getReceivedBundle() {
        Bundle receivedBundle = getArguments();
        if (receivedBundle != null) {
            bundleShopName = receivedBundle.getString("shop_Name");
            bundleQueueNumber = receivedBundle.getString("queue_Number");
        }
    }
}
