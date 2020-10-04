package com.example.entreprenuershipproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class searchShopAdapter extends RecyclerView.Adapter<searchShopAdapter.myViewHolder> {
    ArrayList<searchShop> searching;

    public searchShopAdapter(ArrayList<searchShop> searching) {
        this.searching = searching;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.shopTitleName.setText(searching.get(position).getShopName());
        holder.shopStatus.setText(searching.get(position).getShopStatus());
    }

    @Override
    public int getItemCount() {
        return searching.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView shopTitleName, shopStatus;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            shopTitleName = itemView.findViewById(R.id.shopTitle);
            shopStatus = itemView.findViewById(R.id.shopStatus);
        }
    }
}
