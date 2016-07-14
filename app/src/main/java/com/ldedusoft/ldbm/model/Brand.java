package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 品牌
 * Created by wangjianwei on 2016/7/14.
 * {"ID":5,"PinPai":"宝马"}
 */
public class Brand implements Serializable {
    private int ID;
    private String PinPai;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPinPai() {
        return PinPai;
    }

    public void setPinPai(String pinPai) {
        PinPai = pinPai;
    }
}
