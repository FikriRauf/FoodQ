package com.example.entreprenuershipproject;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class dataQueue extends ViewModel {
    public int queueNumber;
//    public Integer queueNumber;
    public String queueStatus;

    public dataQueue() {
    }

//    public dataQueue(Integer queueNumber, String queueStatus) {
//        this.queueNumber = queueNumber;
//        this.queueStatus = queueStatus;
//    }
//
//    public Integer getQueueNumber() {
//        return queueNumber;
//    }
//
//    public void setQueueNumber(Integer queueNumber) {
//        this.queueNumber = queueNumber;
//    }

    public dataQueue(int queueNumber, String queueStatus) {
        this.queueNumber = queueNumber;
        this.queueStatus = queueStatus;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(String queueStatus) {
        this.queueStatus = queueStatus;
    }
}
