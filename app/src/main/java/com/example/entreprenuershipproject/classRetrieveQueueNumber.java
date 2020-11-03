package com.example.entreprenuershipproject;

import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;

public class classRetrieveQueueNumber extends ViewModel {
    public String
            queueNumber,
            queueStatus,
            queuedShopName,
            queueState,
            queueShopImage;

    public classRetrieveQueueNumber() {
    }

    public classRetrieveQueueNumber(String queueNumber, String queueStatus, String queuedShopName, String queueState) {
        this.queueNumber = queueNumber;
        this.queueStatus = queueStatus;
        this.queuedShopName = queuedShopName;
        this.queueState = queueState;
    }

    public String getQueueShopImage() {
        return queueShopImage;
    }

    public void setQueueShopImage(String queueShopImage) {
        this.queueShopImage = queueShopImage;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(String queueStatus) {
        this.queueStatus = queueStatus;
    }

    public String getQueuedShopName() {
        return queuedShopName;
    }

    public void setQueuedShopName(String queuedShopName) {
        this.queuedShopName = queuedShopName;
    }

    public String getQueueState() {
        return queueState;
    }

    public void setQueueState(String queueState) {
        this.queueState = queueState;
    }
}
