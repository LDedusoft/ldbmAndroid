package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * Created by wangjianwei on 2016/7/13.
 * {ID：id;CangKu：仓库名称；BianHao：编号}
 */
public class FixingsWarehouse implements Serializable {
    private int id;
    private String CangKu;
    private String BianHao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCangKu() {
        return CangKu;
    }

    public void setCangKu(String cangKu) {
        CangKu = cangKu;
    }

    public String getBianHao() {
        return BianHao;
    }

    public void setBianHao(String bianHao) {
        BianHao = bianHao;
    }
}
