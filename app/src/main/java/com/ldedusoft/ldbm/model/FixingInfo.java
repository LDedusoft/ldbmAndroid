package com.ldedusoft.ldbm.model;

import java.io.Serializable;

/**
 * 配件信息
 * Created by wangjianwei on 2016/7/9.
 * {ID：id；BianHao：编号；MingCheng：名称；TuHao：图号；CangKu：仓库；KuCun：库存数量；
 * KuCunJinE：库存金额；KuCunJunJia：库存均价；DanWei：单位}
 */
public class FixingInfo implements Serializable {
    private int ID;
    private String BianHao;
    private String MingCheng;
    private String TuHao;
    private String CangKu;
    private String KuCun;
    private String KuCunJinE;
    private String KuCunJunJia;
    private String DanWei;

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

    public String getMingCheng() {
        return MingCheng;
    }

    public void setMingCheng(String mingCheng) {
        MingCheng = mingCheng;
    }

    public String getTuHao() {
        return TuHao;
    }

    public void setTuHao(String tuHao) {
        TuHao = tuHao;
    }

    public String getCangKu() {
        return CangKu;
    }

    public void setCangKu(String cangKu) {
        CangKu = cangKu;
    }

    public String getKuCun() {
        return KuCun;
    }

    public void setKuCun(String kuCun) {
        KuCun = kuCun;
    }

    public String getKuCunJinE() {
        return KuCunJinE;
    }

    public void setKuCunJinE(String kuCunJinE) {
        KuCunJinE = kuCunJinE;
    }

    public String getKuCunJunJia() {
        return KuCunJunJia;
    }

    public void setKuCunJunJia(String kuCunJunJia) {
        KuCunJunJia = kuCunJunJia;
    }

    public String getDanWei() {
        return DanWei;
    }

    public void setDanWei(String danWei) {
        DanWei = danWei;
    }
}
