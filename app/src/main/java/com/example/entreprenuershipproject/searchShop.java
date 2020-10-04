package com.example.entreprenuershipproject;

public class searchShop {
    private String shopImage;
    private String shopName;
    private String shopStatus;

    public searchShop() {
    }

    public searchShop(String shopImage, String shopName, String shopStatus) {
        this.shopImage = shopImage;
        this.shopName = shopName;
        this.shopStatus = shopStatus;
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

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }
}