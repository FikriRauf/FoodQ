package com.example.entreprenuershipproject.fragment.tester;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.classFilter;
import com.example.entreprenuershipproject.classFood;
import com.example.entreprenuershipproject.classGenerateQueueNumber;
import com.example.entreprenuershipproject.classShop;
import com.example.entreprenuershipproject.fragment.retrieveQueueNumberDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TesterSetValueToDatabase extends Fragment {

    Dialog diningInDialog;
    private static final String TAG = "NewPostActivity";
    private DatabaseReference
            baseDatabaseReference,
            shopDetailDbReference,
            queueNumberDbReference,
            MenuDbReference,
            shopMenuDbReference,
            shopFilterDbReference;

    classShop shopValue;
    classGenerateQueueNumber queueValue;
    classFood foodValue;
    classFilter filterValue;

    String
            shop_Name,
            shop_Status,
            shop_Image,
            shop_Address,
            shop_Phone_Number,
            shop_Operate_Hour,
            queue_Status,
            queue_Number,
            Menu_Food_Name,
            Menu_Food_Price,
            Menu_Food_Image,
            Menu_Food_Description,
            filterOption1,
            filterOption2,
            filterOption3,
            filterOption4;

    Button
            submitShopDetailBtn,
            submitQueueNumberBtn,
            retrieveQueueNumberBtn,
            notDiningInTodayBtn,
            diningInTodayBtn,
            submitShopMenuBtn;


    ImageView closeDiningInDialog;


    public TesterSetValueToDatabase() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tester_2, container, false);

        shopValue = new classShop();
        queueValue = new classGenerateQueueNumber();
        foodValue = new classFood();
        filterValue = new classFilter();
        diningInDialog = new Dialog(requireContext());

        setLayoutViewsToLocalVariables(root);
        setDatabaseReference();

        submitShopDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendShopDetailToDatabase();
            }
        });

        submitQueueNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendQueueDetailToDatabase();
            }
        });

        retrieveQueueNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiningInDialog();
            }
        });

        submitShopMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendShopMenuDetailToDatabase();
                sendShopFilterDetailToDatabase();
            }
        });

        return root;
    }


    private void setLayoutViewsToLocalVariables(View root) {
        submitShopDetailBtn = root.findViewById(R.id.submitShopDetail);
        submitQueueNumberBtn = root.findViewById(R.id.submitQueueNumberDetail);
        retrieveQueueNumberBtn = root.findViewById(R.id.retrieveQueueNumber);
        submitShopMenuBtn = root.findViewById(R.id.submitShopMenu);
    }

    private void setDatabaseReference() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        shopDetailDbReference = baseDatabaseReference.child("shop");
        queueNumberDbReference = baseDatabaseReference.child("queue");
        MenuDbReference = baseDatabaseReference.child("menu");
        shopMenuDbReference = MenuDbReference.child("The Social");
        shopFilterDbReference = baseDatabaseReference.child("filter");
    }

    private void sendShopMenuDetailToDatabase() {
        Menu_Food_Name = "borgar ayam";
        Menu_Food_Price = "4.20";
        Menu_Food_Description = "better cheese with maya";
        Menu_Food_Image = "better something";

        foodValue.setFoodName(Menu_Food_Name);
        foodValue.setFoodPrice(Menu_Food_Price);
        foodValue.setFoodImage(Menu_Food_Image);
        foodValue.setFoodDescription(Menu_Food_Description);

        shopMenuDbReference.push().setValue(foodValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "queue success", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendShopFilterDetailToDatabase() {
        filterOption1 = "No Cheese";
        filterOption2 = "No Lettuce";
        filterOption3 = "No Onion";
        filterOption4 = "No Tomatoe";

        filterValue.setFilterOption1(filterOption1);
        filterValue.setFilterOption2(filterOption2);
        filterValue.setFilterOption3(filterOption3);
        filterValue.setFilterOption4(filterOption4);

        shopFilterDbReference.child("Cool Blog").setValue(filterValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "filter success", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendQueueDetailToDatabase() {
        queue_Number = "3";
        queue_Status = "available";

        queueValue.setQueueNumber(queue_Number);
        queueValue.setQueueStatus(queue_Status);

        queueNumberDbReference.push().setValue(queueValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "queue success", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void sendShopDetailToDatabase() {
        shop_Name = "wendy's";
        shop_Status = "open";
        shop_Image = "null";
        shop_Address = "road is something";
        shop_Phone_Number = "013-123-4356";
        shop_Operate_Hour = "10AM - 5PM";

        shopValue.setShopName(shop_Name);
        shopValue.setShopStatus(shop_Status);
        shopValue.setShopImage(shop_Image);
        shopValue.setShopAddress(shop_Address);
        shopValue.setShopOperateHour(shop_Operate_Hour);
        shopValue.setShopPhoneNumber(shop_Phone_Number);

        shopDetailDbReference.push().setValue(shopValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void showDiningInDialog() {
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
                fragmentTransaction.replace(R.id.fragmentChanger, new retrieveQueueNumberDatabase());
                fragmentTransaction.commit();
                diningInDialog.dismiss();
            }
        });

        diningInDialog.show();

    }

}
