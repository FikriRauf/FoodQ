package com.example.entreprenuershipproject.fragment.tester;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.entreprenuershipproject.R;
import com.example.entreprenuershipproject.classCart;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TesterShopMenuDetails extends Fragment {

    private static final String TAG = "TesterShopMenuDetail";
    Button
            minusBtn,
            plusBtn,
            addToCartBtn;

    TextView
            foodDetailName,
            foodDetailPrice,
            foodDetailDescription;

    EditText
            foodDetailSpecialInstructionInput,
            foodDetailQuantityInput;

    ImageView foodDetailImage;

    String
            bundleFoodName,
            bundleFoodPrice,
            bundleFoodDescription,
            bundleFoodImage,
            bundleAssignedQueueNumber,
            foodInstructionInput,
            foodQuantityInput;

    DatabaseReference
            baseDatabaseReference,
            addToCartDatabaseReference;

    classCart cartValues;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop_menu_detail, container, false);
        cartValues = new classCart();

        setDatabaseReference();
        getBundleData();
        setLayoutViewsToLocalVariables(root);
        setBundleDataToViews();
        sendFoodValueToDatabaseCart();

        return root;
    }

    private void setDatabaseReference() {
        baseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        addToCartDatabaseReference = baseDatabaseReference.child("cart");
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleFoodName = bundle.getString("food_Name");
            bundleFoodPrice = bundle.getString("food_Price");
            bundleFoodDescription = bundle.getString("food_Description");
            bundleFoodImage = bundle.getString("food_Image");
            bundleAssignedQueueNumber = bundle.getString("queue_Number");
        } else {
            Log.d(TAG, "hello error: ");
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
    }

    private void setBundleDataToViews() {
        foodDetailName.setText(bundleFoodName);
        foodDetailPrice.setText(bundleFoodPrice);
        foodDetailDescription.setText(bundleFoodDescription);
        Glide.with(this).load(bundleFoodImage).into(foodDetailImage);

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