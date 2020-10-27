package com.example.entreprenuershipproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private String
            arrayListShopName,
            arrayListShopStatus,
            arrayListShopImage,
            arrayListShopAddress;

//    private OnItemListener onItemListener;

//    public AdapterShopSearch(Context mContent, ArrayList<searchShop> searchShopList, OnItemListener onItemListener) {
    public AdapterShopSearch(Context mContent, ArrayList<searchShop> searchShopList) {
        this.mContent = mContent;
        this.searchShopList = searchShopList;
//        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public AdapterShopSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_shop, parent, false);

        return new ViewHolder(view);
//        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        arrayListShopName = searchShopList.get(position).getShopName();
        arrayListShopStatus = searchShopList.get(position).getShopStatus();
        arrayListShopImage = searchShopList.get(position).getShopImage();
        arrayListShopAddress = searchShopList.get(position).getShopAddress();

        holder.shopName.setText(arrayListShopName);
        holder.shopStatus.setText(arrayListShopStatus);
        Glide.with(mContent).load(arrayListShopImage).into(holder.shopImage);

        holder.shopCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new shopDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("shop_Name", arrayListShopName);
                bundle.putString("shop_Status", arrayListShopStatus);
                bundle.putString("shop_Image", arrayListShopImage);
                bundle.putString("shop_Address", arrayListShopAddress);

                fr.setArguments(bundle);

                FragmentChangeListener fc=(FragmentChangeListener)mContent;
                fc.replaceFragment(fr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchShopList.size();
    }

//    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        TextView shopName, shopStatus;
        LinearLayout shopCardHolder;
        OnItemListener onItemListener;

//        public ViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shopImage = itemView.findViewById(R.id.displayShopImage);
            shopName = itemView.findViewById(R.id.displayShopName);
            shopStatus = itemView.findViewById(R.id.displayShopStatus);
            shopCardHolder = itemView.findViewById(R.id.shopCardHolder);
//            this.onItemListener = onItemListener;

//            itemView.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View v) {
//            onItemListener.onItemClick(getAdapterPosition());
//        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
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
