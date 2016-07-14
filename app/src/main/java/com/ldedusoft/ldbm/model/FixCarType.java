package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 配件车型
 * Created by wangjianwei on 2016/7/14.
 * {"ID":5,"XingHao":"奥迪","BianMa":"CX00001"}
 */
public class FixCarType implements Serializable {
    private int ID;
    private String XingHao;
    private String BianMa;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getXingHao() {
        return XingHao;
    }

    public void setXingHao(String xingHao) {
        XingHao = xingHao;
    }

    public String getBianMa() {
        return BianMa;
    }

    public void setBianMa(String bianMa) {
        BianMa = bianMa;
    }
}
