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
import com.example.entreprenuershipproject.classShop;
import com.example.entreprenuershipproject.retrieveQueueNumberDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shopDetailsFragment extends Fragment {

    TextView
            shopName,
            shopStatus,
            shopAddress;

    String
            bundleShopName,
            bundleShopStatus,
            bundleShopAddress,
            bundleShopImage;

    ImageView
            shopImage,
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
        View root = inflater.inflate(R.layout.fragment_shop_details, container, false);

        shopName = root.findViewById(R.id.databaseShopName);
        shopStatus = root.findViewById(R.id.databaseShopStatus);
        shopAddress = root.findViewById(R.id.databaseShopAddress);
        shopImage = root.findViewById(R.id.databaseShopImage);
        diningBtn = root.findViewById(R.id.diningInBtn);

        diningInDialog = new Dialog(requireContext());

        diningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiningInDialog();
            }
        });

        getBundleData();
        setBundleDataToViews();

        return root;
    }

    private void showDiningInDialog() {
        diningInDialog.setContentView(R.layout.dining_in_dialog);
        closeDiningInDialog = (ImageView) diningInDialog.findViewById(R.id.closeDineInDialog);
        notDiningInTodayBtn = (Button) diningInDialog.findViewById(R.id.notDiningInTodayBtn);
        diningInTodayBtn = (Button) diningInDialog.findViewById(R.id.diningInTodayBtn);


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
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.shopFragmentChanger, new retrieveQueueNumberDatabase());
                fragmentTransaction.commit();
                diningInDialog.dismiss();
            }
        });

        diningInDialog.show();

    }

    private void setBundleDataToViews() {
        shopName.setText(bundleShopName);
        shopStatus.setText(bundleShopStatus);
        shopAddress.setText(bundleShopAddress);
        Glide.with(getContext()).load(bundleShopImage).into(shopImage);
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
}
