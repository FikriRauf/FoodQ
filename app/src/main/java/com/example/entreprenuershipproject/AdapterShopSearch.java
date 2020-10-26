package com.example.entreprenuershipproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entreprenuershipproject.fragment.shopDetailsFragment;

import java.util.ArrayList;

public class AdapterShopSearch extends RecyclerView.Adapter<AdapterShopSearch.ViewHolder> {
    ArrayList<searchShop> searching;
    private static final  String TAG = "RecyclerView";
    private Context mContent;
    private ArrayList<searchShop> searchShopList;

    public AdapterShopSearch(Context mContent, ArrayList<searchShop> searchShopList) {
        this.mContent = mContent;
        this.searchShopList = searchShopList;
    }

    @NonNull
    @Override
    public AdapterShopSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_shop, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.shopName.setText(searchShopList.get(position).getShopName());
        holder.shopStatus.setText((searchShopList.get(position).getShopStatus()));
        Glide.with(mContent).load(searchShopList.get(position).getShopImage()).into(holder.shopImage);
        holder.shopCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new shopDetailsFragment();
                FragmentChangeListener fc=(FragmentChangeListener)mContent;
                fc.replaceFragment(fr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchShopList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        TextView shopName, shopStatus;
        LinearLayout shopCardHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shopImage = itemView.findViewById(R.id.displayShopImage);
            shopName = itemView.findViewById(R.id.displayShopName);
            shopStatus = itemView.findViewById(R.id.displayShopStatus);
            shopCardHolder = itemView.findViewById(R.id.shopCardHolder);

        }

    }

//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_shop, parent, false);
//        return new ViewHolder(view);
//    }

//    public AdapterShopSearch(ArrayList<searchShop> searching) {
//        this.searching = searching;
//    }


//    @NonNull
//    @Override
//    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_shop, parent, false);
//        return new myViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
//        holder.shopTitleName.setText(searching.get(position).getShopName());
//        holder.shopStatus.setText(searching.get(position).getShopStatus());
//    }
//
//    @Override
//    public int getItemCount() {
//        return searching.size();
//    }
//
//    class myViewHolder extends RecyclerView.ViewHolder {
//        TextView shopTitleName, shopStatus;
//
//        public myViewHolder(@NonNull View itemView) {
//            super(itemView);
//            shopTitleName = itemView.findViewById(R.id.displayShopName);
//            shopStatus = itemView.findViewById(R.id.displayShopStatus);
//        }
//    }
}
