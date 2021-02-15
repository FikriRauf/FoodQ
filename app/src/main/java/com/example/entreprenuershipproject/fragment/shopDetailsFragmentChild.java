package com.example.entreprenuershipproject.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.entreprenuershipproject.FragmentChangeListener;
import com.example.entreprenuershipproject.R;

public class shopDetailsFragmentChild extends Fragment {
    private static final String TAG = "shopDetailsFragmentChil";
    TextView
            shopDetailName,
            shopDetailStatus,
            shopDetailAddress,
            shopDetailOperatingHours,
            shopDetailPhoneNumber,
            backBtn;

    String
            bundleShopName,
            bundleShopStatus,
            bundleShopAddress,
            bundleShopImage,
            bundleShopOperatingHour,
            bundleShopPhoneNumber;

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
        View root = inflater.inflate(R.layout.fragment_shop_details_child, container, false);


        diningInDialog = new Dialog(requireContext());

        setLayoutViewsToLocalVariables(root);
        getBundleData();
        setBundleDataToLayoutViews();
        goBack();
        diningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiningInDialog();
            }
        });


        return root;
    }
    private void goBack() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Fragment shopMenuFragmentParent = new shopFragmentParent();

                    FragmentChangeListener fc= (FragmentChangeListener) getContext();
                    if (fc != null) {
                        fc.replaceFragment(shopMenuFragmentParent);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onClick: heelllloo ");
                }
            }
        });
    }

    private void setLayoutViewsToLocalVariables(View root) {
        shopDetailName = root.findViewById(R.id.databaseShopName);
        shopDetailStatus = root.findViewById(R.id.databaseShopStatus);
        shopDetailAddress = root.findViewById(R.id.databaseShopAddress);
        shopDetailImage = root.findViewById(R.id.databaseShopImage);
        shopDetailOperatingHours = root.findViewById(R.id.databaseShopOperatingHours);
        shopDetailPhoneNumber = root.findViewById(R.id.databaseShopPhoneNumber);

        backBtn = root.findViewById(R.id.backBtn);
        diningBtn = root.findViewById(R.id.diningInBtn);
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleShopName = bundle.getString("shop_Name");
            bundleShopStatus = bundle.getString("shop_Status");
            bundleShopAddress = bundle.getString("shop_Address");
            bundleShopImage = bundle.getString("shop_Image");
            bundleShopOperatingHour = bundle.getString("shop_Operating_Hours");
            bundleShopPhoneNumber = bundle.getString("shop_Phone_Number");
        }
    }

    private void setBundleDataToLayoutViews() {
        shopDetailName.setText(bundleShopName);
        shopDetailStatus.setText(bundleShopStatus);
        shopDetailAddress.setText(bundleShopAddress);
        shopDetailOperatingHours.setText(bundleShopOperatingHour);
        shopDetailPhoneNumber.setText(bundleShopPhoneNumber);
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
                Bundle sendBundle = new Bundle();
                sendBundle.putString("shop_Name", bundleShopName);

                Fragment calendarFragment = new calendarFragment();
                calendarFragment.setArguments(sendBundle);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.shopDetailFragmentChanger, calendarFragment);
                fragmentTransaction.commit();
                diningInDialog.dismiss();
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
