package com.example.entreprenuershipproject;

import androidx.lifecycle.ViewModel;

public class classShop extends ViewModel {
    public String
            shopAddress,
            shopImage,
            shopName,
            shopOperateHour,
            shopPhoneNumber,
            shopStatus;

    public classShop() {
    }

    public classShop(String shopName, String shopStatus, String shopImage, String shopAddress, String shopOperateHour, String shopPhoneNumber) {
        this.shopAddress = shopAddress;
        this.shopImage = shopImage;
        this.shopName = shopName;
        this.shopOperateHour = shopOperateHour;
        this.shopPhoneNumber = shopPhoneNumber;
        this.shopStatus = shopStatus;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopOperateHour() {
        return shopOperateHour;
    }

    public void setShopOperateHour(String shopOperateHour) {
        this.shopOperateHour = shopOperateHour;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }


}
