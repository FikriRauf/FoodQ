package com.example.entreprenuershipproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    ArrayList<classCart> cartList;
    Context mContent;

    public AdapterCart(Context mContent, ArrayList<classCart> cartList) {
        this.mContent = mContent;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public AdapterCart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_cart, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCart.ViewHolder holder, int position) {
        classCart getPosition = cartList.get(position);

        String arrayListFoodName = getPosition.getFoodName();
        String arrayListFoodPrice = getPosition.getFoodPrice();
        String arrayListFoodQuantity = getPosition.getFoodQuantity();

        String foodQuantityFormat = arrayListFoodQuantity + " sets";

        int foodQuantity = Integer.parseInt(arrayListFoodQuantity);
        float foodPrice = Float.parseFloat(arrayListFoodPrice);

        float foodTotalPriceInt = foodPrice * foodQuantity;
        String foodTotalPriceString = Float.toString(foodTotalPriceInt);

        String PriceFormat = "RM " + foodTotalPriceString;

        ArrayList<Float> priceSum = new ArrayList<>();

        holder.cartFoodName.setText(arrayListFoodName);
        holder.cartFoodPrice.setText(PriceFormat);
        holder.cartFoodQuantity.setText(foodQuantityFormat);

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                cartFoodName,
                cartFoodPrice,
                cartFoodQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartFoodName = itemView.findViewById(R.id.cartFoodName);
            cartFoodPrice = itemView.findViewById(R.id.cartFoodPrice);
            cartFoodQuantity = itemView.findViewById(R.id.cartFoodQuantity);
        }
    }

}
