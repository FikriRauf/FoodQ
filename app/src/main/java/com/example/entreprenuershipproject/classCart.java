package com.example.entreprenuershipproject;

import androidx.lifecycle.ViewModel;

public class classCart extends ViewModel {
    public String
            foodName,
            foodPrice,
            foodQuantity,
            foodInstruction,
            queueNumber;

    public classCart() {
    }

    public classCart(String foodName, String foodPrice, String foodQuantity, String foodInstruction, String queueNumber) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodQuantity = foodQuantity;
        this.foodInstruction = foodInstruction;
        this.queueNumber = queueNumber;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getFoodInstruction() {
        return foodInstruction;
    }

    public void setFoodInstruction(String foodInstruction) {
        this.foodInstruction = foodInstruction;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }
}
