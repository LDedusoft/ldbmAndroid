package com.ldedusoft.ldbm.model;

/**
 * 车辆信息
 * Created by wangjianwei on 2016/6/29.
 */
//{CarCode：车牌号；color：颜色；brand：品牌；Name：客户名称；LinkMan：联系人；Telephone：电话；Mobilephone：手机}
public class CarCode {
    private String carCode;
    private String color;
    private String brand;
    private String name;
    private String linkMan;
    private String Telephone;
    private String Mobilephone;

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getMobilephone() {
        return Mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        Mobilephone = mobilephone;
    }
}
