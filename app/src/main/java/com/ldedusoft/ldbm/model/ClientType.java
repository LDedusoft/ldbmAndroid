package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 客户类型
 * Created by wangjianwei on 2016/7/5.
 * ID:id；BianHao:编号；LeiBie:类型名称}
 */
public class ClientType implements Serializable {
    private int ID;
    private String BianHao;
    private String LeiBie;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBianHao() {
        return BianHao;
    }

    public void setBianHao(String bianHao) {
        BianHao = bianHao;
    }

    public String getLeiBie() {
        return LeiBie;
    }

    public void setLeiBie(String leiBie) {
        LeiBie = leiBie;
    }
}
