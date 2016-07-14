package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 配件类型
 * Created by wangjianwei on 2016/7/14.
 * {"ID":5,"LeiBie":"脚垫类","BianHao":"001001"}
 */
public class FixingsType implements Serializable {
    private int ID;
    private String LeiBie;
    private String BianHao;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLeiBie() {
        return LeiBie;
    }

    public void setLeiBie(String leiBie) {
        LeiBie = leiBie;
    }

    public String getBianHao() {
        return BianHao;
    }

    public void setBianHao(String bianHao) {
        BianHao = bianHao;
    }
}
