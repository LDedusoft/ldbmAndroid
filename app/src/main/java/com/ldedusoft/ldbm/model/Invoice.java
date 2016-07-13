package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 发票信息
 * Created by wangjianwei on 2016/7/9.
 * {ID：id；FaPiao：名称；ShuiLv：税率}
 */
public class Invoice implements Serializable{
    private int ID;
    private String FaPiao;
    private String ShuiLv;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFaPiao() {
        return FaPiao;
    }

    public void setFaPiao(String faPiao) {
        FaPiao = faPiao;
    }

    public String getShuiLv() {
        return ShuiLv;
    }

    public void setShuiLv(String shuiLv) {
        ShuiLv = shuiLv;
    }
}
