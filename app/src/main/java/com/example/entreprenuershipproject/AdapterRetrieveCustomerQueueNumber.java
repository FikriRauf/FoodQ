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

public class AdapterRetrieveCustomerQueueNumber extends RecyclerView.Adapter<AdapterRetrieveCustomerQueueNumber.ViewHolder> {

    private Context mContent;
    private ArrayList<classRetrieveQueueNumber> queue;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }


    public AdapterRetrieveCustomerQueueNumber(Context mContent, ArrayList<classRetrieveQueueNumber> queueNumber) {
        this.mContent = mContent;
        this.queue = queueNumber;

    }

    @NonNull
    @Override
    public AdapterRetrieveCustomerQueueNumber.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_queue, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        classRetrieveQueueNumber getPosition = queue.get(position);

        String arrayListQueueNumber = getPosition.getQueueNumber();
        String arrayListQueueShopName = getPosition.getQueuedShopName();
        String arrayListQueueState = getPosition.getQueueState();
        String arrayListQueueShopImage = getPosition.getQueueShopImage();

        holder.displayQueueNumber.setText(arrayListQueueNumber);
        holder.displayQueueShopName.setText(arrayListQueueShopName);
        holder.displayQueueState.setText(arrayListQueueState);
        Glide.with(mContent).load(arrayListQueueShopImage).into(holder.displayQueueShopImage);
    }

    @Override
    public int getItemCount() {
        return queue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayQueueShopName, displayQueueNumber, displayQueueState;
        ImageView displayQueueShopImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            displayQueueShopName = itemView.findViewById(R.id.displayQueueShopName);
            displayQueueNumber = itemView.findViewById(R.id.displayQueueNumber);
            displayQueueState = itemView.findViewById(R.id.displayQueueState);
            displayQueueShopImage = itemView.findViewById(R.id.displayQueueShopImage);

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
