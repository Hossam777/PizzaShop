package com.example.cvproject1;


public class Reset {

    String locationAddress;
    String subtotalMoney;
    String phone;
    String barCode;


    public Reset(String locationAddress, String subtotalMoney, String phone, String barCode) {
        this.locationAddress = locationAddress;
        this.subtotalMoney = subtotalMoney;
        this.phone = phone;
        this.barCode = barCode;
    }

    public Reset(){}

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getSubtotalMoney() {
        return subtotalMoney;
    }

    public void setSubtotalMoney(String subtotalMoney) {
        this.subtotalMoney = subtotalMoney;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
