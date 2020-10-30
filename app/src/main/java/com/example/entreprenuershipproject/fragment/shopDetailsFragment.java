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
import com.example.entreprenuershipproject.searchShop;

public class shopDetailsFragment extends Fragment {
    String
            bundleShopName,
            bundleShopStatus,
            bundleShopAddress,
            bundleShopImage;
    Bundle bundleAssign;


    public shopDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop_details, container, false);

        bundleAssign = new Bundle();

        getReceivedBundleData();
        setReceivedBundleDataToSendBundleData();

        Fragment shopDetailsFragment2 = new shopDetailsFragment2();
        shopDetailsFragment2.setArguments(bundleAssign);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.shopDetailFragmentChanger, shopDetailsFragment2);
        fragmentTransaction.commit();

        return root;
    }

    private void getReceivedBundleData() {
        Bundle bundleReceived = getArguments();
        if (bundleReceived != null) {
            bundleShopName = bundleReceived.getString("shop_Name");
            bundleShopStatus = bundleReceived.getString("shop_Status");
            bundleShopAddress = bundleReceived.getString("shop_Address");
            bundleShopImage = bundleReceived.getString("shop_Image");
        }
    }

    private void setReceivedBundleDataToSendBundleData() {
        bundleAssign.putString("shop_Name", bundleShopName);
        bundleAssign.putString("shop_Status", bundleShopStatus);
        bundleAssign.putString("shop_Address", bundleShopAddress);
        bundleAssign.putString("shop_Image", bundleShopImage);

    }
}
