package com.example.entreprenuershipproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterMenuFood extends RecyclerView.Adapter<AdapterMenuFood.ViewHolder> {
    ArrayList<classFood> foodClass;
    Context mContent;
    private AdapterMenuFood.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener (AdapterMenuFood.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public AdapterMenuFood(Context mContent, ArrayList<classFood> foodClass) {
        this.mContent = mContent;
        this.foodClass = foodClass;
    }


    @NonNull
    @Override
    public AdapterMenuFood.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_menu, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMenuFood.ViewHolder holder, int position) {
        classFood getFoodPosition = foodClass.get(position);

        String retrieveFoodName = getFoodPosition.getFoodName();
        String retrieveFoodPrice = getFoodPosition.getFoodPrice();
        String retrieveFoodImage = getFoodPosition.getFoodImage();

        String foodPriceFormat = "RM" + retrieveFoodPrice;
        holder.displayFoodName.setText(retrieveFoodName);
        holder.displayFoodPrice.setText(foodPriceFormat);
        Glide.with(mContent).load(retrieveFoodImage).into(holder.displayFoodImage);

    }

    @Override
    public int getItemCount() {
        return foodClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView displayFoodImage;
        TextView
                displayFoodName,
                displayFoodPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            displayFoodImage = itemView.findViewById(R.id.displayFoodImage);
            displayFoodName = itemView.findViewById(R.id.displayFoodName);
            displayFoodPrice = itemView.findViewById(R.id.displayFoodPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
