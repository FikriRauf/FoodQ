package com.example.entreprenuershipproject;

import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;

public class classRetrieveQueueNumber extends ViewModel {
    public String
            queueNumber,
            queueStatus,
            queuedShopName,
            queueState,
            queueShopImage,
            reservation;

    public classRetrieveQueueNumber() {
    }

    public classRetrieveQueueNumber(String queueNumber, String queueStatus, String queuedShopName, String queueState, String queueShopImage, String reservation) {
        this.queueNumber = queueNumber;
        this.queueStatus = queueStatus;
        this.queuedShopName = queuedShopName;
        this.queueState = queueState;
        this.queueShopImage = queueShopImage;
        this.reservation = reservation;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
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
