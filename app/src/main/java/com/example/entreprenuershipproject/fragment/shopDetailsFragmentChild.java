package com.example.entreprenuershipproject.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.entreprenuershipproject.R;

public class shopDetailsFragmentChild extends Fragment {

    TextView
            shopDetailName,
            shopDetailStatus,
            shopDetailAddress;

    String
            bundleShopName,
            bundleShopStatus,
            bundleShopAddress,
            bundleShopImage;

    ImageView
            shopDetailImage,
            closeDiningInDialog;
    Button
            diningBtn,
            diningInTodayBtn,
            notDiningInTodayBtn;

    Dialog diningInDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_shop_details_2, container, false);


        diningInDialog = new Dialog(requireContext());

        setLayoutViewsToLocalVariables(root);
        getBundleData();
        setBundleDataToLayoutViews();
        diningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiningInDialog();
            }
        });


        return root;
    }

    private void setLayoutViewsToLocalVariables(View root) {
        shopDetailName = root.findViewById(R.id.databaseShopName);
        shopDetailStatus = root.findViewById(R.id.databaseShopStatus);
        shopDetailAddress = root.findViewById(R.id.databaseShopAddress);
        shopDetailImage = root.findViewById(R.id.databaseShopImage);
        diningBtn = root.findViewById(R.id.diningInBtn);
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleShopName = bundle.getString("shop_Name");
            bundleShopStatus = bundle.getString("shop_Status");
            bundleShopAddress = bundle.getString("shop_Address");
            bundleShopImage = bundle.getString("shop_Image");
        }
    }

    private void setBundleDataToLayoutViews() {
        shopDetailName.setText(bundleShopName);
        shopDetailStatus.setText(bundleShopStatus);
        shopDetailAddress.setText(bundleShopAddress);
        Glide.with(this).load(bundleShopImage).into(shopDetailImage);
    }

    private void showDiningInDialog() {
        diningInDialog.setContentView(R.layout.dining_in_dialog);
        closeDiningInDialog = diningInDialog.findViewById(R.id.closeDineInDialog);
        notDiningInTodayBtn = diningInDialog.findViewById(R.id.notDiningInTodayBtn);
        diningInTodayBtn = diningInDialog.findViewById(R.id.diningInTodayBtn);


        closeDiningInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diningInDialog.dismiss();
            }
        });

        notDiningInTodayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        diningInTodayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendBundle = new Bundle();
                sendBundle.putString("shop_Name", bundleShopName);

                Fragment retrieveQueueNumberFragment = new retrieveQueueNumberDatabase();
                retrieveQueueNumberFragment.setArguments(sendBundle);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.shopDetailFragmentChanger, retrieveQueueNumberFragment);
                fragmentTransaction.commit();
                diningInDialog.dismiss();
            }
        });
        diningInDialog.show();

    }
}
