package com.example.entreprenuershipproject;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class AdapterRetrieveCustomerQueueNumber extends RecyclerView.Adapter<AdapterRetrieveCustomerQueueNumber.ViewHolder> {
    private static final String TAG = "AdapterRetrieveCustomerQueueNumber";
    private Context mContent;

    private OnItemClickListener mListener;

    private ArrayList<classRetrieveQueueNumber> queue;
    ArrayList<LinkedHashMap<String,String>> list = new ArrayList<>();

    String
            queueState,
            queueID,
            shopName;

    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMilliSeconds;
    DatabaseReference queueDatabaseReference, firstQueueDatabaseReference;

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
        String arrayListQueueReservation = getPosition.getReservation();

        String queueNumberFormat = "#" + arrayListQueueNumber;
        holder.displayQueueNumber.setText(queueNumberFormat);
        holder.displayQueueShopName.setText(arrayListQueueShopName);

        holder.displayQueueState.setText(arrayListQueueState);


        Glide.with(mContent).load(arrayListQueueShopImage).into(holder.displayQueueShopImage);

        if (arrayListQueueReservation != null) {
            holder.queueTimer.setText(arrayListQueueReservation);
        } else {
            setTimerToShopPresetTimeDuration(arrayListQueueShopName);
            timerStarter(holder, arrayListQueueState, arrayListQueueShopName);
        }

        getQueueIdFromDatabase(arrayListQueueNumber);

    }

    private void setTimerToShopPresetTimeDuration(String arrayListQueueShopName) {
        int second_05 = 5000;
        int second_10 = 10000;
        int second_15 = 15000;
        int second_30 = 30000;
        int minute_1 = 60000;
        int minute_2 = 120000;
        int minute_15 = 900000;
        int minute_30 = 180000000;
        int minute_60 = 3600000;

        switch (arrayListQueueShopName) {
            case "Cool Blog":
                mTimeLeftInMilliSeconds = minute_2;
                break;

            case "Thyme Out":
                mTimeLeftInMilliSeconds = second_30;
                break;

            case "Sushi ZenS":
                mTimeLeftInMilliSeconds = minute_15;
                break;
            default:
                mTimeLeftInMilliSeconds = minute_30;
        }
    }

    private void timerStarter(ViewHolder holder, String arrayListQueueState, String arrayListQueueShopName) {
        if (arrayListQueueState.equals("Ready")) {
            holder.queueTimer.setText("00:00");
        } else {
            startTime(holder, arrayListQueueShopName);
        }
    }

    private void startTime(final ViewHolder holder, final String arrayListQueueShopName) {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                 mTimeLeftInMilliSeconds = millisUntilFinished;
                updateCountDownText(holder);
            }

            @Override
            public void onFinish() {
                for(Map<String, String> map : list){
//                    Log.d(TAG, "startTime: " + map);
                    for(String key : map.keySet()){
                        if (key.equals(arrayListQueueShopName)) {
                            String queueIdValue = map.get(key);
                            firstQueueDatabaseReference = queueDatabaseReference.child(queueIdValue).child("queueState");
                            firstQueueDatabaseReference.setValue("Ready");
                        }
//                        System.out.println("key: " + key + " value " + map.get(key));
                    }
                }
            }
        }.start();
    }

    private void updateCountDownText(ViewHolder holder) {
        int minutes = (int) (mTimeLeftInMilliSeconds / 1000) / 60;
        int seconds = (int) (mTimeLeftInMilliSeconds / 1000) % 60;

        String timeLeftFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        holder.queueTimer.setText(timeLeftFormat);
    }

    private void getQueueIdFromDatabase(final String arrayListQueueNumber) {
        queueDatabaseReference = FirebaseDatabase.getInstance().getReference().child("queue");
        queueDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String queueNumber = (String) dataSnapshot.child("queueNumber").getValue();
                    getShopAndQueueDetailsOfUserQueueNumbers(queueNumber, dataSnapshot, arrayListQueueNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getShopAndQueueDetailsOfUserQueueNumbers(String queueNumber, DataSnapshot dataSnapshot, String arrayListQueueNumber) {
        if (queueNumber != null) {
            if (queueNumber.equals(arrayListQueueNumber)) {
                setDatabaseDataToLocalVariables(dataSnapshot);
                setDatabaseLocalVariablesToShopQueueArrayList();
            }
        }
    }

    private void setDatabaseDataToLocalVariables(DataSnapshot dataSnapshot) {
        queueState = (String) dataSnapshot.child("queueState").getValue();
        queueID = dataSnapshot.getKey();
        shopName = (String) dataSnapshot.child("shopName").getValue();
    }

    private void setDatabaseLocalVariablesToShopQueueArrayList() {
        LinkedHashMap<String, String> shopNameQueueId = new LinkedHashMap<String, String>();
        shopNameQueueId.put(shopName, queueID);
        list.add(shopNameQueueId);
//        Log.d(TAG, "getQueueIdFromDatabase: " + list);
    }

    @Override
    public int getItemCount() {
        return queue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                displayQueueShopName,
                displayQueueNumber,
                displayQueueState,
                queueTimer;
        ImageView displayQueueShopImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            displayQueueShopName = itemView.findViewById(R.id.displayQueueShopName);
            displayQueueNumber = itemView.findViewById(R.id.displayQueueNumber);
            displayQueueState = itemView.findViewById(R.id.displayQueueState);
            displayQueueShopImage = itemView.findViewById(R.id.displayQueueShopImage);
            queueTimer = itemView.findViewById(R.id.queueTimer);

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