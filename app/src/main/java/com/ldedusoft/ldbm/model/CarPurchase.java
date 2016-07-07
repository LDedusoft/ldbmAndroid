package com.ldedusoft.ldbm.model;

/**
 * 整车销售单
 * Created by wangjianwei on 2016/7/7.
 * {Code：车辆编号；Price：单价；Deposit：订金；Warehouse：仓库；Color：颜色}
 */
public class CarPurchase {
    private String Code;
    private String Price;
    private String Deposit;
    private String Warehouse;
    private String Color;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDeposit() {
        return Deposit;
    }

    public void setDeposit(String deposit) {
        Deposit = deposit;
    }

    public String getWarehouse() {
        return Warehouse;
    }

    public void setWarehouse(String warehouse) {
        Warehouse = warehouse;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
