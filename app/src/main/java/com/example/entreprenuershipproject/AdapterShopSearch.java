package com.example.entreprenuershipproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterShopSearch extends RecyclerView.Adapter<AdapterShopSearch.ViewHolder> {
    ArrayList<searchShop> searching;
    private static final  String TAG = "RecyclerView";
    private Context mContent;
    private ArrayList<searchShop> searchShopList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }


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
        searchShop getPosition = searchShopList.get(position);
//        Log.d(TAG, "onBindViewHolder: " + getPosition);

        String arrayListShopName = getPosition.getShopName();
        String arrayListShopStatus = getPosition.getShopStatus();
        String arrayListShopImage = getPosition.getShopImage();
        String arrayListShopAddress = getPosition.getShopAddress();

//        Log.d(TAG, "onBindViewHolder: " + arrayListShopAddress);

        holder.shopName.setText(arrayListShopName);
        holder.shopStatus.setText(arrayListShopStatus);
        Glide.with(mContent).load(arrayListShopImage).into(holder.shopImage);

    }

    @Override
    public int getItemCount() {
        return searchShopList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        TextView shopName, shopStatus;
        LinearLayout shopCardHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shopImage = itemView.findViewById(R.id.displayShopImage);
            shopName = itemView.findViewById(R.id.displayShopName);
            shopStatus = itemView.findViewById(R.id.displayShopStatus);
            shopCardHolder = itemView.findViewById(R.id.shopCardHolder);

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
