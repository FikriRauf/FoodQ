package com.example.entreprenuershipproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAssignCustomerQueueNumber extends RecyclerView.Adapter<AdapterAssignCustomerQueueNumber.ViewHolder> {


    private Context mContent;
    private ArrayList<classRetrieveQueueNumber> retrieveQueueNumbersList;

    public AdapterAssignCustomerQueueNumber(Context mContent, ArrayList<classRetrieveQueueNumber> retrieveQueueNumbersList) {
        this.mContent = mContent;
        this.retrieveQueueNumbersList = retrieveQueueNumbersList;
    }


    public AdapterAssignCustomerQueueNumber.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_number_holder, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAssignCustomerQueueNumber.ViewHolder holder, int position) {
        holder.displayQueueNumber.setText(retrieveQueueNumbersList.get(position).getQueueNumber());

    }

    @Override
    public int getItemCount() {
        return retrieveQueueNumbersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView displayQueueNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            displayQueueNumber = itemView.findViewById(R.id.displayQueueNumberPage);
        }
    }

}
