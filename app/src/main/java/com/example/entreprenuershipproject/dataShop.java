package com.example.entreprenuershipproject;

import androidx.lifecycle.ViewModel;

public class dataShop extends ViewModel {
    public String shopName, shopStatus, shopImage, shopAddress;

    public dataShop() {
    }

    public dataShop(String shopName, String shopStatus, String shopImage, String shopAddress) {
        this.shopName = shopName;
        this.shopStatus = shopStatus;
        this.shopImage = shopImage;
        this.shopAddress = shopAddress;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }


}
