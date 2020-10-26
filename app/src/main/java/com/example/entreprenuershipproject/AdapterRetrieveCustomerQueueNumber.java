package com.example.entreprenuershipproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRetrieveCustomerQueueNumber extends RecyclerView.Adapter<AdapterRetrieveCustomerQueueNumber.ViewHolder> {

    private Context mContent;
    private ArrayList<classRetrieveQueueNumber> queue;

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
        holder.displayQueueNumber.setText(queue.get(position).getQueueNumber());
        holder.displayQueueShopName.setText(queue.get(position).getQueuedShopName());
        holder.displayQueueState.setText(queue.get(position).getQueueState());

    }

    @Override
    public int getItemCount() {
        return queue.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayQueueShopName, displayQueueNumber, displayQueueState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            displayQueueShopName = itemView.findViewById(R.id.displayQueueShopName);
            displayQueueNumber = itemView.findViewById(R.id.displayQueueNumber);
            displayQueueState = itemView.findViewById(R.id.displayQueueState);

        }
    }
}
