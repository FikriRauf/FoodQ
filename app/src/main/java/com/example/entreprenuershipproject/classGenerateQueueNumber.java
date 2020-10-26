package com.example.entreprenuershipproject;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class classGenerateQueueNumber extends ViewModel {
    public String queueNumber;
    public String queueStatus;

    public classGenerateQueueNumber() {
    }

    public classGenerateQueueNumber(String queueNumber, String queueStatus) {
        this.queueNumber = queueNumber;
        this.queueStatus = queueStatus;
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

}
