package com.example.entreprenuershipproject.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.entreprenuershipproject.FragmentChangeListener;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.classCart;
import com.example.entreprenuershipproject.fragment.tester.TesterShopMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class shopMenuDetailFragment extends Fragment {
    private static final String TAG = "shopMenuDetailFragment";
    Button
            minusBtn,
            plusBtn,
            addToCartBtn;

    TextView
            foodDetailName,
            foodDetailPrice,
            foodDetailDescription,
            foodDetailQuantityInput,
            backBtn;

    EditText foodDetailSpecialInstructionInput;

    ImageView foodDetailImage;

    String
            bundleFoodName,
            bundleFoodPrice,
            bundleFoodDescription,
            bundleFoodImage,
            bundleAssignedQueueNumber,
            bundleShopName,
            foodInstructionInput,
            foodQuantityInput,
            minusQuantityString = "1";

    CheckBox
            filterOption1,
            filterOption2,
            filterOption3,
            filterOption4;

    DatabaseReference
            baseDatabaseReference,
            addToCartDatabaseReference,
            filterOptionDatabaseReference;

    classCart cartValues;
    ArrayList<String> filterOptionList;
    int counter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop_menu_detail, container, false);
        cartValues = new classCart();
        filterOptionList = new ArrayList<>();
        counter = 1;

        setDatabaseReference();
        getBundleData();
        setLayoutViewsToLocalVariables(root);
        goBack();
        setBundleDataToViews();
        addAndMinusButtonAction();
        sendFoodValueToDatabaseCart();
        getFilterOptions();

        return root;
    }

    private void getFilterOptions() {
        filterOptionDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String databaseFilterShopName = dataSnapshot.getKey();
                    int numberOfFiltersInShop = (int) dataSnapshot.getChildrenCount();
//                    Log.d(TAG, "1st loop: " + databaseFilterShopName);
//                    Log.d(TAG, "child size " + numberOfFiltersInShop);

                    if (databaseFilterShopName != null) {
                        if (databaseFilterShopName.equals(bundleShopName)) {
                            for (int i = 1; i <= numberOfFiltersInShop; i++) {
                                String filterOptionTag = "filterOption" + i;
                                String filterOptionValue = (String) dataSnapshot.child(filterOptionTag).getValue();
                                filterOptionList.add(filterOptionValue);
                                Log.d(TAG, "filter List: " + filterOptionList);
                            }
                        }
                    }
                }
                setDatabaseFilterDataToCheckbox();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDatabaseFilterDataToCheckbox() {
        Log.d(TAG, "filter List: " + filterOptionList);

        CheckBox[] checkBoxes = {filterOption1, filterOption2, filterOption3, filterOption4};

        int i = 0;
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setText(filterOptionList.get(i));
            i++;
            Log.d(TAG, "setDatabaseFilterDataToCheckbox: " + checkBox.getText());
        }
    }

    private void setDatabaseReference() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        addToCartDatabaseReference = baseDatabaseReference.child("cart");
        filterOptionDatabaseReference = baseDatabaseReference.child("filter");
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleFoodName = bundle.getString("food_Name");
            bundleFoodPrice = bundle.getString("food_Price");
            bundleFoodDescription = bundle.getString("food_Description");
            bundleFoodImage = bundle.getString("food_Image");
            bundleAssignedQueueNumber = bundle.getString("queue_Number");
            bundleShopName = bundle.getString("shop_Name");
        } else {
            Log.d(TAG, "getBundleData error: ");
        }
    }

    private void setLayoutViewsToLocalVariables(View root) {
        foodDetailName = root.findViewById(R.id.detailFoodNameTitle);
        foodDetailPrice = root.findViewById(R.id.detailFoodPriceTitle);
        foodDetailDescription = root.findViewById(R.id.detailFoodDescriptionTitle);

        foodDetailSpecialInstructionInput = root.findViewById(R.id.detailFoodSpecialInstructionInput);
        foodDetailQuantityInput = root.findViewById(R.id.detailFoodQuantityInput);

        foodDetailImage = root.findViewById(R.id.detailFoodImage);

        addToCartBtn = root.findViewById(R.id.addToCartBtn);
        plusBtn = root.findViewById(R.id.plusBtn);
        minusBtn = root.findViewById(R.id.minusBtn);
        backBtn = root.findViewById(R.id.backBtn);

        filterOption1 = root.findViewById(R.id.checkBox1);
        filterOption2 = root.findViewById(R.id.checkBox2);
        filterOption3 = root.findViewById(R.id.checkBox3);
        filterOption4 = root.findViewById(R.id.checkBox4);
    }

    private void goBack() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Fragment shopMenuFragmentChild = new shopMenuFragmentChild();

                    Bundle sendShopDetail = new Bundle();
                    sendShopDetail.putString("shop_Name", bundleShopName);
                    sendShopDetail.putString("queue_Number", bundleAssignedQueueNumber);

                    shopMenuFragmentChild.setArguments(sendShopDetail);
                    FragmentChangeListener fc= (FragmentChangeListener) getContext();
                    if (fc != null) {
                        fc.replaceFragment(shopMenuFragmentChild);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onClick: heelllloo ");
                }
            }
        });
    }

    private void setBundleDataToViews() {
        foodDetailName.setText(bundleFoodName);
        String PriceFormat = "RM " + bundleFoodPrice;
        foodDetailPrice.setText(PriceFormat);
        foodDetailDescription.setText(bundleFoodDescription);
        Glide.with(this).load(bundleFoodImage).into(foodDetailImage);
    }

    private void addAndMinusButtonAction() {
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minusQuantityString != null) {
                    int addQuantityInteger2 = Integer.parseInt(minusQuantityString);
                    addQuantityInteger2++;
                    String addQuantityString2 = Integer.toString(addQuantityInteger2);
                    minusQuantityString = addQuantityString2;
                    foodDetailQuantityInput.setText(addQuantityString2);
                } else {
                    int addQuantityInteger = 0;
                    addQuantityInteger++;
                    String addQuantityString = Integer.toString(addQuantityInteger);
                    minusQuantityString = addQuantityString;
                    foodDetailQuantityInput.setText(addQuantityString);
                }
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minusQuantityString2 = foodDetailQuantityInput.getText().toString();
                int minusQuantityInteger = Integer.parseInt(minusQuantityString2);
                minusQuantityInteger--;
                minusQuantityString = Integer.toString(minusQuantityInteger);
                foodDetailQuantityInput.setText(minusQuantityString);
            }
        });
    }

    private void sendFoodValueToDatabaseCart() {
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodInstructionInput = foodDetailSpecialInstructionInput.getText().toString();
                foodQuantityInput = foodDetailQuantityInput.getText().toString();

                setFoodValuesToObject();

                addToCartDatabaseReference.push().setValue(cartValues)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "queue success", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void setFoodValuesToObject() {
        cartValues.setFoodName(bundleFoodName);
        cartValues.setFoodPrice(bundleFoodPrice);
        cartValues.setFoodInstruction(foodInstructionInput);
        cartValues.setFoodQuantity(foodQuantityInput);
        cartValues.setQueueNumber(bundleAssignedQueueNumber);
    }

}
