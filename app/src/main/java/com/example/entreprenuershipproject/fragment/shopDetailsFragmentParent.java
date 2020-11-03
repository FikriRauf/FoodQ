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

public class shopDetailsFragmentParent extends Fragment {
    String
            bundleShopName,
            bundleShopStatus,
            bundleShopAddress,
            bundleShopImage;
//    Bundle sendBundle;


    public shopDetailsFragmentParent() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop_details_parent, container, false);

        Bundle sendBundle = new Bundle();

        getReceivedBundleData();
        setReceivedBundleDataToSendBundle(sendBundle);

        Fragment shopDetailsFragmentChild = new shopDetailsFragmentChild();
        shopDetailsFragmentChild.setArguments(sendBundle);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.shopDetailFragmentChanger, shopDetailsFragmentChild);
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

    private void setReceivedBundleDataToSendBundle(Bundle sendBundle) {
        sendBundle.putString("shop_Name", bundleShopName);
        sendBundle.putString("shop_Status", bundleShopStatus);
        sendBundle.putString("shop_Address", bundleShopAddress);
        sendBundle.putString("shop_Image", bundleShopImage);
    }
}
