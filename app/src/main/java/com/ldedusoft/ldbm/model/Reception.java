package com.ldedusoft.ldbm.model;

/**
 * 维修接待列表
 * {Id：id；DanHao：单号；jdTime：接待时间；CarCode：车牌号；
 * MingCheng：客户名称；WeiXiuWay：维修方式；ywLeiBie：业务类别}
 * Created by wangjianwei on 2016/7/2.
 */
public class Reception {
    private int Id;
    private String DanHao;
    private String jdTime;
    private String CarCode;
    private String MingCheng;
    private String WeiXiuWay;
    private String ywLeiBie;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDanHao() {
        return DanHao;
    }

    public void setDanHao(String danHao) {
        DanHao = danHao;
    }

    public String getJdTime() {
        return jdTime;
    }

    public void setJdTime(String jdTime) {
        this.jdTime = jdTime;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public String getMingCheng() {
        return MingCheng;
    }

    public void setMingCheng(String mingCheng) {
        MingCheng = mingCheng;
    }

    public String getWeiXiuWay() {
        return WeiXiuWay;
    }

    public void setWeiXiuWay(String weiXiuWay) {
        WeiXiuWay = weiXiuWay;
    }

    public String getYwLeiBie() {
        return ywLeiBie;
    }

    public void setYwLeiBie(String ywLeiBie) {
        this.ywLeiBie = ywLeiBie;
    }
}
